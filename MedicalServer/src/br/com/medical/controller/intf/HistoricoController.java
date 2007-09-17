package br.com.medical.controller.intf;

import java.util.List;

import javax.ejb.Remote;

import br.com.medical.to.UserTO;

@Remote
public interface HistoricoController {

	void criarHistorico(String doenca);
	
	List<String> listarHistoricos(UserTO user);

}
