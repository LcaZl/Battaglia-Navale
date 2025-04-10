package upo.battleship.zanolo.giocatori;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;

/**
 * Controller del campo di battaglia di CPU.
 * 
 * @author luca
 */
public class BattleshipCPUFieldController implements MouseListener{

	private BattleshipPlayerCPU cpu;
	
	public BattleshipCPUFieldController(BattleshipPlayerCPU c) {
		this.cpu = c;
	}

	/**
	 *Questa funzione si occupa di leggere il nome 
	 *(che corrisponde alle coordinate) della casella colpita da Human.
	 *
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		JLabel tmp = (JLabel)e.getSource();
		String[] stmp= new String[2];
		stmp = tmp.getName().split("-", 2);
		if(cpu.grid.getValueOf(Integer.parseInt(stmp[0]), Integer.parseInt(stmp[1])) < 2 )
			cpu.setHitInPoint(Integer.parseInt(stmp[0]), Integer.parseInt(stmp[1]));
			cpu.getHitIn();
	}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

}
