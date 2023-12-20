package pl.put.poznan.transformer.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/**
 * Class for reversing the text with uppercase letters positions left unchanged
 * It extends BasicTextTransformer and implements TextTransformer
 */
public class ReverseDecorator extends BasicTextTransformer {

    private final TextTransformer transformer;
    private static final Logger logger = LoggerFactory.getLogger(ReverseDecorator.class);

    /**
     * Constructor for Abbreviation.
     * This constructor load file with abbreviations and meaning.
     *
     * @param wrappedTransformer Text transformer object to be decorated.
     */
    public ReverseDecorator(TextTransformer wrappedTransformer) {
        transformer = wrappedTransformer;
    }

    /**
     * Method for transforming text.
     *
     * @param text Text to be transformed
     * @return Text reversed with uppercase letters positions left unchanged
     */
    @Override
    public String transform(String text) {
        return reverse(transformer.transform(text));
    }

    /**
     * Function for transforming text.
     *
     * @param text Text to be transformed
     * @return Text reversed with uppercase letters positions left unchanged
     */
    static public String reverse(String text) {
        logger.debug("Doing reverse before=" + text);
        var indices = new ArrayList<Integer>();
        for (int i = 0; i < text.length(); i++) {
            if (Character.isUpperCase(text.charAt(i))) {
                indices.add(i);
            }
        }
        var reversed = new StringBuilder(text).reverse();
        for (int i = 0; i < text.length(); i++) {
            if (indices.contains(i)) {
                var c = Character.toUpperCase(reversed.charAt(i));
                reversed.setCharAt(i, c);
            }
        }
        String output = reversed.toString();
        logger.debug("Doing reverse after=" + output);
        return output;
    }
}


