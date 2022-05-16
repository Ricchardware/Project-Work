
package com.energent.entity;

import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import com.fasterxml.jackson.annotation.JsonIgnore;


//Date.of();

@Entity
public class Academy {
	
	@Id
	@Column( nullable = false)
	private String code;
	
	private String title;
	
	private String location;
	
	@Column( name = "start_date" )		
	private Date startDate;
	
	@Column( name = "end_date" )
	private Date endDate;
	
	@JsonIgnore
	@ManyToMany ( mappedBy = "academies")
	private List<Student> students = new ArrayList<Student>();

	//Inizio metodi get e set:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	public String getCode() {
		
		return code;
		
	}

	public void setCode(String code) {
		
		this.code = code;
		
	}

	public String getTitle() {
		
		return title;
		
	}

	public void setTitle(String title) {
		
		this.title = title;
		
	}

	public String getLocation() {
		
		return location;
		
	}

	public void setLocation(String location) {
		
		this.location = location;
		
	}

	public Date getStartDate() {
		
		return startDate;
		
	}

	public void setStartDate(Date startDate) {
		
		this.startDate = startDate;
		
	}

	public Date getEndDate() {
		
		return endDate;
		
	}

	public void setEndDate(Date endDate) {
		
		this.endDate = endDate;
		
	}

	public List<Student> getStudents() {
		
		return students;
		
	}

	public void setStudents(List<Student> students) {
		
		this.students = students;
		
	}
	//Fine metodi get e set:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	
	
	public Academy() {}

	
	public Academy( String code, String title, String location, Date startDate, Date endDate ) {
		
		this.code = code;
		this.title = title;
		this.location = location;
		this.startDate = startDate;
		this.endDate = endDate;
		
	}

	@Override
	public String toString() {
		
		return "\n\nAcademy " + code + 
				"\n Titolo corso: " + title + 
				"\n Sede: " + location + 
				"\n Data inizio; " + startDate +
				"\n Fine corso: " + endDate + "\n\n";
		
	}

}