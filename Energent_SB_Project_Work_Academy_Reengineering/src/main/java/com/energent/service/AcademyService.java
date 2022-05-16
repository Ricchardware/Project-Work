package com.energent.service;

import java.util.List;
import java.util.Map;

import com.energent.entity.Academy;

public interface AcademyService {
	
	//CREATE
	public Map<String, Academy> addAcademy(Academy academy);
	
	//READ
	Map<String, List<Academy>> findAllAcademiesByLocation(String location);
	
	//READ
	public Map<String, List<Academy>> findAllAcademies();
		
	//READ
	public Map<String, Academy> findAcademyById(String code);
	
	//UPDATE
	public Map<String, Academy> updateAcademy(Academy academy);
	
	//DELETE
	public Map<String, String> removeAcademy(String code);
	
	//ACADEMY METHOD
	boolean iQuestionAcademyExistance(String code);
	
	

	
}
