package com.energent.service;

import java.util.List;
import java.util.Map;

import com.energent.entity.Student;

public interface StudentService {
	
	//CREATE
	public Map<String, Student> addStudent(Student student, String code);
	
	//READ
	public Map<String, Student> findStudentById(String fiscalCode);
	
	//READ
	public Map<String, List<Student>> findAllStudentsInXAcademy( String academyCode);
	
	//READ
	Map<String, List<Student>> findAllStudentsOutsideXAcademy(String academyCode);
		
	//READ
	public Map<String, List<Student>> findAllStudents();
	
	//READ
	public Map<String, List<Student>> findStudentByAgeGreaterThanX(int age);
		
	//UPDATE
	public Map<String, Student> updateStudent( Student student, String code);
	
	//DELETE
	public Map<String, String> removeStudent(String fiscalCode, String code);

	
}

