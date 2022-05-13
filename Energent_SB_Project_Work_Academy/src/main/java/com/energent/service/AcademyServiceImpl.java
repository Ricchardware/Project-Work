package com.energent.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.energent.entity.Academy;
import com.energent.entity.Student;
import com.energent.repository.AcademyRepository;
import com.energent.repository.StudentRepository;

@Service
public class AcademyServiceImpl implements AcademyService {

	//Qui creiamo una variabile per stampare risultati sulla console, in caso di bisogno
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	
	//In questa service avremo bisogno di student e academy repository, quindi le iniettiamo
	@Autowired
	private AcademyRepository academyRepository;
	
	@Autowired
	private StudentRepository studentRepository;
	
	
	/*
	 * Questa variabile permette di etichettare una data situazione con un numero intero;
	 * questo ci permette di far scrivere alla pagina accademy.jsp messaggi dinamici che
	 * richiedono all'utente azioni specifiche per poter procedere col programma.
	 * 
	 * Qui sotto le sitzuazioni che descrivono i vari stati di allerta del programma:
	 * 
	 * - Situazione = 0 : senza errori
	 * - Situazione = 1 : riempire tutti i campi
	 * - Situazione = 2 : data di inizio maggiore di data di fine
	 * - Situazione = 3 : età minore di 6
	 * - Situazione = 4 : caratteri speciali non autorizzati
	 */
	private static int situation = 0;
	
	
	
	//Metodi alla quale il controller di academy si appoggia :::::::::::::::::::::::::::::::::::::::::::::::
	
