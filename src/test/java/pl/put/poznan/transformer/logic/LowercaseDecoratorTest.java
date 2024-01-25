package pl.put.poznan.transformer.logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;

class LowercaseDecoratorTest {
    private TextTransformer transformer;

    @BeforeEach
    void setUp() {
        TextTransformerBuilder builder = new TextTransformerBuilder();
        builder.addTransformation("lower");
        transformer = builder.getTransformer();
    }

    @Test
    void testDecorateLowercaseAllUpper() {
        String got = transformer.transform("CHEETAH");

        assertEquals("cheetah", got);
    }

    @Test
    void testDecorateLowercaseMixed() {
        String got = transformer.transform("AbCdEf");

        assertEquals("abcdef", got);
    }

    @Test
    void testDecorateLowercaseWithSpaces() {
        String got = transformer.transform("A B C D E F G");

        assertEquals("a b c d e f g", got);
    }

    @Test
    void testDecorateLowercaseEmptyString() {
        String got = transformer.transform("");

        assertEquals("", got);
    }

    @Test
    void testDecorateLowercaseLoggerMockito() {
        Logger logger = Mockito.mock(Logger.class);
        TextTransformer t = new LowercaseDecorator(new BasicTextTransformer(), logger);

        String got = t.transform("NA PRZYKLAD");

        Mockito.verify(logger, times(2)).debug(anyString());
    }
}