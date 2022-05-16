
package com.energent.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Student {
	
	@Column( name = "first_name" )	
	private String firstName;
	
	@Column( name = "last_name" )	
	private String lastName;
	
	private int age;
	
	@Id
	@Column( name = "fiscal_code", length = 16 )
	private String fiscalCode;
	
	@JsonIgnore
	@ManyToMany
	@JoinTable ( name = "academy_student",
	 joinColumns = @JoinColumn ( name = "student_id" ),
	 inverseJoinColumns = @JoinColumn( name = "academy_id"),
	 uniqueConstraints=@UniqueConstraint(columnNames= {"student_id","academy_id"}))
	private List<Academy> academies = new ArrayList<Academy>();

	
	//Inizio metodi get e set:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	public String getFirstName() {
		
		return firstName;
		
	}

	public void setFirstName(String firstName) {
		
		this.firstName = firstName;
		
	}

	public String getLastName() {
		
		return lastName;
		
	}

	public void setLastName(String lastName) {
		
		this.lastName = lastName;
		
	}

	public int getAge() {
		
		return age;
		
	}

	public void setAge(int age) {
		
		this.age = age;
		
	}

	public String getFiscalCode() {
		
		return fiscalCode;
		
	}

	public void setFiscalCode(String fiscalCode) {
		
		this.fiscalCode = fiscalCode;
		
	}

	public List<Academy> getAcademies() {
		
		return academies;
		
	}

	public void setAcademies(List<Academy> academies) {
		
		this.academies = academies;
		
	}
	//Fine metodi get e set:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	
	
	public Student () {}

	public Student(String firstName, String lastName, int age, String fiscalCode) {
		
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.fiscalCode = fiscalCode;
		
	}

	@Override
	public String toString() {
		
		return "\n\nNome: " + firstName + 
				"\n Cognome: " + lastName + 
				"\n Età: " + age + 
				"\n Codice Fiscale: " + fiscalCode + "\n\n";
		
	}
	
	
	
	
	
	

}