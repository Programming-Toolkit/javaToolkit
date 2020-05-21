package javaToolkit.lib.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
}
