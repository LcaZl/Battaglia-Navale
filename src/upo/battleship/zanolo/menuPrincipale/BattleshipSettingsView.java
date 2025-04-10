package upo.battleship.zanolo.menuPrincipale;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Graphics;
import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.*;
import javax.swing.JComboBox;
import javax.swing.JToggleButton;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

/**
 * Vista di BattleshipSettings.
 * Si occupa di mostrare le impostazioni all'utente.
 * 
 * @author luca
 */
public class BattleshipSettingsView extends JFrame implements Observer{

	private static final long serialVersionUID = 7688222894938303853L;
	private GridBagConstraints gbc = new GridBagConstraints();
	private Dimension DIM = new Dimension(240,110);
	private Dimension DIMC = new Dimension(240,45);
	private Font font = new Font("Times New Roman",Font.ITALIC,23);
	private JPanel panelSettings;
	private JLabel err1;
	protected JTextField nameField;
	protected JButton startGame, defaultGame;
	protected JComboBox<String> fieldSizeChoice;
	protected Vector<JComboBox<Integer>> shipNumChoiceVet;
	protected JComboBox<String> modeChoice;
	protected JToggleButton timeButton;
	private BattleshipSettingsModel model;
	
	public BattleshipSettingsView(BattleshipSettingsModel m) {
		this.model = m;
		build();
	}
	
	private void build() {
		//Creazione JFrame.
		this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
		this.setTitle("Impostazioni Partita");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		//Creazione pannello per immagine di sfondo al JFrame.
		JPanel panelPic = new JPanel() {
			private static final long serialVersionUID = 1L;
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				setOpaque(true);
				g.drawImage(model.image,0,0,getWidth(),getHeight(),null);
			}
		};		
		this.add(panelPic);
		
