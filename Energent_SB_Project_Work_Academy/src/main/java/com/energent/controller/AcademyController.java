package com.energent.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import com.energent.entity.Academy;
import com.energent.service.AcademyService;



@Controller
public class AcademyController {

	//Qui creiamo una variabile per stampare risultati sulla console, in caso di bisogno
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	//Qui creiamo una variabile di appoggio per il conflitto di codici
	private Academy academy = null;
	
	//In questa service avremo bisogno di academy service, quindi la iniettiamo
	@Autowired
	private AcademyService academyService;

	
	
	//READ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	@GetMapping("/home")
	public ModelAndView showHomePage() {
		
		/*
		 * La home resituirà direttamente la pagina delle accademie perchè da lì si può accedere
		 * a tutte le funzioni, però il link iniziale deve essere comunque home
		 */
		ModelAndView mav = new ModelAndView("redirect:/academies");
		
		return mav;
	}
	
	@GetMapping("/academies")
	public ModelAndView showAllAcademies() {

		ModelAndView mav = new ModelAndView("academies");
		
		/*
		 * Qui chiediamo alla service di restituirci tutte le accademie, così che possiamo presentarle
		 * all'utente che le richiede
		 */
		List<Academy> academies = academyService.findAllAcademies();
	
		
		mav.addObject("academies", academies);
		
		/*
		 * Se l'utente richiede di vedere le accademie significherà che non ci sono operazioni di CRUD
		 * in sospeso; quindi, la situazione non può essere altre che 0, quindi informiamo l'Academy Service
		 * che può "normalizzare" la situazione
		 */
		academyService.setSituation(0);

		return mav;
	}
	//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

	
	//ADD:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	@PostMapping("/academies/add")
	public ModelAndView addAcademy(@ModelAttribute("academy") Academy academy) {
		
		/*
		 * Il redirect verrà completato dal metodo addAcademy; questo approccio rende dinamica la restituzione
		 * della pagina, permettendoci di gestire varie casistiche.
		 */
		String page = "redirect:" + academyService.addAcademy(academy);
		
		/*
		 * Questo this ci occorre in caso di conflitto di codici; in pratica riempiamo la variabile 
		 * accademy creata all'inizio del controller per poter permettere al metodo showExistingCode
		 * di avere l'accademy pronta per essere visualizzata dall'utente
		 */
		this.academy = academy;
		
		ModelAndView mav = new ModelAndView(page);

		return mav;
		
	}
	
	
	@GetMapping("/academies/add")
	public ModelAndView addOneAcademy(@ModelAttribute("academy") Academy academy) {
		
		ModelAndView mav = new ModelAndView("academy");
		
		/*
		 * Diciamo alla pagina la situazione in cui ci troviamo per poter permettere ad essa di scrivere
		 * messaggi in base a cambiamenti che l'utente deve effettuare
		 */
		mav.addObject("situation", academyService.getSituation());
		
		return mav;
		
	}
	
	@GetMapping("codeAlreadyExisting")
	public ModelAndView showExistingCode( @ModelAttribute Academy accademia ) {
		
		/*
		 * Prendiamo l'accademia inserita da this.acadamy nel metodo addAcademy, così che l'utente possa
		 * visualizzarla
		 */
		accademia = academy;
		
		ModelAndView mav = new ModelAndView("codeAlreadyExisting");
		
		/*
		 * Qui aggiungiamo l'accademia in conflitto
		 */
		mav.addObject(accademia);
		
		return mav;

	}
	//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	
	
	//UPDATE::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	@PostMapping("/{code}/update")
	public ModelAndView updateAcademy(@ModelAttribute("academy") Academy academy,
			@PathVariable String code ) {
		
		/*
		 * Il redirect verrà completato dal metodo updateAcademy; questo approccio rende dinamica la 
		 * restituzione della pagina, permettendoci di gestire varie casistiche.
		 */
		String page = "redirect:" + academyService.updateAcademy(academy);

		ModelAndView mav = new ModelAndView(page);
		
		return mav;

	}

	@GetMapping("/{code}/update")
	public ModelAndView showUpdateAcademyPage(@PathVariable String code) {
		
		ModelAndView mav = new ModelAndView("academy");
		
		/*
		 * Qui cerchiamo l'academy che l'utente vuole aggiornare, in questo modo possiamo riempire i
		 * form per lui. Questo permetterà alla pagina di essere più user friendly.
		 */
		Academy academy = academyService.findAcademyById(code);
		mav.addObject("academy", academy);
		
		/*
		 * Diciamo alla pagina la situazione in cui ci troviamo per poter permettere ad essa di scrivere
		 * messaggi in base a cambiamenti che l'utente deve effettuare
		 */
		mav.addObject("situation", academyService.getSituation());
		
		return mav;

	}
	//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	
	
	//DELETE::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	@GetMapping("/{code}/remove")
	public ModelAndView removeAcademy(@PathVariable String code) {
		
		/*
		 * Restituiamo direttamente academies per dare all'utente la cancellazione in tempo reale
		 */
		String page = "redirect:/academies";

		ModelAndView mav = new ModelAndView(page);
		
		/*
		 * Metodo di cancellazione dell'accademia nella service
		 */
		academyService.removeAcademy(code);
		return mav;
	}
	//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

}
