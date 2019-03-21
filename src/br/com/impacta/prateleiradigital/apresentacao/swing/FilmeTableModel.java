package br.com.impacta.prateleiradigital.apresentacao.swing;

import java.util.List;

import javax.swing.table.AbstractTableModel;
import br.com.impacta.prateleiradigital.negocio.Filme;

@SuppressWarnings("serial")
public class FilmeTableModel extends AbstractTableModel {

	private List<Filme> filmes;
	
	private String[] colNomes = { "Título", "Gêneros", "Diretores", "Ano", "Duração", "Nota", "Votos", "URL" };
	private Class<?>[] colTipos = { String.class, String.class, String.class, Integer.class, Integer.class, Double.class, Integer.class,String.class };
	
	public FilmeTableModel(){
	}
	
	public void reload(List<Filme> filmes) {
		this.filmes = filmes;
		//atualiza o componente na tela
		fireTableDataChanged();
	}

	@Override
	public Class<?> getColumnClass(int coluna) {
		return colTipos[coluna];
	}

	@Override
	public int getColumnCount() {
		return 8;
	}

	@Override
	public String getColumnName(int coluna) {
		return colNomes[coluna];
	}

	@Override
	public int getRowCount() {
		if (this.filmes == null){
			return 0;
		}
		return filmes.size();
	}

	@Override
	public Object getValueAt(int linha, int coluna) {
		Filme f = filmes.get(linha);
		switch (coluna) {
		case 0:
			return f.getTitulo();
		case 1:
			return f.getGeneros();
		case 2:
			return f.getDiretores();
		case 3:
			return f.getAno();
		case 4:
			return f.getDuracao();
		case 5:
			return f.getNota();
		case 6:
			return f.getNumVotos();
		case 7:
			return f.getUrl();
		default:
			return null;
		}
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}
	
	public Filme getFilmeAt(int index) {
		return filmes.get(index);
	}
}
