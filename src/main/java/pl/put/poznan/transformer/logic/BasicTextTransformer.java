package pl.put.poznan.transformer.logic;

/**
 * Abstract class for text transformers.
 * Each transformer should extend this class.
 */
public class BasicTextTransformer implements TextTransformer {
    /**
     * Method for transforming text
     * BasicTextTransformer does not transform text in any way, so it returns the same text
     * @param text Text to be transformed
     * @return Transformed text
     */
    @Override
    public String transform(String text) {
        return text;
    }
}

