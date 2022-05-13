package com.energent.service;

import java.util.List;
import com.energent.entity.Academy;

public interface AcademyService {

	public String addAcademy(Academy academy);
	
	public String updateAcademy(Academy academy);

	public void removeAcademy(String code);
	
	public List<Academy> findAllAcademies();
	
	public Academy findAcademyById(String code);
	
	public int getSituation();
	
	public void setSituation( int situationSetting );
	
}
