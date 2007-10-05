package br.com.medical.controller.impl;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import org.hibernate.Query;
import org.hibernate.Session;

import br.com.medical.controller.intf.PatientController;
import br.com.medical.model.Patient;
import br.com.medical.model.Person;
import br.com.medical.to.PersonTO;
import br.com.medical.util.HibernateConfigurator;

@Stateless
@Remote(PatientController.class)
@Local(PatientController.class)
public class PatientBean implements PatientController {

	@Override
	public PersonTO getPatientDetails(String name) {
		StringBuilder sb = new StringBuilder("from ");
		sb.append(Patient.class.getName());
		sb.append(" where UPPER(firstName) like UPPER(:firstName)");
		Session session = HibernateConfigurator.getInstance().getSession();
		
		Query query = session.createQuery(sb.toString());

		query.setString("firstName", name);
		
		Person person = (Person) query.uniqueResult();
		if (person != null) {
			PersonTO personTO = new PersonTO();
			personTO.firstName = person.getFirstName();
			personTO.lastName = person.getLastName();
			personTO.document = person.getDocument();
			personTO.birthDate = person.getBirthDate();
			return personTO;
		}
		return null;
	}

}
