package pl.put.poznan.transformer.logic;

import org.slf4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class for make full words from abbreviations provided in text.
 * It extends BasicTextTransformer and implements TextTransformer.
 */
public class AbbreviationToWordDecorator extends BasicTextTransformer {
    private final TextTransformer transformer;
    private final ArrayList<String> abbreviations = new ArrayList<>();
    private final ArrayList<String> fullWords = new ArrayList<>();
    private final Logger logger;

    /**
     * Constructor for AbbreviationToWordDecorator.
     * This constructor load file with abbreviations and meaning.
     *
     * @param wrappedTransformer Text transformer object to be decorated.
     */
    public AbbreviationToWordDecorator(TextTransformer wrappedTransformer, Logger l) {
        logger = l;
        readFile(abbreviations, fullWords);
        this.transformer = wrappedTransformer;
    }

    /**
     * Method for transforming text.
     *
     * @param text Text to be transformed
     * @return Text after changes with full words instead of abbreviations.
     */
    @Override
    public String transform(String text) {
        logger.debug("Doing abbreviation to full words before=" + text + " ");
        String output = toFullWord(transformer.transform(text));
        logger.debug("Doing abbreviation to full words after=" + output + " ");
        return output;
    }

    /**
     * Function for read full word and abbreviations from file.
     *
     * @param abbreviations Array with abbreviations.
     * @param fullWords     Array with not abbreviated word.
     */
    public void readFile(ArrayList<String> abbreviations, ArrayList<String> fullWords) {
        String[] wordsArray;
        try {
            File myObj = new File("src/main/resources/abbreviation.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                wordsArray = data.split("\t");
                abbreviations.add(wordsArray[0]);
                fullWords.add(wordsArray[1]);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
        }
    }

    /**
     * Method for changing abbreviations found in text to their full meaning.
     *
     * @param text Text to be transformed.
     * @return Text with full words instead of their abbreviated version.
     */
    public String toFullWord(String text) {
        String[] split = text.split(" ");
        ArrayList<String> outputText = new ArrayList<>();
        int totalWordsCount = split.length;
        int indexOfFullWord;

        int i = 0;
        while (i < totalWordsCount) {
            if (split[i].length() == 0) {
                i++;
                continue;
            }
            indexOfFullWord = -1;
            for (String abv : abbreviations) {
                if (split[i].toLowerCase().equals(abv)) {
                    indexOfFullWord = abbreviations.indexOf(abv);
                }
            }
            if (indexOfFullWord == -1) {
                outputText.add(split[i]);
            } else {
                char firstLetter = split[i].charAt(0);
                if (Character.isUpperCase(firstLetter)) {
                    String makeFirstLetterUpper = fullWords.get(indexOfFullWord).substring(0, 1).toUpperCase() + fullWords.get(indexOfFullWord).substring(1);
                    outputText.add(makeFirstLetterUpper);
                } else {
                    outputText.add(fullWords.get(indexOfFullWord));
                }
            }
            i++;
        }
        return String.join(" ", outputText);
    }
}
