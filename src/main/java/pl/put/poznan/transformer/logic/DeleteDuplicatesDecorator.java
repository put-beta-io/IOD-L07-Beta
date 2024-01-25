package pl.put.poznan.transformer.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/**
 * Class for deleting duplicated words that are next to each other
 * It extends BasicTextTransformer and implements TextTransformer
 */
public class DeleteDuplicatesDecorator extends BasicTextTransformer {

    private final TextTransformer transformer;
    private final Logger logger;

    /**
     * Constructor for DeleteDuplicatesDecorator.
     *
     * @param wrappedTransformer Text transformer object to be decorated.
     */
    public DeleteDuplicatesDecorator(TextTransformer wrappedTransformer, Logger l) {
        logger = l;
        transformer = wrappedTransformer;
    }

    /**
     * Method for transforming text.
     *
     * @param text Text to be transformed
     * @return Text without duplicated words next to each other
     */
    @Override
    public String transform(String text) {
        logger.debug("Delete duplicates before=" + text);
        String transformed = deleteDuplicates(transformer.transform(text));
        logger.debug("Delete duplicates after=" + transformed);
        return transformed;
    }

    /**
     * Function for transforming text.
     *
     * @param text Text to be transformed
     * @return Text without duplicated words next to each other
     */
    static public String deleteDuplicates(String text) {
        String[] words = text.split(" ");
        var result = new StringBuilder();

        int i = 0;
        while (i < words.length) {
            while (i < words.length - 1 && words[i].equals(words[i + 1])) {
                i++;
            }
            result.append(words[i]);
            if (i < words.length - 1 && !words[i].equals("")) {
                result.append(" ");
            }
            i++;
        }
        return result.toString();
    }
}


