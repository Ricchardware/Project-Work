package com.energent.service;

import java.util.List;

import com.energent.entity.Student;

public interface StudentService {
	
	public Student findStudentById(String fiscalCode);
	
	public String addStudent(Student student, String code);
	
	public String updateStudent( Student student, String code);
	
	public void removeStudent(String fiscalCode, String code);

	public List<Student> findAllStudents( String academyCode);
	
	public int getSituation();
	
	public void setSituation(int situationSet);
}

