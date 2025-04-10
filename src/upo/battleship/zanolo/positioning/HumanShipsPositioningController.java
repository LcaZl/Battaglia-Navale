package upo.battleship.zanolo.positioning;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JLabel;
import javax.swing.JRadioButton;

/**
 * Controller della vista del posizionamento delle navi.
 * 
 * @author luca
 *
 */
public class HumanShipsPositioningController implements ActionListener, MouseListener{

	private HumanShipsPositioningModel model;
	
	public HumanShipsPositioningController(HumanShipsPositioningModel m) {
		this.model = m;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		JLabel tmp = (JLabel)e.getSource();
		String[] stmp= new String[2];
		stmp = tmp.getName().split("-", 2);
		model.receivePoint(Integer.parseInt(stmp[0]), Integer.parseInt(stmp[1]));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = (String)e.getActionCommand();
		if(cmd.equals("Reset")) {
			model.reset();
		}
		else if(cmd.equals("Inizia")) {
			model.start();
		}
		else if(cmd.equals("Posizionamento Casuale")) {
			System.out.print("Eccooci");
			model.randPositioning();
		}
		else {
			//Nave selezionata, avviso il model.
			JRadioButton but = (JRadioButton)e.getSource();
			model.shipSelected(but.getText());
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void mouseExited(MouseEvent arg0) {}

	@Override
	public void mousePressed(MouseEvent arg0) {}

	@Override
	public void mouseReleased(MouseEvent arg0) {}

}
