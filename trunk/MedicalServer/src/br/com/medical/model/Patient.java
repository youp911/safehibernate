package br.com.medical.model;

import java.util.Collections;
import java.util.Set;

import org.jboss.util.collection.ListSet;

public class Patient extends Person {

	private Doctor doctor;

	public Doctor getDoctor() {
		return this.doctor;
	}

	protected void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}
	
	private Set<History> history = new ListSet();
	
	public Set<History> getHistory() {
		return Collections.unmodifiableSet(this.history);
	}
	
	public void addHistory(History history) {
		this.history.add(history);
	}
	
	@SuppressWarnings("unchecked")
	protected void setHistory(Set history) {
		this.history = history;
	}
}
