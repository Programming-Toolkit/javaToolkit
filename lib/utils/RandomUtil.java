package javaToolkit.lib.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class RandomUtil {

	public Object getARandomElement(List<?> givenList) {
		Random rand = new Random();
		Object randomElement = givenList.get(rand.nextInt(givenList.size()));
		return randomElement;
	}

	public List<Object> randomSelectWithRepeat(List<Object> givenList, int numberOfElements) {
		Random rand = new Random();
		List<Object> selectedEles = new ArrayList<>();

		for (int i = 0; i < numberOfElements; i++) {
			int randomIndex = rand.nextInt(givenList.size());
			Object randomElement = givenList.get(randomIndex);
			selectedEles.add(randomElement);
		}

		return selectedEles;
	}

	public List<?> randomSelectWithoutRepeat(List<?> givenList, int numberOfElements) {
		Random rand = new Random();
		List<Object> selectedEles = new ArrayList<>();

		for (int i = 0; i < numberOfElements; i++) {
			int randomIndex = rand.nextInt(givenList.size());
			Object randomElement = givenList.get(randomIndex);
			selectedEles.add(randomElement);
			givenList.remove(randomIndex);
		}

		return selectedEles;
	}

	/**
	 * include max and min
	 * 
	 * @param min
	 * @param max
	 * @param k
	 * @return
	 */
	public static Set<Integer> randomGenerateKDistinctNumbers(int min, int max, int k) {
		Random rand = new Random();
		Set<Integer> selectedEles = new HashSet<>();

		if (max - min < k) {
			System.out.printf("Cannot generate %s between %s and %s\n", k, min, max);
			System.exit(0);
		}

		while (selectedEles.size() < k) {
			int randomNum = rand.nextInt((max - min) + 1) + min;
			selectedEles.add(randomNum);
		}

		return selectedEles;
	}

	/**
	 * include max and min, not contain
	 * 
	 * @param min
	 * @param max
	 * @param k
	 * @return
	 * @throws Exception 
	 */
	public static Set<Integer> randomKDistinctNumsWithoutSpecificNum(int min, int max, int k, int withoutNum) throws Exception {
		Random rand = new Random();
		Set<Integer> selectedEles = new HashSet<>();

		if (max - min < k) {
			System.out.printf("Cannot generate %s between %s and %s\n", k, min, max);
			throw new Exception("Cannot generate " + k + " between " + min + " and " + max + "\n");
		}

		while (selectedEles.size() < k) {
			int randomNum = rand.nextInt((max - min) + 1) + min;
			if (randomNum != withoutNum) {
				selectedEles.add(randomNum);
			}
		}

		return selectedEles;
	}

}
