package br.com.impacta.prateleiradigital.apresentacao.swing;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import br.com.impacta.prateleiradigital.controle.FilmeController;
import br.com.impacta.prateleiradigital.negocio.Filme;

public class SorteioFrame extends JFrame {

	private JTextField tfDiretor;
	private JComboBox<String> cbGenero;
	private JButton bSortear;
	private JFormattedTextField tfNota;
	private JFormattedTextField tfNumVotos;

	private ListaFilmesFrame framePrincipal;

	public SorteioFrame(ListaFilmesFrame framePrincipal) {
		this.framePrincipal = framePrincipal;
		setTitle("Sortear");
		setSize(250, 400);
		setLocationRelativeTo(null);
		setResizable(false);
		inicializaComponentes();
	}

	private void inicializaComponentes() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(montaPanelBuscaFilme(), BorderLayout.CENTER);
		panel.add(montaPanelBotoesBusca(), BorderLayout.SOUTH);
		this.add(panel);

		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addContainerGap()
						.addComponent(panel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addContainerGap()
						.addComponent(panel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addContainerGap()));
	}

	private JPanel montaPanelBotoesBusca() {
		JPanel panel = new JPanel();

		bSortear = new JButton("Sortear");
		bSortear.setMnemonic(KeyEvent.VK_S);
		bSortear.addActionListener(new ConsultarListener());
		panel.add(bSortear);

		return panel;
	}

	private void resetForm() {
		tfDiretor.setText("");
		cbGenero.setSelectedIndex(-1);
		tfNota.setValue(null);
		tfNumVotos.setValue(null);
	}

	private JPanel montaPanelBuscaFilme() {
		
		JPanel painel = new JPanel();
		GridLayout layout = new GridLayout(8, 1);
		painel.setLayout(layout);

		NumberFormat intFormat = NumberFormat.getIntegerInstance();
		NumberFormat notaFormat = new DecimalFormat();

		//Titulo
		painel.add(new JLabel("Diretor:"));
		tfDiretor = new JTextField();
		painel.add(tfDiretor);
		//Genero
		painel.add(new JLabel("Gênero:"));
		cbGenero = new JComboBox<String>(ListaFilmesFrame.getGeneros());
		painel.add(cbGenero);
		//Nota
		painel.add(new JLabel("Nota Mínima:"));
		tfNota = new JFormattedTextField(notaFormat);
		painel.add(tfNota);
		
		//Votos mínimo
		painel.add(new JLabel("Mínimo de Votos:"));
		tfNumVotos = new JFormattedTextField(intFormat);
		painel.add(tfNumVotos);
		
		return painel;
	}

	private class ConsultarListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			String diretor = tfDiretor.getText();
			String genero = (String) cbGenero.getSelectedItem();
			Object oVotos = tfNumVotos.getValue();
			Integer votos = (oVotos != null) ? ((Long) oVotos).intValue() : 0;
			Object oNota = tfNota.getValue();
			double nota = 0.0;
			if(oNota != null && oNota instanceof Long) {
				nota = (oNota != null) ? (Long) tfNota.getValue() : 0;
			}else {
				nota = (oNota != null) ? (Double) tfNota.getValue() : 0;
			}
			
			try {
				FilmeController controller = new FilmeController();
				Filme filmeSorteado = controller.sortear(diretor, genero, nota, votos);
				resetForm();
				setVisible(false);
				
				if(filmeSorteado != null) {
					framePrincipal.refreshTable(List.of(filmeSorteado));
				}else {
					JOptionPane.showMessageDialog(SorteioFrame.this,"Nenhum filme foi encontrado com os critérios Informados", "Nenhum Filme Sorteado! ",
							JOptionPane.WARNING_MESSAGE);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(SorteioFrame.this, ex.getMessage(), "Erro ao sortear Filme(s)",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

}