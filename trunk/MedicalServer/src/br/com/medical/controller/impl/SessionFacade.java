package br.com.medical.controller.impl;

import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateful;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import br.com.medical.controller.intf.HistoricoController;
import br.com.medical.controller.intf.SessionFacadeIntf;
import br.com.medical.controller.intf.UserController;
import br.com.medical.to.UserTO;

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
	public boolean login(UserTO userTO) {
		try {
			UserController userController = (UserController) this.ctx.lookup("MedicalApp/UserBean/local");
			boolean ok = userController.autenticate(userTO);
			if (ok) {
				this.userTO = userTO;
			}
			return ok;
		} catch (NamingException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void criarHistorico(String doenca) {
		try {
			HistoricoController historicoController = (HistoricoController) this.ctx.lookup("MedicalApp/HistoricoBean/local");
			historicoController.criarHistorico(doenca);
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
	
}
