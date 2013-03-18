package angol.view;

import java.awt.Color;

import javax.swing.JPanel;

public class StatisticsPanel extends JPanel {

	JPanel answer1, answer2, answer3, answer4, answer5;
	JPanel[] answer;

	public StatisticsPanel(int boxSize) {
		this.setLayout(null);
		this.setSize(2 + 5 * boxSize, 2 + boxSize);
		this.setBackground(Color.black);

		answer = new JPanel[5];

		for (int i = 0; i < 5; i++) {
			answer[i] = new JPanel();
			answer[i].setLayout(null);
			answer[i].setLocation(2 + i * boxSize, 2);
			answer[i].setSize(boxSize - 2, boxSize - 2);
			answer[i].setBackground(Color.yellow);
			this.add(answer[i]);
		}
	}

	public void updateStatisticsPanels(String statistics) {
		for (int i = 0; i < 5; i++) {
			if (i < statistics.length()) {
				String s = "" + statistics.charAt(i);
				if (s.equals("o")) {
					answer[i].setBackground(Color.green);
				}
				if (s.equals("x")) {
					answer[i].setBackground(Color.red);
				}

			} else {
				answer[i].setBackground(Color.yellow);
			}
		}

	}
}
