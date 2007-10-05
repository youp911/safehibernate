package br.com.medical.controller.impl;

import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateful;
import javax.naming.CommunicationException;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.hibernate.Session;

import br.com.medical.controller.intf.HistoricoController;
import br.com.medical.controller.intf.PatientController;
import br.com.medical.controller.intf.SessionFacadeIntf;
import br.com.medical.controller.intf.UserController;
import br.com.medical.model.Doctor;
import br.com.medical.model.Patient;
import br.com.medical.model.User;
import br.com.medical.to.PersonTO;
import br.com.medical.to.UserTO;
import br.com.medical.util.HibernateConfigurator;

@Stateful
@Remote(SessionFacadeIntf.class)
public class SessionFacade implements SessionFacadeIntf {

	private UserTO userTO;
	private InitialContext ctx = null;
	
	public SessionFacade() {
		try {
			this.ctx = new InitialContext();
		} catch (NamingException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public boolean login(UserTO userTO) throws CommunicationException {
		try {
			UserController userController = (UserController) this.ctx.lookup("MedicalApp/UserBean/local");
			boolean ok = userController.autenticate(userTO);
			if (ok) {
				this.userTO = userTO;
			}
			return ok;
		} catch (CommunicationException e) {
			throw e;
		} catch (NamingException e) {
			throw new RuntimeException(e);
		} 
	}

	@Override
	public void criarHistorico(String description, PersonTO person) {
		try {
			Session session = HibernateConfigurator.getInstance().getSession();
			Patient patient = (Patient) session.load(Patient.class.getName(), person.document);
			User user = (User) session.load(User.class, this.userTO.name);
			if (!(user.getPerson() instanceof Doctor)) {
				throw new IllegalStateException("You are not a doctor and you can`t create a new History");
			}
			Doctor doctor = (Doctor) user.getPerson();
			
			HistoricoController historicoController = (HistoricoController) this.ctx.lookup("MedicalApp/HistoricoBean/local");
			historicoController.criarHistorico(description, patient, doctor);
		} catch (NamingException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<String> listarDoencas() {
		HistoricoController historicoController;
		try {
			historicoController = (HistoricoController) this.ctx.lookup("MedicalApp/HistoricoBean/local");
			return historicoController.listarHistoricos(this.userTO);
		} catch (NamingException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public PersonTO getUserInformation(String username) {
		try {
			PatientController userController = (PatientController) this.ctx.lookup("MedicalApp/PatientBean/local");
			return userController.getPatientDetails(username);
		} catch (NamingException e) {
			throw new RuntimeException(e);
		}
	}
	
}
