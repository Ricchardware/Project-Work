package com.energent.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.energent.entity.Student;

public interface StudentRepository extends JpaRepository<Student, String>{
	
	/*
	 * Query che ci permette di relazionare uno studente ad un accademia
	 */
	@Modifying
	@Query(value="INSERT INTO academy_student (student_id, academy_id) VALUES (?1, ?2)",
	nativeQuery = true)
	void insertJoin( String studentFiscalCode, String academyCode);
	
	/*
	 * Query che ci permette di cancellare una relazione tra un accademia e uno studente specifici
	 */
	@Modifying
	@Transactional
	@Query(value= "DELETE FROM academy_student WHERE student_id = ?1 AND academy_id = ?2",
	nativeQuery = true)
	void deletejoin( String studentFiscalCode, String academyCode);
	
	/*
	 * Query che ci permette di leggere tutti i codici fiscali dalla tabella academy_student
	 */
	@Modifying
	@Transactional
	@Query(value="SELECT `student_id` FROM `academy_student`",
	nativeQuery=true)
	List<String> checkRelation();
	
}