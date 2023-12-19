package pl.put.poznan.transformer.logic;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class NumbersToTextDecorator extends BasicTextTransformer {
    private final TextTransformer transformer;
    private final ArrayList<String> numbers = new ArrayList<>();
    private final ArrayList<String> textNumbers = new ArrayList<>();

    public NumbersToTextDecorator(TextTransformer wrappedTransformer) {
        readFile(numbers, textNumbers);
        this.transformer = wrappedTransformer;
    }

    @Override
    public String transform(String text) {
        return numbersToText(transformer.transform(text));
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public void readFile(ArrayList<String> numbers, ArrayList<String> textNumbers) {
        String[] line;
        try {
            File myObj = new File("src/main/resources/numbers.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                line = data.split("\t");
                numbers.add(line[0]);
                textNumbers.add(line[1]);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
        }
    }

    public String numbersToText(String text) {
        String[] split = text.split(" ");
        int totalWordsCount = split.length;

        var transformedText = new ArrayList<String>();
        int i = 0;
        int lengthOfNumber;
        boolean foundNumber;
        while (i < totalWordsCount) {
            if (isNumeric(split[i])) {
                foundNumber = false;
                lengthOfNumber = split[i].length();
                int j = 0;
                while (j < numbers.size()) {
                    if (Objects.equals(numbers.get(j), split[i])) {
                        transformedText.add(textNumbers.get(j));
                        foundNumber = true;
                        break;
                    }
                    j++;
                }

                if (lengthOfNumber == 2 && !foundNumber) {
                    transformedText.add(numbersBelowHundred(split[i]));
                } else if (lengthOfNumber == 3 && !foundNumber) {
                    transformedText.add(numberBelowThousand(split[i]));
                }else if (lengthOfNumber > 3 && !foundNumber){
                    transformedText.add(findNumber(split[i],lengthOfNumber));
                }
            } else {
                transformedText.add(split[i]);
            }
            i++;
        }
        return String.join(" ", transformedText);
    }

    public String numbersBelowHundred(String textAsNumber) {
        boolean foundNumber = false;
        var numberToText = new ArrayList<String>();

        int j = 0;
        while (j < numbers.size()) {
            if (Objects.equals(numbers.get(j), textAsNumber)) {
                foundNumber = true;
                break;
            }
            j++;
        }
        if (foundNumber) {
            numberToText.add(textNumbers.get(j));
        } else {
            int firstInNumber = Integer.parseInt(textAsNumber);
            String firstInText = textAsNumber.substring(0, 1);
            String secondInText = textAsNumber.substring(1, 2);
            boolean noAdditionalDigit = false;

            if (firstInText.equals("0")){
                noAdditionalDigit = true;
                numberToText.add(findNumberTextName(secondInText));
            } else if (11 < firstInNumber && firstInNumber < 20) {
                numberToText.add(findNumberTextName(secondInText) + "naście");
                noAdditionalDigit = true;
            } else if (19 < firstInNumber && firstInNumber < 30) {
                numberToText.add(findNumberTextName(firstInText) + "dzieścia");
            } else if (29 < firstInNumber && firstInNumber < 50) {
                if (firstInText.equals("4")){
                    numberToText.add("czterdzieści");
                }else{
                    numberToText.add(findNumberTextName(firstInText) + "dzieści");
                }

            } else if (49 < firstInNumber && firstInNumber < 100) {
                numberToText.add(findNumberTextName(firstInText) + "dziesiąt");
            }
            if(!secondInText.equals("0") && !noAdditionalDigit){
                numberToText.add(findNumberTextName(secondInText));
            }
        }
        return String.join(" ", numberToText);
    }

    public String findNumberTextName(String number) {
        int j = 0;
        while (j < numbers.size()) {
            if (Objects.equals(numbers.get(j), number)) {
                break;
            }
            j++;
        }
        return textNumbers.get(j);
    }

    public String numberBelowThousand(String textAsNumber) {
        var numberToText = new ArrayList<String>();

        int firstInNumber = Integer.parseInt(textAsNumber.substring(0, 1));
        String firstInTextHundred = textAsNumber.substring(0, 1);
        String restOfText = textAsNumber.substring(1, 3);
        if (restOfText.equals("00")){
            numberToText.add(findNumberTextName(firstInTextHundred) + "set");
        }else {
            if (firstInTextHundred.equals("0")) {
                numberToText.add(numbersBelowHundred(restOfText));
            }
            if (firstInTextHundred.equals("1")) {
                numberToText.add("sto " + numbersBelowHundred(restOfText));
            }
            if (firstInTextHundred.equals("2")) {
                numberToText.add("dwieście " + numbersBelowHundred(restOfText));
            }
            if (firstInNumber == 3 || firstInNumber == 4) {
                numberToText.add(findNumberTextName(firstInTextHundred) + "sta");
                numberToText.add(numbersBelowHundred(restOfText));
            }
            if (firstInNumber >= 5) {
                numberToText.add(findNumberTextName(firstInTextHundred) + "set");
                numberToText.add(numbersBelowHundred(restOfText));
            }
        }
        return String.join(" ", numberToText);
    }

    public String findNumber(String textAsNumber, int length) {
        var numberToText = new ArrayList<String>();
        String restOfValue;
        if (length == 4) {
            int firstInNumberThousand = Integer.parseInt(textAsNumber.substring(0, 1));
            String firstInTextThousand = textAsNumber.substring(0, 1);
            restOfValue = textAsNumber.substring(1, 4);
            if (firstInNumberThousand == 1) {
                numberToText.add("tysiąc");
                numberToText.add(numberBelowThousand(restOfValue));
            } else {
                if (firstInNumberThousand < 5) {
                    numberToText.add(findNumberTextName(firstInTextThousand) + " tysiące");
                    numberToText.add(numberBelowThousand(restOfValue));
                } else {
                    numberToText.add(findNumberTextName(firstInTextThousand) + " tysięcy");
                    numberToText.add(numberBelowThousand(restOfValue));
                }
            }
        }
        if (length == 5) {
            String firstTwo = textAsNumber.substring(0, 2);
            restOfValue = textAsNumber.substring(2,5);
            String ending = " tysięcy";
            int secondValue = Integer.parseInt(textAsNumber.substring(1,2));
            if (secondValue < 5 && secondValue != 0){
                ending = " tysiące";
            }
            numberToText.add(numbersBelowHundred(firstTwo) + ending);
            numberToText.add(numberBelowThousand(restOfValue));
        }
        return String.join(" ", numberToText);
    }
}