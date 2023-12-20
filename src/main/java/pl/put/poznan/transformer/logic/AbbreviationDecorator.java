package pl.put.poznan.transformer.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.io.File;
import java.util.Scanner;

/**
 * Class for make abbreviations from words.
 * It extends BasicTextTransformer and implements TextTransformer.
 */
public class AbbreviationDecorator extends BasicTextTransformer {
    private final TextTransformer transformer;
    private final ArrayList<String> abbreviations = new ArrayList<>();
    private final ArrayList<String> fullWords = new ArrayList<>();
    private static final Logger logger = LoggerFactory.getLogger(AbbreviationDecorator.class);

    /**
     * Constructor for Abbreviation.
     * This constructor load file with abbreviations and meaning.
     *
     * @param wrappedTransformer Text transformer object to be decorated.
     */
    public AbbreviationDecorator(TextTransformer wrappedTransformer) {
        readFile(abbreviations, fullWords);
        this.transformer = wrappedTransformer;
    }

    /**
     * Method for transforming text.
     *
     * @param text Text to be transformed
     * @return Text after changes with abbreviations.
     */
    @Override
    public String transform(String text) {
        return abbreviation(transformer.transform(text));
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
     * Method for abbreviate words found in input text.
     *
     * @param text Text to be transformed.
     * @return Text with abbreviations if exists.
     */
    public String abbreviation(String text) {
        logger.debug("Doing abbreviation before=" + text + " ");
        String[] split = text.split(" ");
        int totalWordsCount = split.length;
        var toCapitalize = new ArrayList<Integer>();

        var sentenceAfterAllChanges = new ArrayList<String>();
        boolean isAbbreviated = false;
        int i = 0;
        int abbreviateWordsCount = 0;
        int indexOfAbbreviateWord;
        boolean capitalize = false;
        while (i < totalWordsCount) {
            if (split[i].length() == 0) {
                i++;
                continue;
            }
            indexOfAbbreviateWord = 0;
            for (String word : fullWords) {
                toCapitalize.clear();
                String[] fullWordsAbbreviate = word.split(" ");
                abbreviateWordsCount = fullWordsAbbreviate.length;
                int j = i;
                int k = 0;
                isAbbreviated = false;
                capitalize = false;
                while (j < totalWordsCount && k < abbreviateWordsCount && j < i + abbreviateWordsCount) {
                    char firstLetter = split[j].charAt(0);
                    if (Character.isUpperCase(firstLetter)) {
                        capitalize = true;
                        toCapitalize.add(k);
                    }
                    if (split[j].toLowerCase().equals(fullWordsAbbreviate[k])) {
                        isAbbreviated = true;
                    } else {
                        isAbbreviated = false;
                        break;
                    }
                    j++;
                    k++;
                }
                if (isAbbreviated) {
                    break;
                }
                indexOfAbbreviateWord++;
            }
            if (!isAbbreviated) {
                sentenceAfterAllChanges.add(split[i]);
                i++;
            } else {
                if (capitalize) {
                    String out = getString(abbreviations, indexOfAbbreviateWord, toCapitalize);
                    sentenceAfterAllChanges.add(out);
                } else {
                    sentenceAfterAllChanges.add(abbreviations.get(indexOfAbbreviateWord));
                }
                i += abbreviateWordsCount;
            }
        }
        String output = String.join(" ", sentenceAfterAllChanges);
        logger.debug("Doing abbreviation after=" + output);

        return output;
    }

    /**
     * Method for change letter to capitalize if input text had them capitalized.
     *
     * @param abbreviations Array with abbreviations read from file.
     * @param iteration     Index where we found abbreviate words.
     * @param toCapitalize  Array with indexes of words started with capital letter.
     * @return Abbreviated string with optionally big letters.
     */
    private static String getString(ArrayList<String> abbreviations, int iteration, ArrayList<Integer> toCapitalize) {
        StringBuilder out = new StringBuilder();
        int k = 0;
        String singleAbbreviation = abbreviations.get(iteration);
        while (k < singleAbbreviation.length()) {
            String partOfOutput = singleAbbreviation.substring(k, k + 1);
            boolean existBigLetter = toCapitalize.contains(k);
            if (existBigLetter) {
                out.append(partOfOutput.toUpperCase());
            } else {
                out.append(partOfOutput);
            }
            k++;
        }
        return out.toString();
    }
}