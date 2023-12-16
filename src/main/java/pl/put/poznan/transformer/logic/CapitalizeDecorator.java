package pl.put.poznan.transformer.logic;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for capitalizing text.
 * It extends BasicTextTransformer and implements TextTransformer.
 */
public class CapitalizeDecorator extends BasicTextTransformer {
    private final TextTransformer transformer;

    /**
     * Constructor for CapitalizeDecorator.
     * @param wrappedTransformer TextTransformer object to be decorated.
     */
    public CapitalizeDecorator(TextTransformer wrappedTransformer) {
        transformer = wrappedTransformer;
    }

    /**
     * Method for transforming text.
     * It capitalizes each word in text.
     * @param text Text to be transformed
     * @return Capitalized text
     */
    @Override
    public String transform(String text) {
        return capitalize(transformer.transform(text));
    }

    /**
     * Method for capitalizing text.
     * @param text Text to be capitalized
     * @return Capitalized text
     */
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
