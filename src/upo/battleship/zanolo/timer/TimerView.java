package upo.battleship.zanolo.timer;
import java.awt.Font;
import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class TimerView extends JPanel implements Observer, Serializable {

	private static final long serialVersionUID = -3692702544881242171L;
	JLabel timeLabel;
	TimerModel model;
	
	public TimerView(TimerModel m) {
		super();
		this.model = m;
		this.setSize(250, 150);
		this.setLayout(new BorderLayout());
		JLabel title = new JLabel("<html>(Tempo limite : " + model.timeLimit + " minuti)<BR>");
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setVerticalAlignment(JLabel.CENTER);
		title.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		this.add(title, BorderLayout.PAGE_START);
		TitledBorder bord = new TitledBorder(new LineBorder(Color.RED),"TEMPO");
		bord.setTitleFont(new Font("Times New Roman",Font.BOLD,25));
		this.setBorder(bord);
		JLabel timeInfo = new JLabel("Tempo : ");
		timeInfo.setFont(new Font("Times New Roman",Font.PLAIN, 25));
		timeInfo.setSize(new Dimension(100,50));
		this.add(timeInfo, BorderLayout.LINE_START);
		
		timeLabel = new JLabel("");
		timeLabel.setFont(new Font("Times New Roman", Font.BOLD, 30));
		this.add(timeLabel, BorderLayout.CENTER);
		
		this.setVisible(true);
	}
	
	public void refreshView(String arg) {
		timeLabel.setText(arg);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		int n = (int)arg1;
		if(n == 42)
			refreshView(arg0.toString());
	}
}
