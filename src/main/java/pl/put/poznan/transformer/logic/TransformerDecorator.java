package pl.put.poznan.transformer.logic;

/**
 * Abstract clas for text transformers.
 * Each transformer should implement this interface.
 */
public class TransformerDecorator implements TextTransformer {
    private final TextTransformer transformer;

    /**
     * Constructor for TransformerDecorator
     * @param wrappedTransformer Transformer to be wrapped
     */
    public TransformerDecorator(TextTransformer wrappedTransformer) {
        transformer = wrappedTransformer;
    }

    /**
     * Method for transforming text
     * @param text Text to be transformed
     * @return Transformed text
     */
    @Override
    public String transform(String text) {
        return transformer.transform(text);
    }
}
