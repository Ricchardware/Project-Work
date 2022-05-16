package com.energent.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.sql.Date;
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

	Logger log = LoggerFactory.getLogger(this.getClass());
	
	
	@Autowired
	private AcademyRepository academyRepository;
	
	@Autowired
	private StudentRepository studentRepository;
	
	
	private static int check;
	
	
	//CREATION::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	@Override
	public Map<String,Academy> addAcademy(Academy academy) {
			
		Map<String,Academy> message = new HashMap<String,Academy>();
			
		if ( checkForms(academy) == true ) {
				
			if ( check == 1 ) {
					
				message.put("fill al forms", academy);
					
				return message;
			
			}
				
			if ( check == 2 ) {
					
				message.put("end date cannot be before start date", academy);
					
				return message;
					
			}
				
			if ( check == 4 ) {
				
				message.put("no special characters", academy);
				
				return message;
				
			}
				
			if ( check == 5 ) {
					
				message.put("no academy can exist before 2000-01-01", academy);
					
				return message;
					
			}
				
		}
			

		if ( academyRepository.existsById(academy.getCode()) == false) {
				
			message.put("academy saved",academyRepository.save(academy)); 
				
			return message;
				
		} else {
				
			message.put("academy already exists", academyRepository.findById(academy.getCode()).get()); 
				
			return message;
				
		}
					
	}
	//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	
	
	//READ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	@Override
	public Map<String,Academy> findAcademyById(String code) {
		
		Map<String,Academy> message = new HashMap<String,Academy>();
		
		
		if ( academyRepository.existsById(code) ) {
			
			message.put("academy found",academyRepository.findById(code).get()); 
			
			return message;
			
		} else {
			
			message.put("this academy does not exist",new Academy()); 
			
			return message;
			
		}
		
	}
	
	@Override
	public Map<String,List<Academy>> findAllAcademies() {
		
		Map<String,List<Academy>> message = new HashMap<String,List<Academy>>();
		
		
		if ( academyRepository.findAll().isEmpty() ) {
			
			List<Academy> noAcademies = new ArrayList<Academy>();
			
			noAcademies.add(new Academy());
			
			message.put("no academies aviable", noAcademies);
			
			return message;
			
		} else {
			
			message.put("academies found", academyRepository.findAll());
			return message;
			
		}
		
	}
	
	
	@Override
	public Map<String,List<Academy>> findAllAcademiesByLocation(String location) {
		
		Map<String,List<Academy>> message = new HashMap<String,List<Academy>>();
		
		List<Academy> academies = academyRepository.findByLocation(location);
		
		if ( academies.isEmpty() ) {
			
			academies.add( new Academy());
			
			message.put("no academy is in given location",academies); 
			
			return message;
			
		} else {
			
			academies = academyRepository.findByLocation(location);
			
			message.put("academies found", academies);
			
			return  message;
			
		}
		
	}
	//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	
	
	//UPDATE::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	@Override
	public Map<String,Academy> updateAcademy(Academy academy) {	
		
		Map<String,Academy> message = new HashMap<String,Academy>();
		
		if ( academyRepository.existsById(academy.getCode()) == true ) {
			
			message.put("academy updated",academyRepository.save(academy)); 
			
			return message;
				
		} else {
			
			message.put("academy does not exist, cannot update", academy); 
			
			return message;
			
		}
		
	}
	//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::	
	
	
	//DELETE:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	@Override
	public Map<String,String> removeAcademy(String code) {
		
		Map<String,String> message = new HashMap<String,String>();
		
		
		if ( academyRepository.existsById(code) ) {
			
			academyRepository.deletejoin(code);
		
		
			List<Student> students = studentRepository.findAll();
		
			List<String> fiscalCodesStudents = new ArrayList<>(); 
		
			for (Student student : students) {
			
				fiscalCodesStudents.add(student.getFiscalCode());
			
			}	
		

			List<String> fiscalCodesAcademyStudent = studentRepository.checkRelation();
		

			int isItThere = 0;
		

			for ( String codiceFiscale : fiscalCodesStudents) {
			
				for ( String fiscalCode : fiscalCodesAcademyStudent ) {
				
					if ( codiceFiscale.equals(fiscalCode)) {
					
						isItThere = 1;
					
					}
					
				}
			

				if ( isItThere == 0 ) {
				
					studentRepository.deleteById(codiceFiscale);
				
				}else {
				
					isItThere = 0;
				
				}
			
			}
		
			academyRepository.deleteById(code);
			
		} else {
			
			message.put("deletition", "academy does not exist - cannot delete");
			
			return message;
			
		}
		
		if ( academyRepository.existsById(code) ) {
			
			message.put("deletition", "cannot delete academy");
			
			return message;
			
		}else {
			
			message.put("deletition", "academy deleted");
			
			return message;
			
		}
		
	}
	//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	
	
	//ACADEMY METHODS::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	@Override
	public boolean iQuestionAcademyExistance ( String code ) {
		
		return academyRepository.existsById(code);
		
	}
	
	
	private boolean checkForms ( Academy academy ) {
		
		if ( academy.getCode() == "" || academy.getTitle() == "" || academy.getLocation() == "") {
		
			check = 1;
		
			return true;
		
		}
	
	
		if ( academy.getEndDate().before(academy.getStartDate()) ) {
		
			check = 2;
		
			return true;		
		
		}
	
	
		if ( academy.getStartDate().before(Date.valueOf("2000-01-01")) ){
		
			check = 5;
		
			return true;
		
		}
	
		Pattern pattern = Pattern.compile("[^a-zA-Z \\\\t\\\\n\\\\x0B\\\\f\\\\r]");
		Matcher matcher = pattern.matcher(academy.getTitle());
	
		if ( matcher.find() ){
		
			check = 4;
		
			return true;
		
		}
	
		matcher = pattern.matcher(academy.getLocation());
	
		if ( matcher.find() ){
		
			check = 4;
			
			return true;
		
	
		}
	
		check = 0;
	
		return false;

	}
	//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	

	
	
}