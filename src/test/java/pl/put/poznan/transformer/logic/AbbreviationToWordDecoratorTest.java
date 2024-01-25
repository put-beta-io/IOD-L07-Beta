package pl.put.poznan.transformer.logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;

class AbbreviationToWordDecoratorTest {
    private TextTransformer transformer;

    @BeforeEach
    void setUp() {
        TextTransformerBuilder builder = new TextTransformerBuilder();
        builder.addTransformation("fullword");
        transformer = builder.getTransformer();
    }

    @Test
    void testAbbreviationToWordSingle() {
        String got = transformer.transform("np.");
        assertEquals("na przykład", got);
    }

    @Test
    void testAbbreviationToWordMultiple() {
        String got = transformer.transform("ul. np. m.in");
        String expect = "ulica na przykład między innymi";
        assertEquals(expect, got);
    }

    @Test
    void testAbbreviationToWordEmpty() {
        String got = transformer.transform("");
        assertEquals("", got);
    }

    @Test
    void testAbbreviationBigLetters() {
        String got = transformer.transform("R. Np. Cdn.");
        String expect = "Roku Na przykład Ciąg dalszy nastąpi";
        assertEquals(expect, got);
    }

    @Test
    void testDecorateAbbreviationToWordLoggerMockito() {
        Logger logger = Mockito.mock(Logger.class);
        TextTransformer t = new AbbreviationToWordDecorator(new BasicTextTransformer(), logger);

        String got = t.transform("np.");

        Mockito.verify(logger, times(2)).debug(anyString());
    }
}