	/*
	 * Questo metodo ci permette di cercare e trovare un' accademia specifica all'interno del
	 * database; ci servirà per delle verifiche e per delle operazioni di CRUD
	 */
	@Override
	public Academy findAcademyById(String code) {
		
		return academyRepository.findById(code).get();
	}
	
	
	/*
	 * Questo metodo ci permette di aggiungere un accademia al database ( sezione Creation delle
	 * operazioni di CRUD ); qui effettueremo anche dei controlli per confermare che l'utente abbia
	 * eseguito tutti i passaggi correttamente, come il controllo dell'inserimento di tutti i campi,
	 * il conrollo delle date e il controllo dei caratteri speciali; tutti questi controlli avverranno
	 * attraverso metodi esterni, perchè ripetuti anche in update.
	 */
	@Override
	public String addAcademy(Academy academy) {
		
		/*
		 * Qui richiamiamo un metodo creato da noi per effettuare i vari controlli sulle form riempite
		 * dall'utente; anche la variabile situation viene gestita da questo metodo.
		 */
		if( checkForms(academy) == true ) {
			
			return "/academies/add";
			
		}
		
		/*
		 *  Qui creiamo una variabile boolean che ci permette di vedere se la sessione di inserimento
		 *  è andato a buon fine
		 */
		boolean session = false;
		
		/*
		 * Qui controlliamo che non ci sia conflitto nelle richieste cntrollando se un altro utente
		 * non abbia creato un accademia con un codice uguale evitando cosi conflitti
		 */
		if ( academyRepository.existsById(academy.getCode()) == false) {
				
			academyRepository.save(academy); 
				
			session = true;
				
		}
		
		/*
		 * Se la sessione è andata a buon fine allora il valore contenuto in session sarà true e quindi
		 * il programma resitutirà la pagina di accademie, altrimenti agirà di conseguenza
		 */
		if (session == true) {
			
			return "/academies";
			
		}else {
			
			return "/codeAlreadyExisting";
			
		}
		
				
	}
	
	
	/*
	 * Questo metodo ci permette di aggiornare un accademia nel database ( sezione Update delle
	 * operazioni di CRUD ); qui effettueremo anche dei controlli per confermare che l'utente abbia
	 * eseguito tutti i passaggi correttamente, come il controllo dell'inserimento di tutti i campi,
	 * il conrollo delle date e il controllo dei caratteri speciali; tutti questi controlli avverranno
	 * attraverso metodi esterni, perchè ripetuti anche in add.
	 */
	@Override
	public String updateAcademy(Academy academy) {
		
		/*
		 * Qui richiamiamo un metodo creato da noi per effettuare i vari controlli sulle form riempite
		 * dall'utente; anche la variabile situation viene gestita da questo metodo.
		 */
		if( checkForms(academy) == true ) {
			
			return "/{code}/update";
			
		}
		
		/*
		 *  Qui creiamo una variabile boolean che ci permette di vedere se la sessione di aggiornamento
		 *  è andato a buon fine
		 */
		boolean session = false;	
		
		
		/*
		 * Qui controlliamo se non ci sia conflitto nelle richieste, confermando effettivamente
		 * l'esistenza dell'accademia
		 */
		if ( academyRepository.existsById(academy.getCode()) == true ) {
			
			academy.setCode(academy.getCode());
			academyRepository.save(academy); 
				
			session = true;
				
		}
		
		/*
		 * Se la sessione è andata a buon fine allora il valore contenuto in session sarà true e quindi
		 * il programma resitutirà la pagina di accademie, altrimenti agirà di conseguenza
		 */
		if (session == true) {
			
			return "/academies";
			
		}else {
			
			return "/error";
			
		}
		
				
	}
		
	
	/*
	 * Questo metodo ci permette di cancellare un accademia dal database ( sezione Delete delle
	 * operazioni di CRUD ); prima di tutto cancelliamo tutte le relazioni tra accademia che stiamo
	 * per cancellare e gli studenti al suo interno; poi una volta fatto questo controlliamo se 
	 * qualche studente è rimasto senza accademie: se uno studente è rimasto senza accademie
	 * allora verrà cancellato dal database degli studenti, per evitare problemi di conflitto
	 * fra primary keys ( codici fiscali ). Una volta fatto questo possiamo procedere con la 
	 * cancellazione dell'accademia dal database.
	 */
	@Override
	public void removeAcademy(String code) {
		
		//Qui cancelliamo tutte le relazioni attraverso una query
		academyRepository.deletejoin(code);
		
		/* 
		 * Qui prendiamo tutti gli studenti dalla tabella student e inseriamo tutti i codici fiscali
		 * dentro una lista di stringhe; questo ci permetterà di effettuare i controlli
		 */
		List<Student> students = studentRepository.findAll();
		
		List<String> fiscalCodesStudents = new ArrayList<>(); 
		
		for (Student student : students) {
			
			fiscalCodesStudents.add(student.getFiscalCode());
			
		}	
		
		/*
		 * Qui prendiamo tutti i codici fiscali dentro la tabella academy_student; questo ci
		 * permetterà di effettuare i controlli
		 */
		List<String> fiscalCodesAcademyStudent = studentRepository.checkRelation();
		
		/*
		 * Variabile che controlla l'esistenza di un codice fiscale dentro la tabella academy_student;
		 * questa variabile verrà gestita dentro il for
		 */
		int isItThere = 0;
		
		/*
		 * In questi for each faremo un controllo incrociato di ogni codice fiscale presente dentro
		 * la tabella student con ogni codice fiscale dentro la tabella academy_student. Il controllo
		 * consiste nel confermare l'esistenza per ogni studente dentro la tabella student di una 
		 * relazione ad un accademia: se uno studente è rimasto senza relazioni allora isItThere rimarrà 0
		 * e darà l'OK al programma di cancellare definitivamente lo studente dal database; se trova almeno
		 * una relazione allora isItThere diventerà 1 e riferirà al programma che non deve cancellare
		 * lo studente trovato dal database. 
		 */
		for ( String codiceFiscale : fiscalCodesStudents) {
			
			for ( String fiscalCode : fiscalCodesAcademyStudent ) {
				
				/*
				 * Effettuiamo un controllo attraverso equals per vedere se il codice fiscale di student
				 * sia uguale a un codice fiscale dentro la tabella academy student.
				 */
				if ( codiceFiscale.equals(fiscalCode)) {
					
					isItThere = 1;
					
				}
					
			}
			
			/*
			 * Qui controlliamo che operazione effettuare: se non è stata trovata alcuna relazione per un
			 * dato studente allora lo studente verrà cancellato dal database e il ciclo di controllo
			 * dentro il for each riprende; se è stata trovata almeno una relazione per un dato studente
			 * allora si attiverà l'else, che resetterà isItThere a 0 per poter permettere al programma
			 * di riprendere il ciclo in maniera normale.
			 */
			if ( isItThere == 0 ) {
				
				studentRepository.deleteById(codiceFiscale);
				
			}else {
				
				isItThere = 0;
				
			}
			
		}
		
		//Cancellazione definitiva dell'accademia
		academyRepository.deleteById(code);
		
	}
	
	
	/*
	 * Questo metodo ci permette di leggere tutte le accademie dentro il database ( sezione Read delle
	 * operazioni di CRUD ). Questo metodo serve alla pagina academies per poter presentare nel sito
	 * tutte le accademie e i suoi dati
	 */
	@Override
	public List<Academy> findAllAcademies() {
		
		return academyRepository.findAll();
	}
	
	
	/*
	 * Questo get e questo set ci permettono di maneggiare la variabile situazione da fuori l'Academy Service;
	 * la variabile situation viene spiegata alla riga 33
	 */
	@Override
	public int getSituation() {
		
		return situation;
		
	}
	
