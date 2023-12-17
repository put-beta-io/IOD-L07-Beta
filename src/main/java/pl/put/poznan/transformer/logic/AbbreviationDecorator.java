package pl.put.poznan.transformer.logic;

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

    /**
     * Constructor for Abbreviation.
     * @param wrappedTransformer Text transformer object to be decorated.
     */
    public AbbreviationDecorator(TextTransformer wrappedTransformer) {
        this.transformer = wrappedTransformer;
    }

    /**
     * Method for transforming text.
     * @param text Text to be transformed
     * @return Text after changes with abbreviations.
     */
    @Override
    public String transform(String text) {
        return abbreviation(transformer.transform(text));
    }

    /**
     * Function for read full word and abbreviations from file.
     * @param abbreviations Array with abbreviations.
     * @param fullWords Array with not abbreviated word.
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
     * @param text Text to be transformed.
     * @return Text with abbreviations if exists.
     */
    public String abbreviation(String text) {
        String[] split = text.split(" ");
        int splitLength = split.length;
        var abbreviations = new ArrayList<String>();
        var fullWords = new ArrayList<String>();
        var toCapitalize = new ArrayList<Integer>();

        readFile(abbreviations, fullWords);

        var output = new ArrayList<String>();
        int isAbbreviated = 0;
        int i = 0;
        int size = 0;
        int iteration;
        int capitalize = 0;
        while (i < splitLength) {
            iteration = 0;
            for (String word : fullWords) {
                if (word.length() == 0) {
                    continue;
                }
                toCapitalize.clear();
                String[] splitWords = word.split(" ");
                size = splitWords.length;
                int j = i;
                int k = 0;
                isAbbreviated = 0;
                capitalize = 0;
                while (j < splitLength && k < size && j < i + size) {
                    char firstLetter = split[j].charAt(0);
                    if (Character.isUpperCase(firstLetter)) {
                        capitalize = 1;
                        toCapitalize.add(k);
                    }
                    if (split[j].toLowerCase().equals(splitWords[k])) {
                        isAbbreviated = 1;
                    } else {
                        isAbbreviated = 0;
                        break;
                    }
                    j++;
                    k++;
                }
                if (isAbbreviated == 1) {
                    break;
                }
                iteration++;
            }
            if (isAbbreviated == 0) {
                output.add(split[i]);
                i++;
            } else {
                if (capitalize == 1) {
                    String out = getString(abbreviations, iteration, toCapitalize);
                    output.add(out);
                } else {
                    output.add(abbreviations.get(iteration));
                }
                i += size;
            }
        }
        // When output array is empty add empty string
        if (output.size() == 0) {
            output.add(" ");
        }
        return String.join(" ", output);
    }

    /**
     * Method for change letter to capitalize if input text had them capitalized.
     * @param abbreviations Array with abbreviations read from file.
     * @param iteration Index where we found abbreviate words.
     * @param toCapitalize Array with indexes of words started with capital letter.
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