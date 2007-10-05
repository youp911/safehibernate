package br.com.medical.controller.impl;

import java.io.IOException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import org.hibernate.Query;
import org.hibernate.Session;

import sun.misc.BASE64Decoder;
import br.com.medical.controller.intf.UserController;
import br.com.medical.model.User;
import br.com.medical.to.UserTO;
import br.com.medical.util.MedicalCertificateProvider;
import br.com.medical.util.HibernateConfigurator;

/**
 * Controlador de usu�rio centralizando:
 * <ol>
 * <li>Autentica��o</li>
 * </ol>
 * @author jean
 */
@Stateless
@Remote(UserController.class)
@Local(UserController.class)
public class UserBean implements UserController {

	public UserBean() {
	}

	/**
	 * Realiza a autentica��o do usu�rio indicando se a senha fornecida pelo
	 * mesmo � v�lida.
	 * @param userTO Transfer Object com as informa��es do usu�rio
	 * @return indicador de autentica��o
	 */
	public boolean autenticate(UserTO userTO) {

		String queryString = "from " + User.class.getName()
				+ " where UPPER(name) = UPPER(:name) and password = :password";

		Session session = HibernateConfigurator.getInstance().getSession();
		Query query = session.createQuery(queryString);

		query.setString("name", userTO.name);
		query.setString("password", userTO.password);
		User user = (User) query.uniqueResult();

		if (user != null) {
			byte[] bytePublic = user.getPublicKey();
			byte[] bytePrivate = user.getPrivateKey();

			PublicKey publicKey = null;// ???
			PrivateKey privateKey = null;// ????
			try {
				
				BASE64Decoder bd = new BASE64Decoder();
				KeyFactory kf = KeyFactory.getInstance("RSA");
				if (bytePublic != null) {
					bytePublic = bd.decodeBuffer(new String(bytePublic));
					X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(
							bytePublic);
					publicKey = kf.generatePublic(publicKeySpec);
				}
				if (bytePrivate != null) {
					bytePrivate = bd.decodeBuffer(new String(bytePrivate));
					PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(
							bytePrivate);
					privateKey = kf.generatePrivate(privateKeySpec);
				}
			} catch (NoSuchAlgorithmException e) {
				throw new RuntimeException(e);
			} catch (InvalidKeySpecException e) {
				throw new RuntimeException(e);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}

			if (publicKey != null && privateKey != null) {
				KeyPair keyPair = new KeyPair(publicKey, privateKey);
				userTO.keyPair = keyPair;
				MedicalCertificateProvider.threadLocal.set(userTO);
			}
		}

		return user != null;
	}

}