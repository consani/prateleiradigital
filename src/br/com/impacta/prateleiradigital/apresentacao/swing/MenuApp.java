package br.com.impacta.prateleiradigital.apresentacao.swing;


import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.KeyStroke;

public class MenuApp extends JMenu {

	public MenuApp(String title) {
		super(title);
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("F1"),"click");
	}
	
	/**
	 * @param action vinculada com a tecla F1.
	 */
	public void addListener(final AbstractAction action) {
		this.getActionMap().put("click", action);
	}

}