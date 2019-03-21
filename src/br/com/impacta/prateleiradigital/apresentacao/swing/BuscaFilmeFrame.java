package br.com.impacta.prateleiradigital.apresentacao.swing;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.time.Year;
import java.util.List;
import java.util.Set;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import br.com.impacta.prateleiradigital.controle.FilmeController;
import br.com.impacta.prateleiradigital.negocio.Filme;

public class BuscaFilmeFrame extends JFrame {

	private JTextField tfTitulo;
	private JComboBox<String> cbGenero;
	private JButton bConsultar;
	private JSpinner spinnerAnoInicial;
	private JSpinner spinnerAnoFinal;
	int anoAtual = Year.now().getValue();

	private ListaFilmesFrame framePrincipal;

	public BuscaFilmeFrame(ListaFilmesFrame framePrincipal) {
		this.framePrincipal = framePrincipal;
		setTitle("Buscar");
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

		bConsultar = new JButton("Buscar");
		bConsultar.setMnemonic(KeyEvent.VK_B);
		bConsultar.addActionListener(new ConsultarListener());
		panel.add(bConsultar);

		return panel;
	}

	private void resetForm() {
		tfTitulo.setText("");
		cbGenero.setSelectedIndex(-1);
		spinnerAnoInicial.setValue(anoAtual);
		spinnerAnoFinal.setValue(anoAtual);
	}

	private JPanel montaPanelBuscaFilme() {
		
		JPanel painel = new JPanel();
		GridLayout layout = new GridLayout(8, 1);
		painel.setLayout(layout);
		
		//Titulo
		painel.add(new JLabel("Título:"));
		tfTitulo = new JTextField();
		painel.add(tfTitulo);
		//Genero
		painel.add(new JLabel("Gênero:"));
		cbGenero = new JComboBox<String>(ListaFilmesFrame.getGeneros());
		painel.add(cbGenero);
		// Ano inicial
		SpinnerModel anoInicioModel = new SpinnerNumberModel(1950, 0, anoAtual, 1);
		spinnerAnoInicial = new JSpinner(anoInicioModel);
		spinnerAnoInicial.setEditor(new JSpinner.NumberEditor(spinnerAnoInicial, "#"));
		painel.add(new JLabel("Ano Inicial:"));
		painel.add(spinnerAnoInicial);
		//Ano final
		SpinnerModel anoFimModel = new SpinnerNumberModel(anoAtual, 1900, anoAtual, 1);
		spinnerAnoFinal = new JSpinner(anoFimModel);
		spinnerAnoFinal.setEditor(new JSpinner.NumberEditor(spinnerAnoFinal, "#"));
		painel.add(new JLabel("Ano Final:"));
		painel.add(spinnerAnoFinal);
		return painel;
	}

	private class ConsultarListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			String titulo = tfTitulo.getText();
			String genero = (String) cbGenero.getSelectedItem();
			int anoInicial = Integer.parseInt(spinnerAnoInicial.getValue().toString());
			int anoFinal = Integer.parseInt(spinnerAnoFinal.getValue().toString());
			try {
				FilmeController controller = new FilmeController();
				final List<Filme> filmes = controller.consultarFilme(titulo, genero, anoInicial, anoFinal);
				resetForm();
				setVisible(false);

				framePrincipal.refreshTable(filmes);
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(BuscaFilmeFrame.this, ex.getMessage(), "Erro ao buscar Filme(s)",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

}