package pl.put.poznan.transformer.logic;

public class LowercaseDecorator extends BasicTextTransformer {
    private final TextTransformer transformer;

    public LowercaseDecorator(TextTransformer wrappedTransformer) {
        transformer = wrappedTransformer;
    }

    @Override
    public String transform(String text) {
        return transformer.transform(text).toLowerCase();
    }
}
