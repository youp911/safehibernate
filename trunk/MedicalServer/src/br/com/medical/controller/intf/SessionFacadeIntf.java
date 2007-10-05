package br.com.medical.controller.intf;

import java.util.List;

import javax.naming.CommunicationException;

import br.com.medical.to.PersonTO;
import br.com.medical.to.UserTO;

public interface SessionFacadeIntf {

	boolean login(UserTO userTO) throws CommunicationException;
	
	void criarHistorico(String description, PersonTO patient);
	
	List<String> listarDoencas();
	
	PersonTO getUserInformation(String username);
}
