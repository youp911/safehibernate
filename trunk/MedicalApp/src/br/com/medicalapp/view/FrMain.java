package br.com.medicalapp.view;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDesktopPane;

import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

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
public class FrMain extends javax.swing.JFrame {

	{
		//Set Look & Feel
		try {
			javax.swing.UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}


	private JMenuItem helpMenuItem;
	private JMenu jMenu5;
	private JTextField txtHistoric;
	private JButton btNewHist;
	private JDesktopPane desktop;
	private JPanel pnlStatus;
	private JMenuItem deleteMenuItem;
	private JSeparator jSeparator1;
	private JMenuItem pasteMenuItem;
	private JMenuItem copyMenuItem;
	private JMenuItem cutMenuItem;
	private JMenu jMenu4;
	private JMenuItem exitMenuItem;
	private JSeparator jSeparator2;
	private JMenuItem closeFileMenuItem;
	private JMenuItem saveAsMenuItem;
	private JMenuItem saveMenuItem;
	private JMenuItem openFileMenuItem;
	private JMenuItem newFileMenuItem;
	private JMenu jMenu3;
	private JMenuBar jMenuBar1;

	/**
	* Auto-generated main method to display this JFrame
	*/
	public static void main(String[] args) {
		FrMain inst = new FrMain();
		inst.setVisible(true);
	}
	
	public FrMain() {
		super();
		initGUI();
	}
	
	private void initGUI() {
		try {
			BorderLayout thisLayout = new BorderLayout();
			getContentPane().setLayout(thisLayout);
			{
				pnlStatus = new JPanel();
				getContentPane().add(pnlStatus, BorderLayout.SOUTH);
				pnlStatus.setPreferredSize(new java.awt.Dimension(400, 18));
			}
			{
				desktop = new JDesktopPane();
				getContentPane().add(desktop, BorderLayout.CENTER);
				{
					btNewHist = new JButton();
					desktop.add(btNewHist);
					btNewHist.setText("New Historic");
					btNewHist.setBounds(119, 119, 133, 28);
					btNewHist.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							String doenca = FrMain.this.txtHistoric.getText();
							ServerFactory.getInstance().getHistoricoController().criarHistorico(doenca);
							
						}
					});
				}
				{
					txtHistoric = new JTextField();
					desktop.add(txtHistoric);
					txtHistoric.setText("txtHistoric");
					txtHistoric.setBounds(105, 42, 175, 28);
				}
			}
			setSize(400, 300);
			{
				jMenuBar1 = new JMenuBar();
				setJMenuBar(jMenuBar1);
				{
					jMenu3 = new JMenu();
					jMenuBar1.add(jMenu3);
					jMenu3.setText("File");
					{
						newFileMenuItem = new JMenuItem();
						jMenu3.add(newFileMenuItem);
						newFileMenuItem.setText("New");
					}
					{
						openFileMenuItem = new JMenuItem();
						jMenu3.add(openFileMenuItem);
						openFileMenuItem.setText("Open");
					}
					{
						saveMenuItem = new JMenuItem();
						jMenu3.add(saveMenuItem);
						saveMenuItem.setText("Save");
					}
					{
						saveAsMenuItem = new JMenuItem();
						jMenu3.add(saveAsMenuItem);
						saveAsMenuItem.setText("Save As ...");
					}
					{
						closeFileMenuItem = new JMenuItem();
						jMenu3.add(closeFileMenuItem);
						closeFileMenuItem.setText("Close");
					}
					{
						jSeparator2 = new JSeparator();
						jMenu3.add(jSeparator2);
					}
					{
						exitMenuItem = new JMenuItem();
						jMenu3.add(exitMenuItem);
						exitMenuItem.setText("Exit");
					}
				}
				{
					jMenu4 = new JMenu();
					jMenuBar1.add(jMenu4);
					jMenu4.setText("Edit");
					{
						cutMenuItem = new JMenuItem();
						jMenu4.add(cutMenuItem);
						cutMenuItem.setText("Cut");
					}
					{
						copyMenuItem = new JMenuItem();
						jMenu4.add(copyMenuItem);
						copyMenuItem.setText("Copy");
					}
					{
						pasteMenuItem = new JMenuItem();
						jMenu4.add(pasteMenuItem);
						pasteMenuItem.setText("Paste");
					}
					{
						jSeparator1 = new JSeparator();
						jMenu4.add(jSeparator1);
					}
					{
						deleteMenuItem = new JMenuItem();
						jMenu4.add(deleteMenuItem);
						deleteMenuItem.setText("Delete");
					}
				}
				{
					jMenu5 = new JMenu();
					jMenuBar1.add(jMenu5);
					jMenu5.setText("Help");
					{
						helpMenuItem = new JMenuItem();
						jMenu5.add(helpMenuItem);
						helpMenuItem.setText("Help");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
