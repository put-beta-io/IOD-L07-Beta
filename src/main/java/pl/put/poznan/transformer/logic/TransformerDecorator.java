package pl.put.poznan.transformer.logic;

public class TransformerDecorator implements TextTransformer {
    private final TextTransformer transformer;

    public TransformerDecorator(TextTransformer wrappedTransformer) {
        transformer = wrappedTransformer;
    }

    @Override
    public String transform(String text) {
        return transformer.transform(text);
    }
}
