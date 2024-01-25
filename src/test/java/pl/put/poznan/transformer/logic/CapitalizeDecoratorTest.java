package pl.put.poznan.transformer.logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;

class CapitalizeDecoratorTest {
    private TextTransformer transformer;

    @BeforeEach
    void setUp() {
        TextTransformerBuilder builder = new TextTransformerBuilder();
        builder.addTransformation("capitalize");
        transformer = builder.getTransformer();
    }

    @Test
    void testDecorateCapitalizeAllLower() {
        String got = transformer.transform("cheetah");

        assertEquals("Cheetah", got);
    }

    @Test
    void testDecorateCapitalizeWithSpaces() {
        String got = transformer.transform("a b c d e f g");

        assertEquals("A B C D E F G", got);
    }

    @Test
    void testDecorateCapitalizeEmptyString() {
        String got = transformer.transform("");

        assertEquals("", got);
    }

    @Test
    void testDecorateCapitalizeSingleWord() {
        String got = transformer.transform("apple");

        assertEquals("Apple", got);
    }

    @Test
    void testDecorateCapitalizeSingleLetter() {
        String got = transformer.transform("a");

        assertEquals("A", got);
    }

    @Test
    void testDecorateCapitalizeComplexSentence() {
        String sentence = "this is a complex sentence with multiple words and punctuation!";
        String expected = "This Is A Complex Sentence With Multiple Words And Punctuation!";

        String got = transformer.transform(sentence);

        assertEquals(expected, got);
    }

    @Test
    void testDecorateCapitalizeLoggerMockito() {
        Logger logger = Mockito.mock(Logger.class);
        TextTransformer t = new CapitalizeDecorator(new BasicTextTransformer(), logger);

        String got = t.transform("na przykład");

        Mockito.verify(logger, times(2)).debug(anyString());
    }
}
