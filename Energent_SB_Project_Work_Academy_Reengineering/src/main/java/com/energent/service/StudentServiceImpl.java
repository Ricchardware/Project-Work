package com.energent.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.energent.entity.Student;
import com.energent.repository.StudentRepository;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {
	
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	
	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private AcademyService academyService;
	
	
	private static int check = 0;
	
	
	//CREATION:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	@Override
	public Map<String,Student> addStudent(Student student, String code) {
		
		Map<String,Student> message = new HashMap<String,Student>();
		
		
		if ( checkForms(student) == true ) {
			
			if ( check == 1 ) {
				
				message.put("fill al forms", student);
				
				return message;
				
			}
			
			if ( check == 3 ) {
				
				message.put("minimum age is 16", student);
				
				return message;
				
			}
			
			if ( check == 4 ) {
				
				message.put("no special characters", student);
				
				return message;
				
			}
			
		} 
		
		if ( checkAllStudents(code, student.getFiscalCode()) == false ) {
			
			if ( studentRepository.existsById(student.getFiscalCode()) == false ) {
				
				message.put("student saved", studentRepository.save(student));
				
				studentRepository.insertJoin(student.getFiscalCode(), code);
				
				return message;
				
			} else {
			
				Student studentToUpdate = studentRepository.findById(student.getFiscalCode()).get();
					
				studentRepository.insertJoin(student.getFiscalCode(), code);
					
				studentToUpdate.setFirstName(student.getFirstName());
				studentToUpdate.setLastName(student.getLastName());
				studentToUpdate.setAge(student.getAge());
					
					
				message.put("student already in another academy - student saved", 
					studentRepository.save(studentToUpdate));
					
					
				return message;
					
			}
				
			} else {
				
				message.put("student already in this academy", studentRepository.findById(
					student.getFiscalCode()).get());
				
				return message;
			
			}
		
		
	}
	//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	
	
	//READ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	@Override
	public Map<String,Student> findStudentById(String fiscalCode) {
		
		Map<String,Student> message = new HashMap<String,Student>();
		
		
		if ( studentRepository.existsById(fiscalCode) ) {
			
			message.put("student found", studentRepository.findById(fiscalCode).get());
			
			return message;
			
		} else {
			
			message.put("this student does not exist",new Student()); 
			
			return message;
			
		}
		
	}
	
	@Override
	public Map<String,List<Student>> findStudentByAgeGreaterThanX(int age) {
		
		Map<String,List<Student>> message = new HashMap<String,List<Student>>();
		
		List<Student> students = studentRepository.findByAgeGreaterThan(age);
		
		if ( students.isEmpty() ) {
			
			students.add( new Student());
			
			message.put("students not found", students);
			
			return message;
			
		} else {
			
			message.put("students found",students); 
			
			return message;
			
		}
		
	}
	
	@Override
	public Map<String,List<Student>> findAllStudentsInXAcademy( String academyCode ) {
		
		Map<String,List<Student>> message = new HashMap<String,List<Student>>();
		

		if (  academyService.findAcademyById(academyCode).
				get("academy found").getStudents().isEmpty() ) {
			
			List<Student> noStudents = new ArrayList<Student>();
			
			noStudents.add( new Student ());
			
			message.put("no students aviable", noStudents);
			
			return message;
			
			
		} else {
			
			message.put("students found", academyService.findAcademyById(academyCode).
					get("academy found").getStudents());
			
			return message;
			
		}

	}
	
	@Override
	public Map<String,List<Student>> findAllStudentsOutsideXAcademy( String academyCode ) {
		
		Map<String,List<Student>> message = new HashMap<String,List<Student>>();
		
		List<String> studentsOutside = studentRepository.outside(academyCode);
		
		List<Student> students = new ArrayList<Student>();
				
		if (  studentsOutside.isEmpty() ) {
			
			students.add( new Student());
			
			message.put("no students outside this academy", students);
			
			return message;
			
			
		} else {
			
			for ( int i = 0; i < studentsOutside.size(); i++ ) {
				
				if ( studentRepository.lookForSomeoneHere(academyCode, 
						(studentsOutside.get(i))).size() == 0 ){
					
					students.add(studentRepository.findById(studentsOutside.get(i)).get());
					
				}
				
			}
			
			message.put("students found", students);
			
			
			
			return message;
			
		}

	}
	
	@Override
	public Map<String,List<Student>> findAllStudents( ) {
		
		Map<String,List<Student>> message = new HashMap<String,List<Student>>();
		
		if (  studentRepository.findAll().isEmpty() ) {
			
			List<Student> noStudents = new ArrayList<Student>();
			
			noStudents.add( new Student ());
			
			message.put("no students aviable", noStudents);
			
			return message;
			
			
		} else {
			
			message.put("students found", studentRepository.findAll());
			
			return message;
			
		}

	}
	//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	
	
	//UPDATE::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
		@Override
	public Map<String,Student> updateStudent(Student student, String code ) {
		
		Map<String,Student> message = new HashMap<String,Student>();
		
		if ( checkForms(student) == true ) {
			
			if ( check == 1 ) {
				
				message.put("fill al forms", student);
				
				return message;
				
			}
			
			if ( check == 3 ) {
				
				message.put("minimum age is 16", student);
				
				return message;
				
			}
			
			if ( check == 4 ) {
				
				message.put("no special characters", student);
				
				return message;
				
			}
			
		}
			
		if ( studentRepository.existsById(student.getFiscalCode()) == true ) {
			
			if ( checkAllStudents(code, student.getFiscalCode()) == true ) {
				
				Student studentToUpdate = studentRepository.findById(student.getFiscalCode()).get();
					
				studentToUpdate.setFirstName(student.getFirstName());
				studentToUpdate.setLastName(student.getLastName());
				studentToUpdate.setAge(student.getAge());
				
				message.put("student updated", studentRepository.save(studentToUpdate));
					
					
				return message;

				
			} else {
				
				message.put("student does not exist, cannot update", student);
				
				return message;
			}
			
		} else {
			
			message.put("student does not exist, cannot update", student);
			
			return message;
		}
		
	}
	//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

	
	//DELETE::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	@Override
	public Map<String,String> removeStudent(String fiscalCode, String code) {
		
		Map<String,String> message = new HashMap<String,String>();
		
		if ( studentRepository.existsById(fiscalCode) ) {
			
		
			studentRepository.deletejoin(fiscalCode, code);

			int isItThere = 0;
		

			List<String> fiscalCodes = studentRepository.checkRelation();
		
			for ( String codiceFiscale : fiscalCodes) {
			
				if ( fiscalCode.equals(codiceFiscale)) {
				
					isItThere = 1;
				
				}
			
			}
		
			if ( isItThere == 0 ) {
			
				studentRepository.deleteById(fiscalCode);
			
			}
			
		} else {
			
			message.put("deletiton", "student does not exist");
			
			return message;
			
		}
		
		if ( studentRepository.existsById(fiscalCode) ) {
			
			
			
			message.put("deletiton", "student still in databAse cause still in another accademy"
					+ " - student deleted from this academy");
			
			return message;
			
		} else {
			
			
			
			message.put("deletiton", "student completely deleted cause not in any academy anymore");
			
			return message;
			
		}

	}
	//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	
	
	//STUDENT METHODS::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	private boolean checkAllStudents( String code, String fiscalCode) {
		
		List<Student> students = new ArrayList<Student>();
		
		if ( academyService.iQuestionAcademyExistance(code)) {
			
			students = (academyService.findAcademyById(code)).get("academy found").getStudents();
			
		} else {
			
			return false;
			
		}
		
		
		for( Student student: students) {

			if ( student.getFiscalCode().equals(fiscalCode)) {

				return true;
				
			}
			
		}
		
		return false;
		
	}
	
		private boolean checkForms ( Student student ) {
		
		if ( student.getFirstName() == "" || student.getLastName() == "" 
				|| student.getFiscalCode() == "" || student.getAge() == 0) {
			
			check = 1;
			
			return true;
			
		}
		
		
		if ( student.getAge() < 16 ) {
			
			check = 3;
			
			return true;	
			
		}
		
		Pattern pattern = Pattern.compile("[^a-zA-Z]");
		Matcher matcher = pattern.matcher(student.getFirstName());
		
		if ( matcher.find() ){
			
			check = 4;
			
			return true;
			
		}
		
		matcher = pattern.matcher(student.getLastName());
		
		if ( matcher.find() ){
			
			check = 4;
			
			return true;
			
		}
		
		pattern = Pattern.compile("[^a-zA-Z0-9]");
		matcher = pattern.matcher(student.getFiscalCode());
		
		if ( matcher.find() ){
			
			check = 4;
			
			return true;
			
		}
		
		check = 0;
		
		return false;
	
	}
	//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
		

}