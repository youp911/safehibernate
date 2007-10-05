package br.com.medical.controller.intf;

import javax.ejb.Remote;

import br.com.medical.to.PersonTO;

@Remote
public interface PatientController {

	/**
	 * Find de person details based on the person name
	 * @param name
	 * @return PersonTO
	 */
	PersonTO getPatientDetails(String name);
	
}
