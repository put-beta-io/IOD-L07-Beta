package pl.put.poznan.transformer.logic;

import org.slf4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class for make signs from normal text into latex format signs..
 * It extends BasicTextTransformer and implements TextTransformer.
 */
public class FormatSignsToLatexDecorator extends BasicTextTransformer {
    private final TextTransformer transformer;
    private final ArrayList<String> normalSign = new ArrayList<>();
    private final ArrayList<String> latexSign = new ArrayList<>();
    private final Logger logger;

    /**
     * Constructor for FormatSignsToLatexDecorator
     * This constructor load file with signs and their latex format.
     *
     * @param transformer Text transformer object to be decorated.
     */
    public FormatSignsToLatexDecorator(TextTransformer transformer, Logger l) {
        logger = l;
        readFile(normalSign, latexSign);
        this.transformer = transformer;
    }

    /**
     * Method for transforming text.
     *
     * @param text Text to be transformed
     * @return Text after changes with signs in latex format.
     */
    @Override
    public String transform(String text) {
        logger.debug("Doing formatting signs before=" + text + " ");
        String output = formatToLatex(transformer.transform(text));
        logger.debug("Doing formatting signs after=" + output + " ");
        return output;
    }

    /**
     * Function that read normal signs and their latex format version.
     *
     * @param normalSign Array with normal signs loaded from file.
     * @param latexSign  Array with latex format signs loaded from file
     */
    public void readFile(ArrayList<String> normalSign, ArrayList<String> latexSign) {
        String[] wordsArray;
        try {
            File myObj = new File("src/main/resources/latexSigns.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                wordsArray = data.split("\t");
                normalSign.add(wordsArray[0]);
                latexSign.add(wordsArray[1]);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
        }
    }

    /**
     * Method for changing normal sings in provided text to their versions in latex.
     *
     * @param text Text to be transformed.
     * @return Text after changes, signs will be written in latex format.
     */
    public String formatToLatex(String text) {
        String[] splitedText = text.split(" ");
        ArrayList<String> outputText = new ArrayList<>();
        int totalTextCount = splitedText.length;
        int i = 0;
        int indexOfLatexSign;
        while (i < totalTextCount) {
            if (splitedText[i].length() == 0) {
                i++;
                continue;
            }
            indexOfLatexSign = -1;
            for (String sign : normalSign) {
                if (splitedText[i].equals(sign)) {
                    indexOfLatexSign = normalSign.indexOf(sign);
                }
            }

            if (indexOfLatexSign == -1) {
                outputText.add(splitedText[i]);
            } else {
                outputText.add(latexSign.get(indexOfLatexSign));
            }
            i++;
        }
        return String.join(" ", outputText);
    }
}
