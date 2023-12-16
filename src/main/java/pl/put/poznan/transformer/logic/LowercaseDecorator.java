package pl.put.poznan.transformer.logic;

/**
 * Class for transforming text to lowercase
 * It extends BasicTextTransformer and implements TextTransformer
 */
public class LowercaseDecorator extends BasicTextTransformer {
    private final TextTransformer transformer;

    /**
     * Constructor for LowercaseDecorator
     * @param wrappedTransformer Transformer to be wrapped
     */
    public LowercaseDecorator(TextTransformer wrappedTransformer) {
        transformer = wrappedTransformer;
    }

    /**
     * Method for transforming text
     * @param text Text to be transformed
     * @return Transformed text
     */
    @Override
    public String transform(String text) {
        return transformer.transform(text).toLowerCase();
    }
}
