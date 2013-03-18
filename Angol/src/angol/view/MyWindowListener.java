package angol.view;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import angol.controller.main.EnglishLearningApp;

public class MyWindowListener implements WindowListener {

	EnglishLearningApp controller;

	public MyWindowListener(EnglishLearningApp controller) {
		this.controller = controller;
	}

	@Override
	public void windowActivated(WindowEvent e) {
	}

	@Override
	public void windowClosed(WindowEvent e) {
	}

	@Override
	public void windowClosing(WindowEvent e) {
		closeOperation();
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
	}

	@Override
	public void windowIconified(WindowEvent e) {
	}

	@Override
	public void windowOpened(WindowEvent e) {
	}

	private void closeOperation() {
		controller.saveData();
	}
}
