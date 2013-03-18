package angol.model.excel;

import java.util.ArrayList;
import java.util.Collections;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import angol.model.statistics.Word;

public class Topic {

	private ArrayList<Word> wordList = new ArrayList<Word>();
	private Workbook workbook;
	private String sheetName;

	public Topic(Workbook workbook, String sheetName) {
		this.workbook = workbook;
		this.sheetName = sheetName;
		initWordlist();
	}

	public Topic(ArrayList<Word> wordList, String sheetName) {
		this.wordList = wordList;
		this.sheetName = sheetName;
		Collections.sort(wordList);
	}

	private void initWordlist() {
		Sheet wordSheet = workbook.getSheet(sheetName);

		for (int i = 1; i < wordSheet.getLastRowNum() + 1; i++) {
			Row row = wordSheet.getRow(i);
			String question = row.getCell(ExcelConstants.QUESTION).getStringCellValue();
			String answer = row.getCell(ExcelConstants.ANSWER).getStringCellValue();

			String statistics = "";
			if (row.getCell(ExcelConstants.STATISTICS) != null) {
				statistics = row.getCell(ExcelConstants.STATISTICS).getStringCellValue();
			}
			Word word = new Word(question, answer, sheetName, i);

			for (int index = 0; index < statistics.length(); index++) {
				String s = "" + statistics.charAt(index);
				if (s.equals("o")) {
					word.addCorrectAnswer();
				}
				if (s.equals("x")) {
					word.addIncorrectAnswer();
				}
			}

			wordList.add(word);
		}
		Collections.sort(wordList);

	}

	public ArrayList<Word> getWordList() {
		return wordList;
	}

	public void setWordList(ArrayList<Word> wordList) {
		this.wordList = wordList;
	}
}
