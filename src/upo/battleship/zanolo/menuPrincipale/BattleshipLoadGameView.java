package upo.battleship.zanolo.menuPrincipale;

import java.util.Observable;
import java.util.Observer;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.BorderLayout;
import java.awt.Color;
import java.io.File;
import java.io.FilenameFilter;
import java.time.LocalDate;
import java.time.LocalTime;


/**
 * 
 * Vista di BattleshipLoadGame.
 * Questo oggetto si occupa unicamente della costruzione e aggiornamento della vista.
 * 
 * @author luca
 */
public class BattleshipLoadGameView extends JFrame implements Observer{
	
	private static final long serialVersionUID = -3082894462771791713L;
	protected JButton back;
	protected JButton delete;
	protected JButton load;
	protected JButton exit;
	protected ButtonGroup bGroup;//Contenitore di tutti i JRadioButton
	private JPanel panelPic;//Pannello contenente l'immagine
	private JLabel savesCont;//Contenitore a sinistra
	private BattleshipLoadGameController controller;
	private BattleshipLoadGameModel model;

	/**
	 * Il costruttore imposta la view come Observer del modello
	 * 
	 * @param c Controller di BattleshipLoadGame
	 * @param m Modello di BattleshipLoadGame
	 */
	public BattleshipLoadGameView(BattleshipLoadGameController c, BattleshipLoadGameModel m) {
		this.controller = c;
		this.model = m;
		model.addObserver(this);
		build();
	}
	
