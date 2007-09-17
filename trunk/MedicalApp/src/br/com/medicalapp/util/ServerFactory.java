package br.com.medicalapp.util;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import br.com.medical.controller.intf.SessionFacadeIntf;

public final class ServerFactory {

	private static final String SERVER_PATH = "MedicalApp/"; 
	
	private static ServerFactory instance;
	
	private final InitialContext ctx;

	private ServerFactory() {
		 try {
			this.ctx = new InitialContext();
		} catch (NamingException e) {
			throw new RuntimeException(e);
		}
	}

	public static ServerFactory getInstance() {
		if (instance == null) {
			instance = new ServerFactory();
		}
		return instance;
	}

	public SessionFacadeIntf getSessionFacade() {
		try {
			return (SessionFacadeIntf) this.ctx.lookup(SERVER_PATH + "SessionFacade/remote");
		} catch (NamingException e) {
			throw new RuntimeException(e);
		}
	}

}
