package pl.put.poznan.transformer.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class for transforming text to lowercase
 * It extends BasicTextTransformer and implements TextTransformer
 */
public class LowercaseDecorator extends BasicTextTransformer {
    private final TextTransformer transformer;
    private final Logger logger;

    /**
     * Constructor for LowercaseDecorator
     *
     * @param wrappedTransformer Transformer to be wrapped
     */
    public LowercaseDecorator(TextTransformer wrappedTransformer, Logger l) {
        logger = l;
        transformer = wrappedTransformer;
    }

    /**
     * Method for transforming text
     *
     * @param text Text to be transformed
     * @return Transformed text
     */
    @Override
    public String transform(String text) {
        logger.debug("Doing lowercase before=" + text);
        String output = transformer.transform(text).toLowerCase();
        logger.debug("Doing lowercase after=" + output);
        return output;
    }
}
