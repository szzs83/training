package angol.model.statistics;

import java.util.Random;

public class WordSelector {

	public static int chooseWordIndex(int size) {
		int rand = new Random().nextInt(6);
		int index = 0;

		if (rand == 0 || rand == 1 || rand == 2) {
			index = new Random().nextInt(size / 3);
		} else {
			if (rand == 3 || rand == 4) {
				index = size / 3 + new Random().nextInt(size / 3);
			} else {
				if (rand == 5) {
					if (size % 3 == 0) {
						index = size / 3 * 2 + new Random().nextInt(size / 3);

					}
					if (size % 3 == 1) {
						index = size / 3 * 2 + new Random().nextInt(size / 3 + 1);
					}
					if (size % 3 == 2) {
						index = size / 3 * 2 + new Random().nextInt(size / 3 + 2);
					}
				}
			}

		}

		return index;
	}
}
