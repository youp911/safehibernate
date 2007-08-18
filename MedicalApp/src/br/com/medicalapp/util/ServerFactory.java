package br.com.medicalapp.util;

import br.com.medical.controller.HistoricoController;
import br.com.medical.controller.UserController;

public final class ServerFactory {

	private static ServerFactory instance;

	private ServerFactory() {

	}

	public static ServerFactory getInstance() {
		if (instance == null) {
			instance = new ServerFactory();
		}
		return instance;
	}

	public UserController getUserController() {
		return UserController.getInstance();
	}
	
	public HistoricoController getHistoricoController() {
		return HistoricoController.getInstance();
	}
}
