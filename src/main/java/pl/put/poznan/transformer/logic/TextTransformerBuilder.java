package pl.put.poznan.transformer.logic;

import java.util.ArrayList;

public class TextTransformerBuilder {
    private final ArrayList<String> transformations;

    public TextTransformerBuilder() {
        this.transformations = new ArrayList<>();
    }

    public TextTransformerBuilder(ArrayList<String> transformations) {
        this.transformations = transformations;
    }

    public void addTransformation(String transformationCommand) {
        transformations.add(transformationCommand);
    }

    public TextTransformer getTransformer() {
        TextTransformer textTransformer = new TransformerDecorator(new BasicTextTransformer());
        for (var transform : transformations) {
            switch (transform) {
                case "upper":
                    textTransformer = new UppercaseDecorator(textTransformer);
                    break;
                case "lower":
                    textTransformer = new LowercaseDecorator(textTransformer);
                    break;
                case "capitalize":
                    textTransformer = new CapitalizeDecorator(textTransformer);
                    break;
            }
        }
        return textTransformer;
    }
}
