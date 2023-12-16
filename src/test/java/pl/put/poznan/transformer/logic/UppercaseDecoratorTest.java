package pl.put.poznan.transformer.logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UppercaseDecoratorTest {
    private TextTransformer transformer;

    @BeforeEach
    void setUp() {
        TextTransformerBuilder builder = new TextTransformerBuilder();
        builder.addTransformation("upper");
        transformer = builder.getTransformer();
    }

    @Test
    void testDecorateUppercaseAllLower() {
        String got = transformer.transform("cheetah");

        assertEquals("CHEETAH", got);
    }

    @Test
    void testDecorateUppercaseMixed() {
        String got = transformer.transform("aBcDeF");

        assertEquals("ABCDEF", got);
    }

    @Test
    void testDecorateUppercaseWithSpaces() {
        String got = transformer.transform("a b c d e f g");

        assertEquals("A B C D E F G", got);
    }

    @Test
    void testDecorateUppercaseEmptyString() {
        String got = transformer.transform("");

        assertEquals("", got);
    }
}