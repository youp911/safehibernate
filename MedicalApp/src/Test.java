import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import br.com.safehibernate.interceptor.Interceptor;

public class Test {

	public static void main(String[] args) {
		try {
			// SessionFactory deve ser criado uma única vez durante a execução
			// da aplicação
			Configuration cf = new org.hibernate.cfg.Configuration().configure();
			//.configure("hibernate.cfg.xml");
			cf.setProperty("safeHibernate.wrappedDialect", "org.hibernate.dialect.MySQLDialect");
			
			SessionFactory sf = cf 
					.buildSessionFactory();
			
			Interceptor interceptor = new Interceptor();
			
			
			Session session = sf.openSession(interceptor); // Abre sessão
			Transaction tx = session.beginTransaction(); // Cria transação

			// Cria objeto Aluno
			Pessoa pessoa = new Pessoa();
			pessoa.setCodigo(1);
			pessoa.setNome("Jean Carlos");
			session.saveOrUpdate(pessoa); // Realiza persistência
			tx.commit(); // Fecha transação
			session.close(); // Fecha sessão
			

		} catch (HibernateException e1) {
			e1.printStackTrace();
		}
	}

}
