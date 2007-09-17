package br.com.medical.controller.intf;

import java.util.List;

import br.com.medical.to.UserTO;

public interface SessionFacadeIntf {

	boolean login(UserTO userTO);
	
	void criarHistorico(String doenca);
	
	List<String> listarDoencas();
}
