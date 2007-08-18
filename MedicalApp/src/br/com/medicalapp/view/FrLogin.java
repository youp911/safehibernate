package br.com.medicalapp.view;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.jdesktop.layout.GroupLayout;
import org.jdesktop.layout.LayoutStyle;

import br.com.medical.to.UserTO;
import br.com.medicalapp.util.ServerFactory;

/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class FrLogin extends javax.swing.JDialog {

	{
		//Set Look & Feel
		try {
			javax.swing.UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	private JLabel jLabel1;
	private JTextField edtName;
	private JLabel lbCertificate;
	private JButton edtFindCertificate;
	private JTextField edtCertificate;
	private JPasswordField edtPassword;
	private JButton btnOk;
	private JButton btnCancel;
	private JSeparator jSeparator1;
	private JLabel lblPassword;
	private File file;

	/**
	* Auto-generated main method to display this JDialog
	*/
	public static void main(String[] args) {
		FrLogin inst = new FrLogin(null);
		inst.setVisible(true);
	}
	
	public FrLogin(JFrame frame) {
		super(frame);
		initGUI();
	}
	
	private void initGUI() {
		try {
			GroupLayout thisLayout = new GroupLayout(
				(JComponent) getContentPane());
			getContentPane().setLayout(thisLayout);
			this.setPreferredSize(new java.awt.Dimension(347, 181));
			this.setResizable(false);
			this.setTitle("Login");
			this.setModal(true);
			this.setAlwaysOnTop(true);
			this.setLocationByPlatform(true);
			{
				jLabel1 = new JLabel();
				jLabel1.setText("name:");
			}
			{
				edtName = new JTextField();
				jLabel1.setLabelFor(edtName);
			}
			{
				lblPassword = new JLabel();
				lblPassword.setText("password:");
			}
			{
				jSeparator1 = new JSeparator();
			}
			{
				btnCancel = new JButton();
				btnCancel.setText("cancel");
				btnCancel.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						btnCancelActionPerformed(evt);
					}
				});
			}
			{
				btnOk = new JButton();
				btnOk.setText("Ok");
				btnOk.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						btnOkActionPerformed(evt);
					}
				});
			}
			{
				lbCertificate = new JLabel();
				lbCertificate.setText("certificate:");
			}
			{
				edtCertificate = new JTextField();
			}
			{
				edtFindCertificate = new JButton();
				edtFindCertificate.setText("...");
				edtFindCertificate.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						btnFindCertificatePerformed(evt);
					}
				});
			}
			{
				edtPassword = new JPasswordField();
			}
			this.setSize(347, 181);
			thisLayout.setVerticalGroup(thisLayout.createSequentialGroup()
			    .addContainerGap()
			    .add(thisLayout.createParallelGroup()
			        .add(GroupLayout.LEADING, edtName, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
			        .add(GroupLayout.LEADING, thisLayout.createSequentialGroup()
			            .add(2)
			            .add(jLabel1, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)))
			    .add(14)
			    .add(thisLayout.createParallelGroup()
			        .add(GroupLayout.LEADING, edtPassword, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
			        .add(GroupLayout.LEADING, thisLayout.createSequentialGroup()
			            .add(1)
			            .add(lblPassword, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)))
			    .add(13)
			    .add(thisLayout.createParallelGroup()
			        .add(GroupLayout.LEADING, thisLayout.createParallelGroup()
			            .add(GroupLayout.BASELINE, edtCertificate, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
			            .add(GroupLayout.BASELINE, edtFindCertificate, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE))
			        .add(GroupLayout.LEADING, thisLayout.createSequentialGroup()
			            .add(1)
			            .add(lbCertificate)))
			    .addPreferredGap(LayoutStyle.RELATED)
			    .add(jSeparator1, GroupLayout.PREFERRED_SIZE, 3, GroupLayout.PREFERRED_SIZE)
			    .addPreferredGap(LayoutStyle.RELATED)
			    .add(thisLayout.createParallelGroup()
			        .add(GroupLayout.BASELINE, btnCancel)
			        .add(GroupLayout.BASELINE, btnOk))
			    .addContainerGap(22, 22));
			thisLayout.setHorizontalGroup(thisLayout.createSequentialGroup()
			    .addContainerGap()
			    .add(thisLayout.createParallelGroup()
			        .add(GroupLayout.LEADING, jSeparator1, GroupLayout.PREFERRED_SIZE, 314, GroupLayout.PREFERRED_SIZE)
			        .add(thisLayout.createSequentialGroup()
			            .add(thisLayout.createParallelGroup()
			                .add(GroupLayout.LEADING, lbCertificate, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
			                .add(thisLayout.createSequentialGroup()
			                    .add(2)
			                    .add(thisLayout.createParallelGroup()
			                        .add(GroupLayout.LEADING, jLabel1, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE)
			                        .add(GroupLayout.LEADING, lblPassword, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE))))
			            .add(3)
			            .add(thisLayout.createParallelGroup()
			                .add(GroupLayout.TRAILING, edtName, GroupLayout.PREFERRED_SIZE, 246, GroupLayout.PREFERRED_SIZE)
			                .add(GroupLayout.TRAILING, edtPassword, GroupLayout.PREFERRED_SIZE, 246, GroupLayout.PREFERRED_SIZE)
			                .add(GroupLayout.LEADING, thisLayout.createSequentialGroup()
			                    .add(edtCertificate, GroupLayout.PREFERRED_SIZE, 216, GroupLayout.PREFERRED_SIZE)
			                    .addPreferredGap(LayoutStyle.RELATED))
			                .add(GroupLayout.LEADING, thisLayout.createSequentialGroup()
			                    .add(90)
			                    .add(btnOk, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
			                    .addPreferredGap(LayoutStyle.RELATED)
			                    .add(btnCancel, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE))
			                .add(GroupLayout.TRAILING, edtFindCertificate, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE))))
			    .addContainerGap(21, 21));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void btnOkActionPerformed(ActionEvent evt) {
		if (checkInputValues()) {
		
			String name = this.edtName.getText();
			String password = new String(this.edtPassword.getPassword());
			
			UserTO userTO  = new UserTO(name, password);
			if (ServerFactory.getInstance().getUserController().autenticate(userTO)) {
				this.setVisible(false);
				FrMain main = new FrMain();
				main.setVisible(true);
			} else {
				JOptionPane.showMessageDialog(this, "Usuário ou senha inválidos");
			}
		}
	}
	
	private boolean checkInputValues() {
		if (this.edtName.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(this, "Usuário é obrigatório");
			return false;
		}
		if (this.edtPassword.getPassword().length == 0) {
			JOptionPane.showMessageDialog(this, "Senha é obrigatória");
			return false;
		}
		return true;
	}

	private void btnCancelActionPerformed(ActionEvent evt) {
		System.exit(0);
	}
	
	void btnFindCertificatePerformed(ActionEvent evt) {
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"Certificate Files", "crt", "CRT");
		chooser.setFileFilter(filter);

		int returnVal = chooser.showOpenDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			this.file = chooser.getSelectedFile();
		}
	}

}
