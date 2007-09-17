package br.com.medical.controller.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import org.hibernate.Query;
import org.hibernate.Session;

import br.com.medical.controller.intf.HistoricoController;
import br.com.medical.model.Historico;
import br.com.medical.to.UserTO;
import br.com.medical.util.HibernateConfigurator;

@Stateless
@Remote(HistoricoController.class)
@Local(HistoricoController.class)
public final class HistoricoBean implements HistoricoController {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2723566388761423332L;

	public HistoricoBean() {
		super();
	}

	/**
	 * Cria um histórico para a doença informada
	 * @param doenca
	 */
	@Override
	public void criarHistorico(String doenca) {
		Session session = HibernateConfigurator.getInstance().getSession();
		Historico historico = new Historico();
		historico.setDoenca(doenca);
		session.save(historico);
	}

	@Override
	public List<String> listarHistoricos(UserTO user) {
		Session session = HibernateConfigurator.getInstance().getSession();
		Query query = session.createQuery("select h from " + Historico.class.getName() + " h ");
		List<?> list = query.list();
		List<String> result = new ArrayList<String>(list.size());
		for (Iterator<?> iterator = list.iterator(); iterator.hasNext();) {
			Historico hist = (Historico) iterator.next();
			result.add(hist.getCodigo() + " - " + hist.getDoenca());
		}
		return result;
	}

}
