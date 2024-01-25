package pl.put.poznan.transformer.logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.junit.jupiter.api.Assertions.*;

class AbbreviationDecoratorTest {
    private TextTransformer transformer;

    @BeforeEach
    void setUp() {
        TextTransformerBuilder builder = new TextTransformerBuilder();
        builder.addTransformation("abbreviate");
        transformer = builder.getTransformer();
    }

    @Test
    void testAbbreviationSingle() {
        String got = transformer.transform("na przykład");
        assertEquals("np.", got);
    }

    @Test
    void testAbbreviationMultiple() {
        String got = transformer.transform("ulica na przykład między innymi");
        String expect = "ul. np. m.in";
        assertEquals(expect, got);
    }

    @Test
    void testAbbreviationEmpty() {
        String got = transformer.transform("");
        assertEquals("", got);
    }

    @Test
    void testAbbreviationBigLetters() {
        String got = transformer.transform("Roku Na Przykład Ciąg Dalszy Nastąpi");
        String expect = "R. NP. CDN.";
        assertEquals(expect, got);
    }

    @Test
    void testDecorateAbbreviationLoggerMockito() {
        Logger logger = Mockito.mock(Logger.class);
        TextTransformer t = new AbbreviationDecorator(new BasicTextTransformer(), logger);

        String got = t.transform("na przykład");

        Mockito.verify(logger, times(2)).debug(anyString());
    }
}