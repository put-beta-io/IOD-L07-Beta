package pl.put.poznan.transformer.logic;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

/**
 * Class for transforming text to uppercase.
 * It extends BasicTextTransformer and implements TextTransformer.
 */
public class UppercaseDecorator extends BasicTextTransformer {
    private final TextTransformer transformer;
    private final Logger logger;
    /**
     * Constructor for UppercaseDecorator.
     * @param wrappedTransformer TextTransformer object to be decorated.
     */
    public UppercaseDecorator(TextTransformer wrappedTransformer, Logger l) {
        transformer = wrappedTransformer;
        logger = l;
    }

    /**
     * Method for transforming text.
     * @param text Text to be transformed
     * @return Text converted to uppercase
     */
    @Override
    public String transform(String text) {
        logger.debug("Doing uppercase before="+text);
        String output = transformer.transform(text).toUpperCase();
        logger.debug("Doing uppercase after="+text);
        return output;
    }
}
