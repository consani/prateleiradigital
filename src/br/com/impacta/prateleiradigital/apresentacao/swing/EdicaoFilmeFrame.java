package br.com.impacta.prateleiradigital.apresentacao.swing;

import br.com.impacta.prateleiradigital.negocio.Filme;

public class EdicaoFilmeFrame extends InclusaoFilmeFrame {
	
	private Filme filme;

	public EdicaoFilmeFrame(ListaFilmesFrame framePrincipal) {
		super(framePrincipal);
		setTitle("Editar Filme");
		bExcluir.setVisible(true);
	}
	
	protected Filme loadFilmeFromPanel() {
		this.filme = super.loadFilmeFromPanel();
		filme.setId(getIdFilme());
		return filme;
	}

	public void setFilme(Filme f) {
		this.filme = f;
	}
	
}