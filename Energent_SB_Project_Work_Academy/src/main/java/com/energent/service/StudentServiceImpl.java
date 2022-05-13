package com.energent.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.energent.entity.Student;
import com.energent.repository.StudentRepository;
import java.util.regex.Matcher; 
import java.util.regex.Pattern;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {
	
	//Qui creiamo una variabile per stampare risultati sulla console, in caso di bisogno
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	//In questa service avremo bisogno di student e academy service, quindi le iniettiamo
	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private AcademyService academyService;
	
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
	 * Questo metodo ci permette di cercare e trovare uno studente specifico all'interno del
	 * database; ci servirà per delle verifiche e per delle operazioni di CRUD
	 */
	@Override
	public Student findStudentById(String fiscalCode) {

		return studentRepository.findById(fiscalCode).get();
		
	}
	
	
	/*
	 * Questo metodo ci permette di aggiungere uno studente al database ( sezione Creation delle
	 * operazioni di CRUD ) e di relazionarlo ad un accademia; qui effettueremo anche dei controlli 
	 * per confermare che l'utente abbia eseguito tutti i passaggi correttamente, come il controllo 
	 * dell'inserimento di tutti i campi, il conrollo dell'età e il controllo dei caratteri speciali; 
	 * tutti questi controlli avverranno attraverso metodi esterni, perchè ripetuti anche in update.
	 */
	@Override
	public String addStudent(Student student, String code) {
		
		if ( checkForms(student) == true ) {
			
			return "/{code}/student/add";
			
		}
		
		
		/*
		 *  Qui creiamo una variabile boolean che ci permette di vedere se la sessione di inserimento
		 *  è andato a buon fine
		 */
		boolean session = false;
		
		/*
		 * Se ( if ) lo studente esiste già sul database dobbiamo trattare l'inserimento come un
		 * aggiornamento, altrimenti ( else ) bisogna trattare l'inserimento in maniera normale
		 */
		if ( checkAllStudents(code, student.getFiscalCode()) == false ) {
			
			/*
			 * Qui controlliamo che non ci sia conflitto nelle richieste cntrollando se un altro utente
			 * non abbia creato uno studente con un codice uguale evitando cosi conflitti
			 */
			if ( studentRepository.existsById(student.getFiscalCode())) {
				
				/*
				 * Qui, attraverso una query creata appositamete da noi, creiamo la relazione tra
				 * accademia e studente
				 */
				studentRepository.insertJoin(student.getFiscalCode(), code);
				
				/*
				 * Siccome dobbiamo aggiungere accademie allo studente ci appoggiamo al metodo di
				 * update, anche se si tratta di un inserimento; questo ci permette di relazionare
				 * uno studente a più di un accademia ( Many To Many ) e di evitare una sovrascrittura
				 * delle accademie
				 */
				updateStudent( student, code );
				
			}else {
			
				/*
				 * Se lo studente non esiste in nessun'altra accademia allora si tratta di quella che
				 * possiamo creare creazione totale; in pratica lo studente non esiste nè nel database
				 * student, nè nel database academy_student. Quindi salviamo il nuovo studente nel database
				 * e poi specifichiamo la relazione
				 */
				studentRepository.save(student);
				studentRepository.insertJoin(student.getFiscalCode(), code);
			
			}
			
			session = true;
				
		}
		
		/*
		 * Se la sessione è andata a buon fine allora il valore contenuto in session sarà true e quindi
		 * il programma resitutirà la pagina di studenti, altrimenti agirà di conseguenza
		 */
		if (session == true) {
			
			return "/{code}/students";
			
		}else {
			
			return "/{code}/fiscalCodeAlreadyExisting";
			
		}
		
		
	}
	
	
	/*
	 * Questo metodo ci permette di aggiornare uno studente nel database ( sezione Update delle
	 * operazioni di CRUD ); qui effettueremo anche dei controlli per confermare che l'utente abbia eseguito 
	 * tutti i passaggi correttamente, come il controllo dell'inserimento di tutti i campi, il conrollo 
	 * dell'età e il controllo dei caratteri speciali; tutti questi controlli avverranno attraverso metodi 
	 * esterni, perchè ripetuti anche in add.
	 */
	@Override
	public String updateStudent(Student student, String code ) {
		
		if ( checkForms(student) == true ) {
			
			return "/{code}/student/update/{fiscalCode}";
			
		}
		
		
		/*
		 *  Qui creiamo una variabile boolean che ci permette di vedere se la sessione di inserimento
		 *  è andato a buon fine
		 */
		boolean session = false;
		
		/*
		 * Qui controlliamo che non ci sia conflitto nelle richieste cntrollando se un altro utente
		 * non abbia creato uno studente con un codice uguale evitando cosi conflitti
		 */
		if ( studentRepository.existsById(student.getFiscalCode()) == true) {
			
			/*
			 * Creiamo una variabile di appoggio che ci permette di aggiornare in sicurezza
			 * lo studente per poi inserirlo nel database
			 */
			Student studentToUpdate = findStudentById(student.getFiscalCode());
			
			studentToUpdate.setFirstName(student.getFirstName());
			studentToUpdate.setLastName(student.getLastName());
			studentToUpdate.setAge(student.getAge());
			
			studentRepository.save(studentToUpdate);
			
			
			session = true;
				
		}
		
		/*
		 * Se la sessione è andata a buon fine allora il valore contenuto in session sarà true e quindi
		 * il programma resitutirà la pagina di studenti, altrimenti agirà di conseguenza
		 */
		if (session == true) {
			
			return "/{code}/students";
			
		}else {
			
			return "/error";
			
		}
		
		
	}


	/*
	 * Questo metodo ci permette di cancellare uno studente da un accademia ( sezione Delete delle
	 * operazioni di CRUD ); prima di tutto cancelliamo tutte le relazioni tra studente che stiamo
	 * per cancellare e l'accademia dalla quale lo stiamo per cancellare; poi una volta fatto questo 
	 * controlliamo se lo studente è rimasto senza accademie: se uno studente è rimasto senza accademie
	 * allora verrà cancellato dal database degli studenti, per evitare problemi di conflitto
	 * fra primary keys ( codici fiscali ).
	 */
	@Override
	public void removeStudent(String fiscalCode, String code) {
		
		//Qui cancelliamo la relazione attraverso una query
		studentRepository.deletejoin(fiscalCode, code);
		
		/*
		 * Variabile che controlla l'esistenza di un codice fiscale dentro la tabella academy_student;
		 * questa variabile verrà gestita dentro il for
		 */
		int isItThere = 0;
		
		/*
		 * Qui prendiamo tutti i codici fiscali dentro la tabella academy_student; questo ci
		 * permetterà di effettuare i controlli
		 */
		List<String> fiscalCodes = studentRepository.checkRelation();
		
		for ( String codiceFiscale : fiscalCodes) {
			
			/*
			 * Effettuiamo un controllo attraverso equals per vedere se il codice fiscale di student
			 * sia uguale a un codice fiscale dentro la tabella academy student.
			 */
			if ( fiscalCode.equals(codiceFiscale)) {
				
					isItThere = 1;
				
			}
			
		}
		
		/*
		 * Qui controlliamo se cancellare lo studente dal database se non è stata trovata alcuna relazione 
		 * ad accademie allora lo studente verrà cancellato dal database; se è stata trovata almeno una 
		 * relazione per un dato studente allora lo studente rimarrà nel database.
		 */
		if ( isItThere == 0 ) {
			
			studentRepository.deleteById(fiscalCode);
			
		}

	}
	
	
	/*
	 * Questo metodo ci permette di leggere tutte gli studenti dentro un accademia ( sezione Read delle
	 * operazioni di CRUD ). Questo metodo serve alla pagina students per poter presentare nel sito
	 * tutte gli studenti nella data accademia e i loro dati
	 */
	@Override
	public List<Student> findAllStudents( String academyCode ) {

		return academyService.findAcademyById(academyCode).getStudents();

	}
	
	
	
	/*
	 * Questo get e questo set ci permettono di maneggiare la variabile situazione da fuori l'Academy Service;
	 * la variabile situation viene spiegata alla riga 30
	 */
	@Override
	public int getSituation() {
		
		return situation;
		
	}
	
	@Override
	public void setSituation(int situationSet) {
		
		situation = situationSet;
		
	}
	//:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	
	
	
	//Metodi alla quale la service di Student si appoggia::::::::::::::::::::::::::::::::::::::::::::::::::
	//I meodi sono private perchè utilizzati solo da Student Service ::::::::::::::::::::::::::::::::::::::
	
	/*
	 * Questo metodo ci permettere di controllare come sono stati riempiti i form dall'utente in maniera
	 * corretta; dentro i commenti che scpecificano i vari controlli
	 */
	private boolean checkForms ( Student student ) {
		
		/*
		 * Qui controlliamo se tutti i form sono stati riempiti: abbiamo usato or perchè se anche uno solo
		 * di questi form non è stato riempito allora l'if si deve attivare, descrivendo poi che la
		 * situazione è di tipo 1 ( per info sulle situazioni controllare riga 30 ) e restituisce true;
		 * il true segnala la presenza di un errore da parte dell'utente.
		 */
		if ( student.getFirstName() == "" || student.getLastName() == "" 
				|| student.getFiscalCode() == "" ) {
			
			situation = 1;
			
			return true;
			
		}
		
		
		/*
		 * Qui controlliamo se l'età inserita è minore di 6: se l'età inserita è minore di 6, allora
		 * l'if si deve attivare, descrivendo poi che la situazione è di tipo 3 ( per info sulle situazioni ù
		 * controllare riga 30 ) e restituisce true; il true segnala la presenza di un errore da parte 
		 * dell'utente.
		 */
		if ( student.getAge() < 6 ) {
			
			situation = 3;
			
			return true;	
			
		}
		
		/*
		 * Qui controlliamo la presenza di caratteri speciali in nome, cognome e codice fiscale:
		 * settiamo il pattern a questo valore, perchè l'API ha bisogno di un certo range di caratteri
		 * alla quale dare il permesso di essere presente nella stringa. Il matcher poi controlla se
		 * dentro la stringa data siano presenti altri caratteri che non si trovano nel range.
		 * Se sono presenti caretteri "illegali" allora entra dentro l'if descrivendo la situazione 4
		 * ( per info sulle situazioni controllare riga 30 ) e restituisce true;
		 * il true segnala la presenza di un errore da parte dell'utente.
		 */
		Pattern pattern = Pattern.compile("[^a-zA-Z]");
		Matcher matcher = pattern.matcher(student.getFirstName());
		
		if ( matcher.find() ){
			
			situation = 4;
			
			return true;
			
		}
		
		matcher = pattern.matcher(student.getLastName());
		
		if ( matcher.find() ){
			
			situation = 4;
			
			return true;
			
		}
		
		/*
		 * Qui cambiamo il pattern perchè dentro il codice fiscale possono esserci anche numeri, quindi
		 * aumentiamo il range di controllo e permettiamo l'inserimento di numeri
		 */
		pattern = Pattern.compile("[^a-zA-Z0-9]");
		matcher = pattern.matcher(student.getFiscalCode());
		
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
	
	
	/*
	 * Questo metodo ci permette di controllare l'esistenza di uno studente dentro una data accademia;
	 * questo serivrà ai metodi di add e update per controllare che sia tutto apposto e poter procedere
	 * tranquillamente; se il metodo restituisce false allora è tutto apposto, altrimenti restituirà true
	 */
	private boolean checkAllStudents( String code, String fiscalCode) {
		
		List<Student> students = academyService.findAcademyById(code).getStudents();
		
		for( Student student: students) {
			
			/*
			 * Attraverso equals controlliamo se il codice fiscale dato dll'utente si uguale
			 * ad un codice fiscale presente nel database
			 */
			if ( student.getFiscalCode().equals(fiscalCode)) {
				
				/*
				 * Il metodo ritornerà true se dentro l'accademia c'è uno studente con il codice
				 * fiscale inserito dall'utente, dicendo poi al programma di restituire la pagina
				 * di conflitto di codici fiscali.
				 */
				return true;
				
			}
			
		}
		
		/*
		 * Se il metodo ritorna false allora il programma può procedere normalmente.
		 */
		return false;
		
	}
	//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	
}
