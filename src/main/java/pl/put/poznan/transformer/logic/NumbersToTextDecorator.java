package pl.put.poznan.transformer.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

/**
 * Class for change numbers to text.
 * It extends BasicTextTransformer and implements TextTransformer.
 */
public class NumbersToTextDecorator extends BasicTextTransformer {
    private final TextTransformer transformer;
    private final ArrayList<String> numbers = new ArrayList<>();
    private final ArrayList<String> textNumbers = new ArrayList<>();
    private static final Logger logger = LoggerFactory.getLogger(NumbersToTextDecorator.class);

    /**
     * Constructor for changing numbers to text.
     * This constructor load file with basic numbers or with specific names.
     *
     * @param wrappedTransformer Text transformer object to be decorated.
     */
    public NumbersToTextDecorator(TextTransformer wrappedTransformer) {
        readFile(numbers, textNumbers);
        this.transformer = wrappedTransformer;
    }

    /**
     * Method for transforming text.
     *
     * @param text Text to be transformed
     * @return Text after changing.
     */
    @Override
    public String transform(String text) {
        return numbersToText(transformer.transform(text));
    }

    /**
     * Method check if text can be converted to Integer.
     *
     * @param strNum Specific word from sentence.
     * @return True or False depends if value can be converted or not.
     */
    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            int d = Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    /**
     * Function for read numbers and their specific names.
     *
     * @param numbers     Array with numbers.
     * @param textNumbers Array with names of specific number.
     */
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

    /**
     * Main method that get text, split it and change numbers to text.
     *
     * @param text Text to be transformed.
     * @return Text after change.
     */
    public String numbersToText(String text) {
        logger.debug("Doing number to text before=" + text);
        String[] split = text.split(" ");
        int totalWordsCount = split.length;

        var transformedText = new ArrayList<String>();
        int i = 0;
        int lengthOfNumber;
        boolean foundNumber;
        boolean isDouble;
        String[] numberWithDecimal = new String[0];
        while (i < totalWordsCount) {
            if (split[i].length() == 0) {
                i++;
                continue;
            }
            // Check if value is decimal
            isDouble = split[i].contains(",");
            if (isDouble) {
                numberWithDecimal = split[i].split(",");
                if (numberWithDecimal.length != 1) {
                    if (numberWithDecimal[0].length() != 0 && numberWithDecimal[1].length() != 0) {
                        boolean intValue = isNumeric(numberWithDecimal[0]);
                        boolean decimalValue = isNumeric(numberWithDecimal[1]);
                        if (intValue && decimalValue) {
                            split[i] = numberWithDecimal[0];
                        }
                    }
                } else {
                    isDouble = false;
                }
            }

            // Change value to name
            if (isNumeric(split[i])) {
                if (Integer.parseInt(split[i]) == 0) {
                    transformedText.add("zero");
                } else {
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
                    } else if (lengthOfNumber > 3 && !foundNumber) {
                        transformedText.add(findNumber(split[i], lengthOfNumber));
                    }
                }
            } else {
                transformedText.add(split[i]);
            }
            // Work with decimal value
            if (isDouble) {
                String inputString = numberWithDecimal[1];
                if (numberWithDecimal[1].length() == 1) {
                    inputString += "0";
                }
                if (Integer.parseInt(numberWithDecimal[1].substring(1, 2)) < 5 && !numberWithDecimal[1].substring(1, 2).equals("0")) {
                    transformedText.add("i " + numbersBelowHundred(inputString) + " setne");
                } else {
                    transformedText.add("i " + numbersBelowHundred(inputString) + " setnych");
                }
            }
            i++;
        }
        String output = String.join(" ", transformedText);
        logger.debug("Doing number to text after=" + output);
        return output;
    }

    /**
     * Method that takes numbers in range 10-99 and convert them to words
     *
     * @param textAsNumber String text of number length 2.
     * @return Number converted to name in words.
     */
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

            if (firstInText.equals("0")) {
                noAdditionalDigit = true;
                numberToText.add(findNumberTextName(secondInText));
            } else if (11 < firstInNumber && firstInNumber < 20) {
                numberToText.add(findNumberTextName(secondInText) + "naście");
                noAdditionalDigit = true;
            } else if (19 < firstInNumber && firstInNumber < 30) {
                numberToText.add(findNumberTextName(firstInText) + "dzieścia");
            } else if (29 < firstInNumber && firstInNumber < 50) {
                if (firstInText.equals("4")) {
                    numberToText.add("czterdzieści");
                } else {
                    numberToText.add(findNumberTextName(firstInText) + "dzieści");
                }

            } else if (49 < firstInNumber && firstInNumber < 100) {
                numberToText.add(findNumberTextName(firstInText) + "dziesiąt");
            }
            if (!secondInText.equals("0") && !noAdditionalDigit) {
                numberToText.add(findNumberTextName(secondInText));
            }
        }
        return String.join(" ", numberToText);
    }

    /**
     * Method that look for specific number in array that contains info from file.
     *
     * @param number String of number.
     * @return Full name of number in words.
     */
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

    /**
     * Method that takes numbers in range 100-999 and convert them to words
     *
     * @param textAsNumber String text of number of length 3.
     * @return Number converted to name in words.
     */
    public String numberBelowThousand(String textAsNumber) {
        var numberToText = new ArrayList<String>();

        int firstInNumber = Integer.parseInt(textAsNumber.substring(0, 1));
        String firstInTextHundred = textAsNumber.substring(0, 1);
        String restOfText = textAsNumber.substring(1, 3);
        if (restOfText.equals("00")) {
            numberToText.add(findNumberTextName(firstInTextHundred) + "set");
        } else {
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

    /**
     * Method that converts number in range 1000-99999.
     *
     * @param textAsNumber String of number.
     * @param length       Length of provided string.
     * @return Number converted to name in words.
     */
    public String findNumber(String textAsNumber, int length) {
        var numberToText = new ArrayList<String>();
        String restOfValue;
        if (length == 4) {
            int firstInNumberThousand = Integer.parseInt(textAsNumber.substring(0, 1));
            String firstInTextThousand = textAsNumber.substring(0, 1);
            restOfValue = textAsNumber.substring(1, 4);
            if (firstInTextThousand.equals("0")) {
                numberToText.add(numberBelowThousand(restOfValue));
            } else if (firstInNumberThousand == 1) {
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
            restOfValue = textAsNumber.substring(2, 5);
            String ending = " tysięcy";
            int secondValue = Integer.parseInt(textAsNumber.substring(1, 2));
            if (secondValue < 5 && secondValue != 0) {
                ending = " tysiące";
            }
            numberToText.add(numbersBelowHundred(firstTwo) + ending);
            numberToText.add(numberBelowThousand(restOfValue));
        }
        return String.join(" ", numberToText);
    }
}