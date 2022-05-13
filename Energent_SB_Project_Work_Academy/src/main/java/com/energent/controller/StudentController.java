package com.energent.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.energent.entity.Student;
import com.energent.service.AcademyService;
import com.energent.service.StudentService;


@Controller
public class StudentController {
		
	//Qui creiamo una variabile per stampare risultati sulla console, in caso di bisogno
	Logger log = LoggerFactory.getLogger(this.getClass());
		
	//Qui creiamo una variabile di appoggio per il conflitto di codici fiscali
	private Student student = null;
		
	//In questa service avremo bisogno di academy e student service, quindi le iniettiamo
	@Autowired
	private StudentService studentService;
		
	@Autowired
	private AcademyService academyService;
		
		
	
	//READ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	@GetMapping("/{code}/students")
	public ModelAndView showAllStudents( @PathVariable String code) {
			
		ModelAndView mav=new ModelAndView("students");
			
		/*
		 * Qui chiediamo alla service di restituirci tutti gli studenti, così che possiamo presentarli
		 * all'utente che le richiede
		 */
		mav.addObject("students", academyService.findAcademyById(code).getStudents());
			
		/*
		 * Aggiungiamo il codice dell'accademia perchè, lavorando in un accademia alla volta, al
		 * programma e alle pagine servirà un pò ovunque
		 */
		mav.addObject("academy", academyService.findAcademyById(code));
			
		/*
		 * Se l'utente richiede di vedere gli studenti significherà che non ci sono operazioni di CRUD
		 * in sospeso; quindi, la situazione non può essere altre che 0, quindi informiamo la Student 
		 * Service che può "normalizzare" la situazione
		 */
		studentService.setSituation(0);
			
		return mav;
			
	}
	//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	
	
	//ADD:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	@PostMapping("/{code}/student/add")
	public ModelAndView intermediario( @ModelAttribute Student student, @PathVariable String code ) {
			
		/*
		 * Il redirect verrà completato dal metodo addStudent; questo approccio rende dinamica la restituzione
		 * della pagina, permettendoci di gestire varie casistiche.
		 */
		String page ="redirect:" + studentService.addStudent(student, code);
			
		/*
		 * Questo this ci occorre in caso di conflitto di codici fiscali; in pratica riempiamo la variabile 
		 * accademy creata all'inizio del controller per poter permettere al metodo showExistingCode
		 * di avere l'accademy pronta per essere visualizzata dall'utente
		 */	
		this.student = student;
			
		ModelAndView mav=new ModelAndView(page);
			
		return mav;
			
	}
		
	@GetMapping("{code}/student/add")
	public ModelAndView addStudent( @PathVariable String code ) {
		
		ModelAndView mav=new ModelAndView("student");
		
		mav.addObject("student", new Student());
		
		/*
		 * Aggiungiamo il codice dell'accademia perchè, lavorando in un accademia alla volta, al
		 * programma e alle pagine servirà un pò ovunque
		 */
		mav.addObject("academy", academyService.findAcademyById(code));
		
		/*
		 * Diciamo alla pagina la situazione in cui ci troviamo per poter permettere ad essa di scrivere
		 * messaggi in base a cambiamenti che l'utente deve effettuare
		 */
		mav.addObject("situation", studentService.getSituation());
			
		return mav;
		
	}
		
	@GetMapping("/{code}/fiscalCodeAlreadyExisting")
	public ModelAndView showExistingCode( @ModelAttribute Student studente,
				@PathVariable String code) {
		
		/*
		 * Prendiamo lo studente inserito da this.student nel metodo addStudent, così che l'utente possa
		 * visualizzarlo
		 */
		studente = student;
			
		ModelAndView mav = new ModelAndView("fiscalCodeAlreadyExisting");
		
		/*
		 * Qui aggiungiamo lo studente in conflitto
		 */
		mav.addObject("student", studente);
		
		/*
		 * Aggiungiamo il codice dell'accademia perchè, lavorando in un accademia alla volta, al
		 * programma e alle pagine servirà un pò ovunque
		 */
		mav.addObject("academy", academyService.findAcademyById(code));
			
		return mav;

	}
	//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	
	
	//UPDATE::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	@PostMapping("/{code}/student/update/{fiscalCode}")
	public ModelAndView intermediate( @ModelAttribute Student student,
			@PathVariable String code, @PathVariable String fiscalCode ) {
		
		/*
		 * Il redirect verrà completato dal metodo updatesTUDENT; questo approccio rende dinamica la 
		 * restituzione della pagina, permettendoci di gestire varie casistiche.
		 */
		ModelAndView mav=new ModelAndView("redirect:" + studentService.updateStudent(student, code));
		
		/*
		 * Aggiungiamo il codice dell'accademia perchè, lavorando in un accademia alla volta, al
		 * programma e alle pagine servirà un pò ovunque
		 */
		mav.addObject(code);
			
		return mav;
			
	}
	
	
	@GetMapping("/{code}/student/update/{fiscalCode}")  
	public ModelAndView showUpdateStudentPage(@PathVariable String fiscalCode,
			@PathVariable String code) {
			
		ModelAndView mav=new ModelAndView("student");
		
		/*
		 * Qui cerchiamo lO STUDENTE che l'utente vuole aggiornare, in questo modo possiamo riempire i
		 * form per lui. Questo permetterà alla pagina di essere più user friendly.
		 */
		mav.addObject("student",studentService.findStudentById(fiscalCode));
		
		/*
		 * Aggiungiamo il codice dell'accademia perchè, lavorando in un accademia alla volta, al
		 * programma e alle pagine servirà un pò ovunque
		 */
		mav.addObject("academy", academyService.findAcademyById(code));
		
		/*
		 * Diciamo alla pagina la situazione in cui ci troviamo per poter permettere ad essa di scrivere
		 * messaggi in base a cambiamenti che l'utente deve effettuare
		 */
		mav.addObject("situation", studentService.getSituation());
			
		return mav;
		
	}
	//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	
	
	//DELETE::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	@GetMapping("/{code}/student/remove/{fiscalCode}")
	public ModelAndView removeStudent(@PathVariable String fiscalCode,
			@PathVariable String code) {
		
		/*
		 * Restituiamo direttamente students per dare all'utente la cancellazione in tempo reale
		 */
		String page = "redirect:/{code}/students";

		ModelAndView mav = new ModelAndView(page);
		
		/*
		 * Metodo di cancellazione dello studente nella service
		 */
		studentService.removeStudent(fiscalCode, code);
		
		return mav;
		
	}
	//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
}
