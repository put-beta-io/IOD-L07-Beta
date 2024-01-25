package pl.put.poznan.transformer.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for capitalizing text.
 * It extends BasicTextTransformer and implements TextTransformer.
 */
public class CapitalizeDecorator extends BasicTextTransformer {
    private final TextTransformer transformer;
    private final Logger logger;

    /**
     * Constructor for CapitalizeDecorator.
     *
     * @param wrappedTransformer TextTransformer object to be decorated.
     */
    public CapitalizeDecorator(TextTransformer wrappedTransformer, Logger l) {
        logger = l;
        transformer = wrappedTransformer;
    }

    /**
     * Method for transforming text.
     * It capitalizes each word in text.
     *
     * @param text Text to be transformed
     * @return Capitalized text
     */
    @Override
    public String transform(String text) {
        return capitalize(transformer.transform(text));
    }

    /**
     * Method for capitalizing text.
     *
     * @param text Text to be capitalized
     * @return Capitalized text
     */
    private String capitalize(String text) {
        logger.debug("Doing capitalize before=" + text);
        var split = List.of(text.split(" "));
        var words = new ArrayList<String>();
        for (String word : split) {
            // when empty string is split, it creates an empty string in an array
            // resulting in an empty word first letter access to be capitalized
            if (word.length() == 0) {
                continue;
            }
            String capitalize = word.substring(0, 1).toUpperCase() + word.substring(1);
            words.add(capitalize);
        }
        String output = String.join(" ", words);
        logger.debug("Doing capitalize after=" + output);
        return output;
    }
}