		//Creazione pannello delle impostazioni con l'imamgine di sfondo.
		panelSettings = new JPanel(new GridBagLayout()){
			private static final long serialVersionUID = 1L;
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				setOpaque(true);
				g.drawImage(model.image2,0,0,getWidth(),getHeight(),null);
			}
		};
		panelSettings.setPreferredSize(new Dimension(1000,1100));	
		TitledBorder bord = new TitledBorder(new LineBorder(Color.ORANGE),"Impostazioni Partita");
		bord.setTitlePosition(TitledBorder.CENTER);
		bord.setTitleFont(new Font("Times New Roman",Font.BOLD,45));
		panelSettings.setBorder(bord);
		gbc.insets = new Insets(20,0,0,100);
		gbc.anchor = GridBagConstraints.LINE_END;
		gbc.fill = GridBagConstraints.HORIZONTAL;

		//Creazione impostazione nome
		JLabel nameLabel = new JLabel();
		nameLabel.setSize(DIM);
		nameLabel.setText("Nome Giocatore");
		nameLabel.setFont(font);
		nameField = new JTextField();
		nameField.setPreferredSize(DIMC);
		//posizionamento
		gbc.gridx = 0;
		gbc.gridy = 0;
		panelSettings.add(nameLabel, gbc);
		gbc.gridx = 1;
		panelSettings.add(nameField, gbc);
		
		//Creazione impostazione difficolta CPU
		JLabel cpuMode = new JLabel();
		cpuMode.setSize(DIM);
		cpuMode.setText("<html>Livello CPU</html>");
		cpuMode.setFont(font);
		String[] cpuModes = {"Facile","Normale"};
		modeChoice = new JComboBox<String>(cpuModes);
		modeChoice.setPreferredSize(DIMC);
		//posizionamento
		gbc.gridx = 0;
		gbc.gridy = 1;
		panelSettings.add(cpuMode, gbc);
		gbc.gridx = 1;
		panelSettings.add(modeChoice, gbc);
		
		//Creazione impostazione dimensione del campo di battaglia (10x10,15x15,20x20)
		JLabel sizeLabel = new JLabel();
		sizeLabel.setSize(DIM);
		sizeLabel.setText("<html>Dimensione campo<BR>di battaglia</html>");
		sizeLabel.setFont(font);
		String[] dims = {"10x10","15x15","20x20"};
		fieldSizeChoice = new JComboBox<String>(dims);
		fieldSizeChoice.setPreferredSize(DIMC);
		fieldSizeChoice.setName("dField");
		//posizionamento
		gbc.gridx = 0;
		gbc.gridy = 2;
		panelSettings.add(sizeLabel, gbc);
		gbc.gridx = 1;
		panelSettings.add(fieldSizeChoice, gbc);
		
		//Impostazione tempo si/no
		JLabel timeLabel = new JLabel();
		timeLabel.setText("Tempo");
		timeLabel.setPreferredSize(DIM);
		timeLabel.setFont(font);
		timeButton = new JToggleButton("ON/OFF",false);
		timeButton.setPreferredSize(DIMC);
		//posizionamento
		gbc.gridx = 0;
		gbc.gridy = 3;
		panelSettings.add(timeLabel, gbc);
		gbc.gridx = 1;
		panelSettings.add(timeButton, gbc);
				
		//Scelta numero di navi di ogni tipo
		this.shipNumChoiceVet = new Vector<JComboBox<Integer>>();
		this.buildSetting("<html>Numero<BR>Caccitorpedinieri</html>" , 4);
		this.buildSetting("<html>Numero<BR>Sottomarini</html>"       , 5);
		this.buildSetting("<html>Numero<BR>Corazzate</html>"         , 6);
		this.buildSetting("<html>Numero<BR>Portaerei</html>"         , 7);
		shipNumChoiceVet.get(1).removeItem(0);
		shipNumChoiceVet.get(3).removeItem(0);

		//Creazione e aggiunta bottoni
		Font buttonFont = new Font("Times New Roman",Font.PLAIN,25);
		startGame = new JButton();
		startGame.setFont(buttonFont);
		startGame.setText("Battaglia!");
		startGame.setName("st");
		startGame.setPreferredSize(DIM);
		defaultGame = new JButton();
		defaultGame.setText("Inizio Veloce");
		defaultGame.setPreferredSize(DIM);
		defaultGame.setFont(buttonFont);
		//posizionamento
		gbc.insets = new Insets(80,0,0,100);
		gbc.gridx = 0;
		gbc.gridy = 8;
		panelSettings.add(startGame, gbc);
		gbc.gridx = 1;
		panelSettings.add(defaultGame, gbc);
				
		//Creazione info bottoni
		Dimension dimInfo = new Dimension(240,40);
		JLabel info1 = new JLabel();
		info1.setText("(Impostazioni personalizzate)");
		info1.setSize(dimInfo);
		info1.setFont(new Font("Times New Roman", Font.PLAIN , 10));
		
		JLabel info2 = new JLabel();
		info2.setText("(Campo 10x10, Facile, 4-3-2-1)");
		info2.setSize(dimInfo);
		info2.setFont(new Font("Times New Roman", Font.PLAIN , 10));
		//posizionamento
		gbc.insets = new Insets(3,0,0,100);
		gbc.gridx = 0;
		gbc.gridy = 9;
		panelSettings.add(info1, gbc);
		gbc.gridx = 1;
		panelSettings.add(info2, gbc);
	
		//creazione Label errore
		err1 = new JLabel();
		err1.setText("Campo obbligatorio!");
		err1.setForeground(Color.red);
		err1.setSize(dimInfo);
		err1.setVisible(false);
		//posizionamento
		gbc.insets = new Insets(20,0,0,100);
		gbc.gridx = 3;
		gbc.gridy = 0;
		panelSettings.add(err1, gbc);
		
		//Aggiunta del pannello contenente le impostazioni
		panelPic.add(panelSettings, BorderLayout.CENTER);
		this.setVisible(true);
	}
	
	private void buildSetting(String text,int y) {
		JLabel tempLabel = new JLabel();
		tempLabel.setSize(DIM);
		tempLabel.setText(text);
		tempLabel.setFont(font);
		Vector<Integer> data = new Vector<Integer>();
		for(int i = 0;i <= model.shipMaxN10x10[y-4]; i++)
			data.add(i); 
		JComboBox<Integer> temp = new JComboBox<Integer>(data);
		temp.setPreferredSize(DIMC);
		shipNumChoiceVet.addElement(temp);
		//posizionamento
		gbc.gridx = 0;
		gbc.gridy = y;
		panelSettings.add(tempLabel, gbc);
		gbc.gridx = 1;
		panelSettings.add(temp, gbc);
	}
	
	
	/**
	 * Funzione che si occupa di aggiornare il numero di navi disponibili in relazione
	 * alle nuove dimensioni del campo di battaglia scelte.
	 * 
	 * @param index Valore ad indicare le dimensioni del campo. (0:10x10, 1:15x15, 2:20x20).
	 */
	protected void reloadShipNumber(int index) {
		for(int i = 0; i <= shipNumChoiceVet.size()-1; i++) {
			shipNumChoiceVet.get(i).removeAllItems();
			switch(index) {
				case 0: 
					for(int k = 0; k <= model.shipMaxN10x10[i]; k++) 
						shipNumChoiceVet.get(i).addItem(k);
				break;
				case 1: 
					for(int k = 0; k <= model.shipMaxN15x15[i]; k++)
						shipNumChoiceVet.get(i).addItem(k);
				break;
				case 2:
					for(int k = 0; k <= model.shipMaxN20x20[i]; k++) 
						shipNumChoiceVet.get(i).addItem(k);
				break;
			}
			shipNumChoiceVet.get(1).removeItem(0);
			shipNumChoiceVet.get(3).removeItem(0);
			shipNumChoiceVet.get(i).validate();
			shipNumChoiceVet.get(i).repaint();
		}
	}
	
	@Override
	public void update(Observable arg0, Object flag) {
		switch((int)flag) {
			case 0:
				this.setVisible(false);
				break;
			case 1:
				err1.setVisible(true);
				break;
		}
		panelSettings.validate();
		panelSettings.repaint();
	}	
}

