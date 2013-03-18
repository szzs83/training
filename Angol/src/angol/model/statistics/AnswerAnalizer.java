package angol.model.statistics;

public class AnswerAnalizer {

	public static boolean isCorrectAnswer(String word, String userAnswer) {

		word = word.toLowerCase();
		userAnswer = userAnswer.toLowerCase();

		if (word.contains("{") && word.contains("|") && word.contains("}")) {
			String options = word.substring(word.indexOf("{"), word.indexOf("}") + 1);

			String firstOption = word.substring(word.indexOf("{") + 1, word.indexOf("|"));
			String secondOption = word.substring(word.indexOf("|") + 1, word.indexOf("}"));
			String word1 = word.replace(options, firstOption);
			String word2 = word.replace(options, secondOption);
			if (word1.equals(userAnswer) || word2.equals(userAnswer)) {
				return true;
			}

		}

		return word.equals(userAnswer);

	}
}
