package pl.put.poznan.transformer.logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReverseDecoratorTest {
    private TextTransformer transformer;

    @BeforeEach
    void setUp() {
        TextTransformerBuilder builder = new TextTransformerBuilder();
        builder.addTransformation("reverse");
        transformer = builder.getTransformer();
    }

    @Test
    void testDecorateReverseEmptyString() {
        String got = transformer.transform("");

        assertEquals("", got);
    }

    @Test
    void testDecorateReverseSingleWord() {
        String got = transformer.transform("apple");

        assertEquals("elppa", got);
    }

    @Test
    void testDecorateReverseSingleLetter() {
        String got = transformer.transform("a");

        assertEquals("a", got);
    }

    @Test
    void testDecorateReverseWithUppercaasseLetters() {
        String got = transformer.transform("ApPlE");

        assertEquals("ElPpA", got);
    }

    @Test
    void testDecorateReverseComplexSentence() {
        String sentence = "This is a complex sentence with multiple words and punctuation!";
        String expected = "!noitautcnup dna sdrow elpitlum htiw ecnetnes xelpmoc a si sihT";

        String got = transformer.transform(sentence);

        assertEquals(expected, got);
    }
}