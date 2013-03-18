package angol.controller.main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import angol.model.excel.DataHandler;
import angol.model.excel.Topic;
import angol.model.statistics.Word;
import angol.model.statistics.WordSelector;
import angol.view.View;

public class EnglishLearningApp {

	// private ArrayList<Word> words;
	private String lastSelectedQuestion = "%initialValue%";
	private DataHandler dataHandler;
	HashMap<String, Topic> topics;
	Topic topic;

	public EnglishLearningApp(String inputFilePath) {
		dataHandler = new DataHandler(inputFilePath);
		topics = dataHandler.getTopics();
		topic = topics.get(dataHandler.getTopicStrings().get(0));
		new View(this);
	}

	public static void main(String[] args) {
		new EnglishLearningApp(args[0]);
	}

	public void sortList(String s) {
		Topic topic = topics.get(s);
		ArrayList<Word> words = topic.getWordList();
		Collections.sort(words);
		topic.setWordList(words);
	}

	public Word getNewWord() {
		ArrayList<Word> wordList = topic.getWordList();
		int newSelectedIndex = WordSelector.chooseWordIndex(wordList.size());
		String newSelectedQuestion = wordList.get(newSelectedIndex).getQuestion();
		while (lastSelectedQuestion.equals(newSelectedQuestion)) {
			newSelectedIndex = WordSelector.chooseWordIndex(wordList.size());
			newSelectedQuestion = wordList.get(newSelectedIndex).getQuestion();
		}
		lastSelectedQuestion = newSelectedQuestion;
		return wordList.get(newSelectedIndex);
	}

	public ArrayList<String> getTopics() {
		return dataHandler.getTopicStrings();
	}

	public void updateStatisticsCell(int rowNr, String sheetName, String s) {
		dataHandler.updateStatisticsCell(rowNr, sheetName, s);
	}

	public void saveData() {
		dataHandler.saveData();
	}

	public void chooseNewTopic(String topicString) {
		topic = topics.get(topicString);
	}
}