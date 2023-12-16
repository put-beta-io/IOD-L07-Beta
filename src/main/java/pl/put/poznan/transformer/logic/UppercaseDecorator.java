package pl.put.poznan.transformer.logic;

/**
 * Class for transforming text to uppercase.
 * It extends BasicTextTransformer and implements TextTransformer.
 */
public class UppercaseDecorator extends BasicTextTransformer {
    private final TextTransformer transformer;

    /**
     * Constructor for UppercaseDecorator.
     * @param wrappedTransformer TextTransformer object to be decorated.
     */
    public UppercaseDecorator(TextTransformer wrappedTransformer) {
        transformer = wrappedTransformer;
    }

    /**
     * Method for transforming text.
     * @param text Text to be transformed
     * @return Text converted to uppercase
     */
    @Override
    public String transform(String text) {
        return transformer.transform(text).toUpperCase();
    }
}
