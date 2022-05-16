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

import com.energent.entity.Academy;
import com.energent.service.AcademyService;


@CrossOrigin 
@RestController
@RequestMapping ( "/academy-service")
public class AcademyController {

	Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private AcademyService academyService;

	
	
	//READ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	@GetMapping("/academies")
	public Map<String,List<Academy>> showAllAcademies() {

		return academyService.findAllAcademies();


	}
	
	@GetMapping("/academies/location/{location}")
	public Map<String,List<Academy>> showAllAcademiesByLocation( @PathVariable String location) {

		return academyService.findAllAcademiesByLocation(location);


	}
	//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

	
	//ADD:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	@PostMapping("/academies/add")
	public Map<String,Academy> addAcademy(@RequestBody Academy academy) {

		return academyService.addAcademy(academy);
		
	}
	//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	
	
	//UPDATE::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	@PutMapping("/{code}/update")
	public Map<String,Academy> updateAcademy(@RequestBody Academy academy,
			@PathVariable String code ) {
		
		return academyService.updateAcademy(academy);

	}
	//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	
	
	//DELETE::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	@DeleteMapping("/{code}/remove")
	public Map<String,String> removeAcademy(@PathVariable String code) {
		
		return academyService.removeAcademy(code);
		
	}
	//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

}
