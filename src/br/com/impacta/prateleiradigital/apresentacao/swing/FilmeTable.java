package br.com.impacta.prateleiradigital.apresentacao.swing;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import br.com.impacta.prateleiradigital.negocio.Filme;


@SuppressWarnings("serial")
public class FilmeTable extends JTable {

	private FilmeTableModel modelo;
	private float[] columnWidthPercentage = {29.0f, 20.0f, 15.0f, 3.0f, 5.0f, 3.0f, 5.0f, 20.0f};
	
	public FilmeTable() {
		modelo = new FilmeTableModel();
		setModel(modelo);
      
		resizeColumns();
	    addComponentListener(new ComponentAdapter() {
	        @Override
	        public void componentResized(ComponentEvent e) {
	            resizeColumns();
	        }
	    });
	}
	
	public Filme getFilmeSelected() {
		int i = getSelectedRow();
		if (i < 0) {
			return null;
		}
		return modelo.getFilmeAt(i);
	}
	
	private void resizeColumns() {
	    int tW = this.getWidth();
	    TableColumn column;
	    TableColumnModel jTableColumnModel = this.getColumnModel();
	    int cantCols = jTableColumnModel.getColumnCount();
	    for (int i = 0; i < cantCols; i++) {
	        column = jTableColumnModel.getColumn(i);
	        int pWidth = Math.round(columnWidthPercentage[i] * tW);
	        column.setPreferredWidth(pWidth);
	    }
	}
	
	public void reload(List<Filme> filmes) {
		modelo.reload(filmes);
	}
}