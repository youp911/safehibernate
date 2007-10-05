package br.com.medical.model;

import java.util.Date;

public abstract class Person {

	private int document;
	
	private String firstName;
	private String lastName;
	private Date birthDate;

	private User user;

	protected Person() {
		// Used by hibernate
	}

	public Person(String firstName, String lastName, int document) {
		setDocument(document);
		setFirstName(firstName);
		setLastName(lastName);
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getBirthDate() {
		return this.birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public int getDocument() {
		return this.document;
	}

	public void setDocument(int document) {
		this.document = document;
	}

	public String getFullName() {
		return this.getFirstName() + this.getLastName();
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
