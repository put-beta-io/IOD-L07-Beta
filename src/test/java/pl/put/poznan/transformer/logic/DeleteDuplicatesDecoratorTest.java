package pl.put.poznan.transformer.logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeleteDuplicatesDecoratorTest {
    private TextTransformer transformer;

    @BeforeEach
    void setUp() {
        TextTransformerBuilder builder = new TextTransformerBuilder();
        builder.addTransformation("duplicates");
        transformer = builder.getTransformer();
    }

    @Test
    void testDecorateDeleteDuplicatesEmptyString() {
        String got = transformer.transform("");

        assertEquals("", got);
    }

    @Test
    void testDecorateDeleteDuplicatesSingleWord() {
        String got = transformer.transform("apple");

        assertEquals("apple", got);
    }

    @Test
    void testDecorateDeleteDuplicatesOneDuplicate() {
        String got = transformer.transform("apple apple");

        assertEquals("apple", got);
    }

    @Test
    void testDDecorateDeleteDuplicatesMultipleDuplicates() {
        String got = transformer.transform("apple apple apple apple");

        assertEquals("apple", got);
    }

    @Test
    void testDecorateDeleteDuplicatesComplexSentence() {
        String sentence = "This is a complex sentence sentence with with with multiple words and and punctuation!";
        String expected = "This is a complex sentence with multiple words and punctuation!";

        String got = transformer.transform(sentence);

        assertEquals(expected, got);
    }
}