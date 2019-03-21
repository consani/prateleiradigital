package br.com.impacta.prateleiradigital.apresentacao.swing;


import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.*;
import javax.swing.GroupLayout.Group;
import javax.swing.GroupLayout.SequentialGroup;

public class SobreFrame extends JFrame {

	public SobreFrame(){
		setTitle("Sobre a aplicação");
		setSize(730,230);
		setLocationRelativeTo(null);
		setResizable(false);
		inicializaComponentes();
	}
	
	private void inicializaComponentes() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(montaLabelsSobre(), BorderLayout.CENTER);
		add(panel);
	}
	
	private JPanel montaLabelsSobre() {
        JPanel painelLabels = new JPanel();
        painelLabels.setBorder(BorderFactory.createEtchedBorder());
        GroupLayout panelLayout = new GroupLayout(painelLabels);
        
        JLabel l1 = new JLabel("Aplicação:");
        JLabel l2 = new JLabel("Versão:");
        JLabel l3 = new JLabel("Desenvolvido por:");
        JLabel l5 = new JLabel("Site:");
        
        JLabel lTitulo = new JLabel("Gerenciamento de Filmes IMDB");
        JLabel lVersao = new JLabel("1.0");
        JLabel lDesenvPor = new JLabel("Curso Impacta Java Programmer");
        JLabel lSite = new JLabel("www.impacta.com.br");
        JPanel logo = new JPanel();
        logo.setPreferredSize(new Dimension(160, 80));
 
        painelLabels.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
            panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(createHorizontalGroup(panelLayout, 33,
            		groupComponents(panelLayout, l1, l2, l3, l5), 
            		groupComponents(panelLayout, lTitulo, lVersao, lDesenvPor, lSite))
            	.addComponent(logo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
            )
        );
        
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(panelLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                		.addComponent(logo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                		.addGroup(createVerticalGroup(panelLayout,
                				groupComponents(panelLayout, l1, lTitulo),
                				groupComponents(panelLayout, l2, lVersao),
                				groupComponents(panelLayout, l3, lDesenvPor),
                				groupComponents(panelLayout, l5, lSite)))
                ).addContainerGap(203, Short.MAX_VALUE))
        );

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(painelLabels, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(painelLabels, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        return painelLabels;
    }
	
	private Group groupComponents(GroupLayout layout, Component ... components) {
		Group g = layout.createParallelGroup(GroupLayout.Alignment.LEADING);
		for (Component c: components) {
			g.addComponent(c);
		}
		return g;
	}
	
	private Group createHorizontalGroup(GroupLayout layout, int gap, Group ... columns) {
		Group g = layout.createSequentialGroup().addContainerGap();
		for (Group c: columns) {
			g.addGroup(c).addGap(gap);
		}
		return g;
	}
	
	private Group createVerticalGroup(GroupLayout layout, Group ... columns) {
		SequentialGroup g = layout.createSequentialGroup();
		for (Group c: columns) {
			g.addGroup(c).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED);
		}
		return g;
	}
	
	
}