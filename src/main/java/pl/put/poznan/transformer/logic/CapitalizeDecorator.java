package pl.put.poznan.transformer.logic;

import java.util.ArrayList;
import java.util.List;

public class CapitalizeDecorator extends BasicTextTransformer {
    private final TextTransformer transformer;

    public CapitalizeDecorator(TextTransformer wrappedTransformer) {
        transformer = wrappedTransformer;
    }

    @Override
    public String transform(String text) {
        return capitalize(transformer.transform(text));
    }

    private String capitalize(String text) {
        var split = List.of(text.split(" "));
        var words = new ArrayList<String>();
        for (String word : split) {
            String capitalize = word.substring(0, 1).toUpperCase() + word.substring(1);
            words.add(capitalize);
        }
        return String.join(" ", words);
    }
}
