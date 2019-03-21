package br.com.impacta.prateleiradigital.apresentacao.swing;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.List;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import br.com.impacta.prateleiradigital.controle.FilmeController;
import br.com.impacta.prateleiradigital.negocio.Filme;

public class ListaFilmesFrame extends JFrame {
	
	private FilmeTable tabela;
	private JScrollPane scrollPane;
	private JButton bNovoFilme;
	private JButton bBuscarFilme;
	private JButton bAtualizaLista;
	private JButton bImportacao;
	private JButton bSorteio;
	private JMenuBar menubar;
	
	private InclusaoFilmeFrame incluirFrame;
	private EdicaoFilmeFrame editarFrame;
	private BuscaFilmeFrame buscaFrame;
	private SobreFrame sobreFrame;
	public SorteioFrame sortearFrame;
	
	public ListaFilmesFrame() {
		setTitle("Lista de Filmes");
		
		inicializaComponentes();
		adicionaComponentes();
		
		pack();
		
		setResizable(true);
		setSize(1200,750);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
		
	}

	private void inicializaComponentes() {
		tabela = new FilmeTable();
		tabela.addMouseListener(new EditarFilmeListener());
		scrollPane = new JScrollPane();
		scrollPane.setViewportView(tabela);

		bNovoFilme = new JButton();
		bNovoFilme.setText("Novo");
		bNovoFilme.setMnemonic(KeyEvent.VK_N);
		bNovoFilme.addActionListener(new InclusaoFilmeListener());

		bBuscarFilme = new JButton();
		bBuscarFilme.setText("Buscar");
		bBuscarFilme.setMnemonic(KeyEvent.VK_B);
		bBuscarFilme.addActionListener(new BuscarFilmeListener());
		
		bAtualizaLista = new JButton();
		bAtualizaLista.setText("Atualizar");
		bAtualizaLista.setMnemonic(KeyEvent.VK_A);
		bAtualizaLista.addActionListener(new AtualizarListaListener());
		
		bImportacao = new JButton();
		bImportacao.setText("Importar");
		bImportacao.setMnemonic(KeyEvent.VK_I);
		bImportacao.addActionListener(new ImportarFilmeListener());
		
		bSorteio = new JButton();
		bSorteio.setText("Sortear");
		bSorteio.setMnemonic(KeyEvent.VK_S);
		bSorteio.addActionListener(new SortearFilmeListener());

		
		menubar = new JMenuBar();
		MenuApp mAjuda = new MenuApp("Ajuda");
		mAjuda.setMnemonic(KeyEvent.VK_J);
        JMenuItem miSobre = new JMenuItem("Sobre    - F1");
        miSobre.addActionListener(new SobreMenuListener());
        mAjuda.addListener(new SobreMenuListener());
        mAjuda.add(miSobre);
        menubar.add(mAjuda);
        setJMenuBar(menubar);
		
		incluirFrame = new InclusaoFilmeFrame(this);
		editarFrame = new EdicaoFilmeFrame(this);
		buscaFrame = new BuscaFilmeFrame(this);
		sortearFrame = new SorteioFrame(this);
		sobreFrame = new SobreFrame();
		
	}
	
	private void adicionaComponentes(){
		add(scrollPane);
		JPanel panel = new JPanel();
		panel.add(bNovoFilme);
		panel.add(bBuscarFilme);
		panel.add(bAtualizaLista);
		panel.add(bImportacao);
		panel.add(bSorteio);
		add(panel, BorderLayout.SOUTH);
	}
	
	public Runnable newAtualizaFilmesAction() {
		return new Runnable() {
			public void run() {
				try {
					FilmeController controller = new FilmeController();
					tabela.reload(controller.getAll());
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(ListaFilmesFrame.this,ex.getMessage(), "Erro ao consultar Filme(s)", JOptionPane.ERROR_MESSAGE);
				}
			}
		};
	}
	
	public void refreshTable(List<Filme> filmes) {
		tabela.reload(filmes);
	}
	
	private class AtualizarListaListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			SwingUtilities.invokeLater(newAtualizaFilmesAction());
		}
	}
	
	private class InclusaoFilmeListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			incluirFrame.setVisible(true);
		}
	}
	
	private class EditarFilmeListener extends MouseAdapter {
		public void mouseClicked(MouseEvent event) {
			if (event.getClickCount() == 2) {
				Filme f = tabela.getFilmeSelected();
				if (f != null) {
					editarFrame.setFilme(f);
					editarFrame.loadPanelFromFilme(f);
					editarFrame.setVisible(true);
				}
			}
		}
	}
	
	private class BuscarFilmeListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			buscaFrame.setVisible(true);
		}
	}
	
	private class ImportarFilmeListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			final JFileChooser fc = new JFileChooser();
			int returnVal = fc.showOpenDialog(ListaFilmesFrame.this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
		            File file = fc.getSelectedFile();
		            FilmeController controller = new FilmeController();
		            try {
						controller.importarFilmes(file.getAbsolutePath());
					} catch (Exception e) {
						e.printStackTrace();
						
					}
	        } 
		}
	}
	
	private class SortearFilmeListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			sortearFrame.setVisible(true);
		}
	}
	
	private class SobreMenuListener extends AbstractAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			sobreFrame.setVisible(true);
		}
	}
	
	public static String[] getGeneros() {
		String[] generos;
		FilmeController controller = new FilmeController();

		try {
			Set<String> setGeneros = controller.getListaDeGeneros();
			generos = new String[setGeneros.size()];
			int indice = 0;
			for (String genero : setGeneros) {
				generos[indice++] = genero;
			}
		} catch (Exception e) {
			generos = new String[0];
			e.printStackTrace();
		}

		return generos;
	}


}