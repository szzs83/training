package angol.view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import angol.controller.main.EnglishLearningApp;
import angol.model.excel.DataHandler;
import angol.model.statistics.AnswerAnalizer;
import angol.model.statistics.Word;

public class View implements ActionListener {

	JPanel quizPanel, completionPanel;
	StatisticsPanel statisticsPanel;
	PreviouslyAskedPanel previouslyAskedPanel;
	ChooseSelectionPanel chooseSelectionPanel;
	JLabel expectedWordLabel, correctOrWrongLabel;
	JTextField answerWordField;
	// JButton nextButton;
	EnglishLearningApp controller;
	Word word;

	// DataHandler dataHandler;

	public View(EnglishLearningApp controller) {
		this.controller = controller;
		// this.dataHandler = dataHandler;
		word = controller.getNewWord();
		createAndShowGUI();
		expectedWordLabel.setText(word.getQuestion());
	}

	public JPanel createContentPane() {

		JPanel totalGUI = new JPanel();
		totalGUI.setLayout(null);

		chooseSelectionPanel = new ChooseSelectionPanel(this, controller);
		chooseSelectionPanel.setLocation(10, 10);
		totalGUI.add(chooseSelectionPanel);

		previouslyAskedPanel = new PreviouslyAskedPanel();
		previouslyAskedPanel.setLocation(10, 200);
		totalGUI.add(previouslyAskedPanel);

		statisticsPanel = new StatisticsPanel(22);
		statisticsPanel.setLocation(10, 60);
		statisticsPanel.updateStatisticsPanels(word.getStatisticsString());
		totalGUI.add(statisticsPanel);

		quizPanel = new JPanel();
		quizPanel.setLayout(null);
		quizPanel.setLocation(10, 90);
		quizPanel.setSize(ViewConstants.HORIZONTAL_SIZE, 70);
		totalGUI.add(quizPanel);

		expectedWordLabel = new JLabel("");
		expectedWordLabel.setLocation(0, 0);
		expectedWordLabel.setSize(ViewConstants.HORIZONTAL_SIZE, 40);
		quizPanel.add(expectedWordLabel);

		answerWordField = new JTextField(8);
		answerWordField.setLocation(0, 40);
		answerWordField.setSize(300, 30);
		answerWordField.addActionListener(this);
		quizPanel.add(answerWordField);

		// completionPanel = new JPanel();
		// completionPanel.setLayout(null);
		// completionPanel.setLocation(240, 35);
		// completionPanel.setSize(70, 80);
		// totalGUI.add(completionPanel);

		correctOrWrongLabel = new JLabel("");
		correctOrWrongLabel.setForeground(Color.red);
		correctOrWrongLabel.setLocation(240, 35);
		correctOrWrongLabel.setSize(70, 40);
		totalGUI.add(correctOrWrongLabel);

		// nextButton = new JButton("Next");
		// nextButton.setLocation(50, 120);
		// nextButton.setSize(80, 30);
		// nextButton.addActionListener(this);
		// totalGUI.add(nextButton);

		totalGUI.setOpaque(true);
		return totalGUI;
	}

	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == answerWordField) {

			if (AnswerAnalizer.isCorrectAnswer(word.getAnswer(), answerWordField.getText())) {
				word.addCorrectAnswer();
			} else {
				word.addIncorrectAnswer();
			}
			controller.updateStatisticsCell(word.getRowId(), word.getTopicName(), word.getStatisticsString());
			statisticsPanel.updateStatisticsPanels(word.getStatisticsString());
			previouslyAskedPanel.updateFields(word, answerWordField.getText());
			adjustNextWord();

		}

		// if (e.getSource() == nextButton) {
		// previouslyAskedPanel.updateFields(word, answerWordField.getText());
		// adjustNextWord();
		// }

	}

	private void createAndShowGUI() {
		JFrame.setDefaultLookAndFeelDecorated(true);
		JFrame frame = new JFrame("EnglishLearningApp");
		frame.setContentPane(createContentPane());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addWindowListener(new MyWindowListener(controller));
		frame.setLocation(100, 100);
		frame.setSize(500, 400);
		frame.setVisible(true);
	}

	public void adjustNextWord() {
		String topic = (String) chooseSelectionPanel.getCombo().getSelectedItem();
		controller.sortList(topic);
		word = controller.getNewWord();
		expectedWordLabel.setText(word.getQuestion());
		correctOrWrongLabel.setText("");
		answerWordField.setText("");
		statisticsPanel.updateStatisticsPanels(word.getStatisticsString());
	}

}