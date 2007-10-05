package br.com.medical.model;

import java.io.Serializable;

import br.com.safehibernate.interceptor.EncryptedField;

public class History implements Serializable {

	private static final long serialVersionUID = -7415123767728136038L;

	private Integer code;

	private Doctor doctor;
	
	private Patient patient;
	
	@EncryptedField
	private String description;

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getCode() {
		return this.code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public Doctor getDoctor() {
		return this.doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	public Patient getPatient() {
		return this.patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

}
