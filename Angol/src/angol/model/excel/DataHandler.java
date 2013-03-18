package angol.model.excel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import angol.model.statistics.Word;

public class DataHandler {

	private Workbook workbook;
	private Sheet wordSheet;
	String inputFilePath;
	HashMap<String, Topic> topics;

	public DataHandler(String inputFilePath) {
		this.inputFilePath = inputFilePath;
		try {
			workbook = WorkbookFactory.create(new FileInputStream(inputFilePath));
			wordSheet = workbook.getSheetAt(0);
			topics = new HashMap<String, Topic>();

			ArrayList<Word> allWords = new ArrayList<Word>();
			for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
				String sheetName = workbook.getSheetAt(i).getSheetName();
				Topic topic = new Topic(workbook, sheetName);
				topics.put(sheetName, topic);

				allWords.addAll(topic.getWordList());
			}
			Topic allTopic = new Topic(allWords, "All");
			topics.put("All", allTopic);
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void updateStatisticsCell(int rowNr, String sheetName, String s) {
		System.out.println("sheetName " + sheetName);
		Sheet wordSheet = workbook.getSheet(sheetName);
		Row row = wordSheet.getRow(rowNr);
		Cell statisticsCell = row.getCell(ExcelConstants.STATISTICS);
		if (statisticsCell == null) {
			statisticsCell = row.createCell(ExcelConstants.STATISTICS);
		}
		statisticsCell.setCellValue(s);
	}

	public void saveData() {
		FileOutputStream fileOut;
		try {
			for (int k = 0; k < workbook.getNumberOfSheets(); k++) {
				Sheet wordSheet = workbook.getSheetAt(k);
				Topic topic = topics.get(wordSheet.getSheetName());
				ArrayList<Word> wordList = topic.getWordList();
				Collections.sort(wordList);
				for (int i = 0; i < wordList.size(); i++) {
					Row row = wordSheet.getRow(i + 1);
					Cell question = row.getCell(ExcelConstants.QUESTION);
					Cell answer = row.getCell(ExcelConstants.ANSWER);
					Cell statistics = row.getCell(ExcelConstants.STATISTICS);
					if (statistics == null) {
						statistics = row.createCell(ExcelConstants.STATISTICS);
					}

					question.setCellValue(wordList.get(i).getQuestion());
					answer.setCellValue(wordList.get(i).getAnswer());
					statistics.setCellValue(wordList.get(i).getStatisticsString());
				}

				fileOut = new FileOutputStream(inputFilePath);
				workbook.write(fileOut);
				fileOut.close();
			}
		} catch (IOException e) {
			System.out.println("The save operation failed");
			System.exit(-1);
		}
	}

	public ArrayList<String> getTopicStrings() {
		ArrayList<String> result = new ArrayList<String>();
		for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
			result.add(workbook.getSheetAt(i).getSheetName());
		}
		return result;

	}

	public HashMap<String, Topic> getTopics() {
		return topics;
	}

}