package pl.put.poznan.transformer.logic;

import java.util.ArrayList;

/**
 * TextTransformerBuilder is a class that builds a TextTransformer object.
 * It is used to create a TextTransformer object with a specific set of decorators.
 * It allows adding transformations to build TextTransformer object.
 * Once all transformations are added, getTransformer() method returns a TextTransformer object.
 * Class can be also constructed with a list of transformations to be added.
 */
public class TextTransformerBuilder {
    /**
     * List of transformations to be added to the TextTransformer object.
     */
    private final ArrayList<String> transformations;

    /**
     * Default constructor.
     * Initializes transformations list.
     */
    public TextTransformerBuilder() {
        this.transformations = new ArrayList<>();
    }

    /**
     * Constructor with a list of transformations to be added.
     * @param transformations List of transformations to be added.
     */
    public TextTransformerBuilder(ArrayList<String> transformations) {
        this.transformations = transformations;
    }

    /**
     * Adds a transformation to the list of transformations to be added.
     * @param transformationCommand Transformation to be added.
     */
    public void addTransformation(String transformationCommand) {
        transformations.add(transformationCommand);
    }

    /**
     * Returns a TextTransformer object with all transformations added.
     * All possible transformations should be added to switch-case statement.
     * @return TextTransformer object with all transformations added.
     */
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
                case "abbreviate":
                    textTransformer = new AbbreviationDecorator(textTransformer);
                    break;
                case "numbers":
                    textTransformer = new NumbersToTextDecorator(textTransformer);
                    break;
            }
        }
        return textTransformer;
    }
}