	@Override
	public void setSituation( int situationSet) {
		
		situation = situationSet;
		
	}
	//:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	
	
	//Metodi alla quale la service di Accademy si appoggia:::::::::::::::::::::::::::::::::::::::::::::::::
	//I meodi sono private perchè utilizzati solo da Academy Service ::::::::::::::::::::::::::::::::::::::
	
	/*
	 * Questo metodo ci permettere di controllare come sono stati riempiti i form dall'utente in maniera
	 * corretta; dentro i commenti che scpecificano i vari controlli
	 */
	private boolean checkForms ( Academy academy ) {
		
		/*
		 * Qui controlliamo se tutti i form sono stati riempiti: abbiamo usato or perchè se anche uno solo
		 * di questi form non è stato riempito allora l'if si deve attivare, descrivendo poi che la
		 * situazione è di tipo 1 ( per info sulle situazioni controllare riga 33 ) e restituisce true;
		 * il true segnala la presenza di un errore da parte dell'utente.
		 */
		if ( academy.getCode() == "" || academy.getTitle() == "" 
				|| academy.getLocation() == "" ) {
			
			situation = 1;
			
			return true;
			
		}
		
		/*
		 * Qui controlliamo se la data di fine sia minore della data di inizio: se troviamo questo
		 * conflitto allora l'if si deve attivare, descrivendo poi che la situazione è di tipo 2 
		 * ( per info sulle situazioni controllare riga 33 ) e restituisce true;
		 * il true segnala la presenza di un errore da parte dell'utente.
		 */
		if ( academy.getEndDate().before(academy.getStartDate()) == true ) {
			
			situation = 2;
			
			return true;	
			
		}
		
		/*
		 * Qui, attraverso un API, controlliamo la presenza di caratteri speciali in title e location:
		 * settiamo il pattern a questo valore, perchè l'API ha bisogno di un certo range di caratteri
		 * alla quale dare il permesso di essere presente nella stringa. Il matcher poi controlla se
		 * dentro la stringa data siano presenti altri caratteri che non si trovano nel range.
		 * Se sono presenti caretteri "illegali" allora entra dentro l'if descrivendo la situazione 4
		 * ( per info sulle situazioni controllare riga 33 ) e restituisce true;
		 * il true segnala la presenza di un errore da parte dell'utente.
		 */
		Pattern pattern = Pattern.compile("[^a-zA-Z \\\\t\\\\n\\\\x0B\\\\f\\\\r]");
		Matcher matcher = pattern.matcher(academy.getTitle());
		
		if ( matcher.find() ){
			
			situation = 4;
			
			return true;
			
		}
		
		matcher = pattern.matcher(academy.getLocation());
		
		if ( matcher.find() ){
			
			situation = 4;
			
			return true;
			
		}
		
		/*
		 * Se non sono stati trovati errori allora l'intero metodo resiturà false; ciò dice al programma che
		 * non sono stati commessi errori dall'utente, ed ha il permesso di procedere
		 */
		return false;
		
	}
	//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
}
