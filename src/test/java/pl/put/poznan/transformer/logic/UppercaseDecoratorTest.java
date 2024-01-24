package pl.put.poznan.transformer.logic;

import org.apache.juli.logging.Log;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.slf4j.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

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

    @Test
    void testDecorateUppercaseAllLowerLoggerMockito() {
        Logger logger = Mockito.mock(Logger.class);
        TextTransformer t = new UppercaseDecorator(new BasicTextTransformer(), logger);

        String got = t.transform("cheetah");

        Mockito.verify(logger, times(2)).debug(anyString());
    }
}