package pl.put.poznan.transformer.logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;

class FormatSignsToLatexDecoratorTest {
    private TextTransformer transformer;

    @BeforeEach
    void setUp() {
        TextTransformerBuilder builder = new TextTransformerBuilder();
        builder.addTransformation("latex");
        transformer = builder.getTransformer();
    }

    @Test
    void testLatexFormatSingle() {
        String got = transformer.transform("John Smith & Sons");
        String expected = transformer.transform("John Smith \\& Sons");
        assertEquals(expected, got);
    }

    @Test
    void testLatexFormatMultiple() {
        String got = transformer.transform("John Smith & Sons 5 % i 3 $");
        String expected = transformer.transform("John Smith \\& Sons 5 \\% i 3 \\$");
        assertEquals(expected, got);
    }

    @Test
    void testLatexFormatEmpty() {
        String got = transformer.transform("");
        assertEquals("", got);
    }

    @Test
    void testDecorateLatexFormatLoggerMockito() {
        Logger logger = Mockito.mock(Logger.class);
        TextTransformer t = new FormatSignsToLatexDecorator(new BasicTextTransformer(), logger);

        String got = t.transform("John & Sam");

        Mockito.verify(logger, times(2)).debug(anyString());
    }
}