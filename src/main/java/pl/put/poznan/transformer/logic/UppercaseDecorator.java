package pl.put.poznan.transformer.logic;

public class UppercaseDecorator extends BasicTextTransformer {
    private final TextTransformer transformer;

    public UppercaseDecorator(TextTransformer wrappedTransformer) {
        transformer = wrappedTransformer;
    }

    @Override
    public String transform(String text) {
        return transformer.transform(text).toUpperCase();
    }
}
