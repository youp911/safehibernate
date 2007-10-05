package br.com.medical.controller.intf;

import java.util.List;

import javax.ejb.Remote;

import br.com.medical.model.Doctor;
import br.com.medical.model.Patient;
import br.com.medical.to.UserTO;

@Remote
public interface HistoricoController {

	void criarHistorico(String description, Patient patient, Doctor doctor);
	
	List<String> listarHistoricos(UserTO user);

}
