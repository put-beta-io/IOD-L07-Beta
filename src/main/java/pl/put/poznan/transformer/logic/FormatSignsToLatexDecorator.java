package pl.put.poznan.transformer.logic;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class FormatSignsToLatexDecorator extends BasicTextTransformer {
    private final TextTransformer transformer;
    private final ArrayList<String> normalSign = new ArrayList<>();
    private final ArrayList<String> latexSign = new ArrayList<>();

    public FormatSignsToLatexDecorator(TextTransformer transformer) {
        readFile(normalSign, latexSign);
        this.transformer = transformer;
    }

    @Override
    public String transform(String text) {
        return formatToLatex(transformer.transform(text));
    }

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
            }else{
                outputText.add(latexSign.get(indexOfLatexSign));
            }
            i++;
        }
        return String.join(" ", outputText);
    }
}
