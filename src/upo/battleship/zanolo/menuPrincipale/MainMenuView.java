package upo.battleship.zanolo.menuPrincipale;

import java.util.Observable;
import java.util.Observer;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

/**
 * Creazione di una gui per il menu principale.
 * 
 * @author luca
 *
 */

public class MainMenuView extends JFrame implements Observer{

	private static final long serialVersionUID = 1L;
	protected JButton buttonNewGame, buttonExitGame, buttonLoadGame;
	private MainMenuModel model;
	
	public MainMenuView(MainMenuModel m) {
		this.model = m;
		build();
	}
	
	private void build() {
		//Crezione panel contenente l'immagine, tutti gli altri saranno contenuti al suo interno
		this.setTitle("Battaglia Navale - Menu");
		this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		JPanel panelPic = new JPanel() {//SpringLAyout
			private static final long serialVersionUID = 1L;
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				setOpaque(true);
				g.drawImage(model.image,0,0,getWidth(),getHeight(),null);
			}
		};	
		this.add(panelPic);
		panelPic.setLayout(new BorderLayout());
		
		//Creazione label contenente il titolo del gioco
		JLabel labelTitle = new JLabel("Battaglia Navale - Human vs. CPU");
		labelTitle.setPreferredSize(new Dimension(1280,420));
		labelTitle.setFont(new Font("Times New Roman",Font.PLAIN,70));
		labelTitle.setForeground(Color.ORANGE);

		//Creazione Bottoni
		Font buttonFont = new Font("Times New Roman",Font.PLAIN,28);
		buttonNewGame = new JButton("Nuova Partita");
		buttonNewGame.setFont(buttonFont);
		buttonLoadGame = new JButton("Carica Partita");
		buttonLoadGame.setFont(buttonFont);
		buttonExitGame = new JButton("Abbandona");
		buttonExitGame.setFont(buttonFont);

		//Raggruppamento bottoni in una label
		JLabel labelButton = new JLabel();
		labelButton.setPreferredSize(new Dimension(480,400));
		labelButton.setLayout(new GridLayout(3,1,40,40));
		labelButton.add(buttonNewGame);
		labelButton.add(buttonLoadGame);
		labelButton.add(buttonExitGame);
		
		//Creazione "Menu"
		JLabel labelMenuTitle = new JLabel("Menu");
		labelMenuTitle.setPreferredSize(new Dimension(480,120));
		labelMenuTitle.setFont(new Font(null,Font.BOLD,60));
		labelMenuTitle.setForeground(Color.WHITE);
		labelMenuTitle.setHorizontalAlignment(JLabel.CENTER);

		//Creazione jlabel contenente "menu" e bottoni
		JLabel buttonAndTitleLabel = new JLabel();
		buttonAndTitleLabel.setPreferredSize(new Dimension(560,680));
		buttonAndTitleLabel.setLayout(new GridLayout(4,1,10,10));
		buttonAndTitleLabel.add(labelMenuTitle);
		buttonAndTitleLabel.add(buttonNewGame);
		buttonAndTitleLabel.add(buttonLoadGame);
		buttonAndTitleLabel.add(buttonExitGame);
		
		panelPic.add(labelTitle, BorderLayout.PAGE_START);
		panelPic.add(buttonAndTitleLabel, BorderLayout.LINE_END);
		
		//creazione barra superiore
		JMenuBar frameBar = new JMenuBar();
		JMenu m1 = new JMenu("Help");
		JMenuItem i1 = new JMenuItem("Esci");
		frameBar.add(m1);
		m1.add(i1);
		this.setJMenuBar(frameBar);
		this.setVisible(true);
	}

	@Override
	public void update(Observable o, Object arg) {
		this.dispose();
	}
}
