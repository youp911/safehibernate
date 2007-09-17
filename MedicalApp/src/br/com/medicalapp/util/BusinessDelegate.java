package br.com.medicalapp.util;

import java.util.List;

import br.com.medical.controller.intf.SessionFacadeIntf;
import br.com.medical.to.UserTO;

public class BusinessDelegate {

	private static BusinessDelegate instance = null;
	
	private SessionFacadeIntf sessionFacade = null;
	
	private UserTO userTO = null;
	
	private BusinessDelegate() {
		//private constructor 
	}
	
	public boolean login(UserTO userTO) {
		boolean autenticate = getSessionFacade().login(userTO);
		if (autenticate) {
			this.userTO = userTO;
		}
		return autenticate;
	}
	
	public void createHistory(String doenca) {
		getSessionFacade().criarHistorico(doenca);
	}
	
	public List<String> listHistorics() {
		return getSessionFacade().listarDoencas();
	}
	
	public static BusinessDelegate getInstance() {
		if (instance == null) {
			instance = new BusinessDelegate();
		}
		return instance;
	}
	
	private SessionFacadeIntf getSessionFacade() {
		if (this.sessionFacade == null) {
			this.sessionFacade = ServerFactory.getInstance().getSessionFacade();
		}
		return this.sessionFacade;
	}
	
}
