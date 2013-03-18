package angol.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import angol.controller.main.EnglishLearningApp;

public class ChooseSelectionPanel extends JPanel {
	View view;
	JComboBox combo = new JComboBox();
	JLabel chooseTopicLabel = new JLabel("choose topic: ");

	public ChooseSelectionPanel(final View view, final EnglishLearningApp controller) {
		this.view = view;
		this.setLayout(null);
		// this.setBackground(Color.green);
		this.setSize(300, 30);

		chooseTopicLabel.setSize(100, 30);
		combo.setSize(100, 20);
		combo.setLocation(100, 0);
		for (String topic : controller.getTopics()) {
			combo.addItem(topic);
		}
		combo.addItem("All");

		combo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.chooseNewTopic((String) combo.getSelectedItem());
				view.adjustNextWord();
			}
		});

		this.add(combo);
		this.add(chooseTopicLabel);
		setVisible(true);
	}

	public JComboBox getCombo() {
		return combo;
	}

}
