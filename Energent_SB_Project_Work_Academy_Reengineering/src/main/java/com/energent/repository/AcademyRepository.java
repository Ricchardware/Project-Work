package com.energent.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.energent.entity.Academy;

public interface AcademyRepository extends JpaRepository<Academy , String> {
	
	/*
	 * Query che ci permette di cancellare le relazioni tra studenti e accademie attraverso una
	 * data accademia
	 */
	@Modifying
	@Transactional
	@Query(value= "DELETE FROM academy_student WHERE academy_id = ?1",
	nativeQuery = true)
	void deletejoin( String academyCode);
	
	/*
	 * Query che ci permette di leggere tutti gli studenti dentro la tabella academy_student; questa query
	 * verrà utilizzata per dei controlli nella service
	 */
	@Modifying
	@Transactional
	@Query(value="SELECT `student_id` FROM `academy_student`",
	nativeQuery=true)
	List<String> checkRelation();
	
	
	List<Academy> findByLocation(String location);

}