	private void build() {
		Dimension DIM = new Dimension(370,110);
		panelPic = new JPanel() {
			private static final long serialVersionUID = 1L;
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				setOpaque(true);
				g.drawImage(model.image,0,0,getWidth(),getHeight(),null);
			}
		};	
		this.add(panelPic);
		this.setTitle("Salvataggi");
		this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);	
		panelPic.setLayout(new BorderLayout());

		//creazione bottoni a destra
		Font buttonFont = new Font("Times New Roman",Font.BOLD, 30);
		load = new JButton();
		load.setText("Carica Partita");
		load.setFont(buttonFont);
		load.setPreferredSize(DIM);
		load.addActionListener(controller);
		
		delete = new JButton();
		delete.setText("Cancella Partita");
		delete.setFont(buttonFont);
		delete.setPreferredSize(DIM);
		delete.addActionListener(controller);
		
		exit = new JButton();
		exit.setText("Esci dal Gioco");
		exit.setFont(buttonFont);
		exit.setPreferredSize(DIM);
		exit.addActionListener(controller);
		
		back = new JButton();
		back.setText("Menu principale");
		back.setFont(buttonFont);
		back.setPreferredSize(DIM);
		back.addActionListener(controller);
		
		JLabel buttons = new JLabel();
		buttons.setLayout(new GridBagLayout());
		buttons.setOpaque(false);
		GridBagConstraints gbc = new GridBagConstraints();
		buttons.setPreferredSize(new Dimension(600,1300));
		gbc.insets = new Insets(20,0,20,0);
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.fill = GridBagConstraints.VERTICAL;
		gbc.gridx = 0;
		gbc.gridy = 0;
		buttons.add(load, gbc);
		gbc.gridy = 1;
		buttons.add(delete, gbc);
		gbc.gridy = 2;
		buttons.add(back, gbc);
		gbc.gridy = 3;
		buttons.add(exit, gbc);
		panelPic.add(buttons, BorderLayout.LINE_END);		
		
		//creazione e aggiunta titolo
		TitledBorder bord = new TitledBorder(new LineBorder(Color.ORANGE),"Battaglia Navale - Human Vs. CPU");
		bord.setTitlePosition(TitledBorder.CENTER);
		bord.setTitleFont(new Font("Times New Roman",Font.BOLD,45));
		bord.setTitleColor(Color.ORANGE);
		panelPic.setBorder(bord);
		
		//creazione menu salvataggi
		bGroup = new ButtonGroup();
		createSavesList();
		panelPic.add(savesCont, BorderLayout.LINE_START);

		this.setVisible(true);	
	}
	
	private void createSavesList() {
		Font ntsFont = new Font("Times New Roman", Font.BOLD, 35);
		Font tmpFont = new Font("Times New Roman", Font.BOLD, 24);
		savesCont = new JLabel();
		savesCont.setLayout(new GridBagLayout());
		savesCont.setPreferredSize(new Dimension(900,1300));
		TitledBorder bord = new TitledBorder(new LineBorder(Color.ORANGE),"Salvataggi");
		bord.setTitlePosition(TitledBorder.CENTER);
		bord.setTitleFont(new Font("Times New Roman",Font.BOLD,40));
		bord.setTitleColor(Color.ORANGE);
		savesCont.setBorder(bord);
		File pf = new File("./Salvataggi/");
		FilenameFilter filter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.startsWith("S1") && !name.endsWith("TESTING");
			}
		};
		String a[]  = pf.list(filter);
		GridBagConstraints gbcS = new GridBagConstraints();
		gbcS.insets = new Insets(20,30,20,30);
		gbcS.anchor = GridBagConstraints.CENTER;
		gbcS.fill = GridBagConstraints.VERTICAL;
		
		if(a.length == 0) {
			JLabel noSave = new JLabel();
			noSave.setText("Nessun salvataggio presente.");
			noSave.setFont(tmpFont);
			gbcS.gridy = gbcS.gridy + 1;
			savesCont.add(noSave, gbcS);
		}
		else {
			gbcS.gridx = 0;
			gbcS.gridy = 0;
			JLabel nts2 = new JLabel("Nome");
			nts2.setFont(ntsFont);
			nts2.setForeground(Color.ORANGE);
			gbcS.gridx = 1;
			savesCont.add(nts2, gbcS);
			JLabel nts3 = new JLabel("Data");
			nts3.setFont(ntsFont);
			nts3.setForeground(Color.ORANGE);
			gbcS.gridx = 2;
			savesCont.add(nts3,gbcS);
			JLabel nts4 = new JLabel("Ora");
			nts4.setFont(ntsFont);
			nts4.setForeground(Color.ORANGE);
			gbcS.gridx = 3;
			savesCont.add(nts4,gbcS);
			gbcS.insets= new Insets(10,20,15,10);
			for(int i = 0; i < a.length; i++) {
				gbcS.gridy = gbcS.gridy + 1;
				JRadioButton sel = new JRadioButton();
				sel.setName(a[i]);
				sel.addActionListener(controller);
				bGroup.add(sel);
				gbcS.gridx = 0;
				savesCont.add(sel, gbcS);
				
				JLabel tmpName = new JLabel();
				tmpName.setText(getVal(a[i], 0));
				tmpName.setFont(tmpFont);
				tmpName.setForeground(Color.ORANGE);
				gbcS.gridx = 1;
				savesCont.add(tmpName, gbcS);
					
				JLabel tmpDate = new JLabel();
				tmpDate.setText(getVal(a[i], 1));
				tmpDate.setFont(tmpFont);
				tmpDate.setForeground(Color.ORANGE);
				gbcS.gridx = 2;
				savesCont.add(tmpDate, gbcS);
				
				JLabel tmpTime = new JLabel();
				tmpTime.setText(getVal(a[i], 2));
				tmpTime.setFont(tmpFont);
				tmpTime.setForeground(Color.ORANGE);
				gbcS.gridx = 3;
				savesCont.add(tmpTime, gbcS);
			}
		}
	}
	
	private String getVal(String str, int mod) {
			String tmp1[] = str.split("_");
			String tmp2[] = tmp1[1].split("T");
			switch(mod) {
			case 0:
				return tmp1[2];
			case 1:
				LocalDate date = LocalDate.parse(tmp2[0]);
				return (date.getDayOfMonth() +  "-" + date.getMonthValue() + "-" + date.getYear()); 
			case 2:
				LocalTime time  = LocalTime.parse(tmp2[1]);
				return time.toString();
			}
			throw new IllegalArgumentException("Salvataggio corrotto.\n");
		}

	@Override
	public void update(Observable arg0, Object arg1) {
		int n = (int)arg1;
		if(n == 51)
		{	
			savesCont.removeAll();
			createSavesList();
			savesCont.validate();
			savesCont.repaint();
			panelPic.add(savesCont, BorderLayout.LINE_START);
			panelPic.validate();
			panelPic.repaint();
			
		}
		else if(n == 52) {
			this.setVisible(false);
			this.dispose();
		}
	} 
}
