package angol.model.statistics;

import java.util.Iterator;

public class Word implements Comparable<Word> {

	private String question;
	private String answer;
	private LimitedQueue<String> statistics;
	private int rowId;
	private String topicName;

	public Word(String question, String answer, String topicName, int rowId) {
		this.question = question;
		this.answer = answer;
		this.rowId = rowId;
		this.topicName = topicName;
		statistics = new LimitedQueue<String>(5);
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String hungarian) {
		this.question = hungarian;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String english) {
		this.answer = english;
	}

	public int getNrOfCorrectAnswers() {
		Iterator<String> it = statistics.iterator();
		int counter = 0;
		while (it.hasNext()) {
			String iteratorValue = (String) it.next();
			if (iteratorValue.equals("o")) {
				counter++;
			}
		}
		return counter;
	}

	public void addCorrectAnswer() {
		statistics.add("o");
	}

	public void addIncorrectAnswer() {
		statistics.add("x");
	}

	public int getRowId() {
		return rowId;
	}

	public void setRowId(int rowId) {
		this.rowId = rowId;
	}

	public String getStatisticsString() {
		Iterator<String> it = statistics.iterator();
		String result = "";
		while (it.hasNext()) {
			String iteratorValue = (String) it.next();
			result += iteratorValue;
		}
		return result;
	}

	public String getTopicName() {
		return topicName;
	}

	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}

	@Override
	public int compareTo(Word word2) {
		return this.getNrOfCorrectAnswers() - word2.getNrOfCorrectAnswers();
	}

}
