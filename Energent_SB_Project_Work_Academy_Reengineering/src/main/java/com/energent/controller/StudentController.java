package com.energent.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.energent.entity.Student;
import com.energent.service.StudentService;


@CrossOrigin
@RestController
@RequestMapping ( "/student-service" )
public class StudentController {
		
	Logger log = LoggerFactory.getLogger(this.getClass());
		

	@Autowired
	private StudentService studentService;
		
	
	//READ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	@GetMapping("/{code}/students")
	public Map<String,List<Student>> showAllStudentsInXAcademy( @PathVariable String code) {
			
		return studentService.findAllStudentsInXAcademy(code);
			
	}
	
	@GetMapping("/students/outside/{code}")
	public Map<String,List<Student>> showAllStudentsOutsideXAcademy( @PathVariable String code) {
			
		return studentService.findAllStudentsOutsideXAcademy(code);
			
	}
	
	@GetMapping("/students")
	public Map<String,List<Student>> showAllStudents( ) {
		
		return studentService.findAllStudents();
			
	}
	
	@GetMapping("/students/age/{age}")
	public Map<String,List<Student>> showAllStudentsWithAgeGreaterThanX( @PathVariable int age) {
		
		return studentService.findStudentByAgeGreaterThanX(age);
			
	}
	//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	
	
	//ADD:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	@PostMapping("/{code}/students/add")
	public Map<String,Student> addStudent( @RequestBody Student student, @PathVariable String code ) {
			
		return studentService.addStudent(student, code);
			
	}
	//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	
	
	//UPDATE::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	@PutMapping("/{code}/students/update/{fiscalCode}")
	public Map<String,Student> updateStudent( @RequestBody Student student,
			@PathVariable String code, @PathVariable String fiscalCode ) {
		
		return studentService.updateStudent(student, code);
			
	}
	//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	
	
	//DELETE::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	@DeleteMapping("/{code}/students/remove/{fiscalCode}")
	public Map<String,String> removeStudent(@PathVariable String fiscalCode,
			@PathVariable String code) {
		
		return studentService.removeStudent(fiscalCode, code);
		
	}
	//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
}
