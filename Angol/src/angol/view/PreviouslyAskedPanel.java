package angol.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import angol.model.statistics.AnswerAnalizer;
import angol.model.statistics.Word;

public class PreviouslyAskedPanel extends JPanel implements ActionListener {

	StatisticsPanel statisticsPanel;
	JLabel label1, label2, label3;
	JTextField correction_field = new JTextField();
	JButton correction_button = new JButton("Ez a válasz rossz, javítom");
	Word word;

	int horizontal = 20;
	int vertical = 30;

	public PreviouslyAskedPanel() {
		this.setLayout(null);
		// this.setBackground(Color.cyan);
		JLabel previouslyAsked = new JLabel("Previously asked:");
		previouslyAsked.setLocation(horizontal, 0 + vertical);
		previouslyAsked.setSize((int) previouslyAsked.getPreferredSize().getHeight(), 500);
		this.add(previouslyAsked);
		this.setSize(ViewConstants.HORIZONTAL_SIZE, 150);

		statisticsPanel = new StatisticsPanel(12);
		statisticsPanel.setLocation(17, 20 + vertical);
		this.add(statisticsPanel);

		label1 = new JLabel();
		label1.setLocation(horizontal, 25 + vertical);
		label1.setSize(ViewConstants.HORIZONTAL_SIZE, 40);
		this.add(label1);

		label2 = new JLabel();
		label2.setLocation(horizontal, 45 + vertical);
		label2.setSize(ViewConstants.HORIZONTAL_SIZE, 40);
		this.add(label2);

		label3 = new JLabel();
		label3.setLocation(horizontal, 60 + vertical);
		label3.setSize(ViewConstants.HORIZONTAL_SIZE, 40);
		this.add(label3);

		correction_button.setLocation(200 + horizontal, 20);
		correction_button.setSize(correction_button.getPreferredSize());
		// jbutton.setSize(100, 30);
		correction_button.addActionListener(this);
		correction_button.setVisible(false);
		this.add(correction_button);

		correction_field.setLocation(200 + horizontal, 20);
		correction_field.setSize(correction_button.getPreferredSize());
		// jbutton.setSize(100, 30);
		correction_field.addActionListener(this);
		correction_field.setVisible(false);
		this.add(correction_field);
	}

	public void paint(Graphics g) {
		super.paint(g); //
		// this.setBackground(Color.cyan);
		// g.drawRect(0, 0, 99, 99);
		// g.drawRect(0, 0, 100, 100);
		g.drawLine(0, 0, 200, 0);
	}

	public void updateFields(Word word, String userAnswer) {
		this.word = word;
		statisticsPanel.updateStatisticsPanels(word.getStatisticsString());
		label2.setText("your answer      : " + userAnswer);
		label3.setText("correct answer: " + word.getAnswer());
		if (AnswerAnalizer.isCorrectAnswer(word.getAnswer(), userAnswer)) {
			label1.setForeground(Color.green);
			label1.setText("CORRECT!");
		} else {
			label1.setForeground(Color.red);
			label1.setText("WRONG!");
		}
	}

	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == correction_button) {
			System.out.println("szevasy");
			correction_button.setVisible(false);
			correction_field.setVisible(true);
		}

		if (e.getSource() == correction_field) {
			word.setAnswer(correction_field.getText());
			correction_field.setText("");

			correction_button.setVisible(true);
			correction_field.setVisible(false);

		}

	}
}
