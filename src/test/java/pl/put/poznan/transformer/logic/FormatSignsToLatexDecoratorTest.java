package pl.put.poznan.transformer.logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FormatSignsToLatexDecoratorTest {
    private TextTransformer transformer;
    @BeforeEach
    void setUp() {
        TextTransformerBuilder builder = new TextTransformerBuilder();
        builder.addTransformation("latex");
        transformer = builder.getTransformer();
    }

    @Test
    void testLatexFormatSingle(){
        String got = transformer.transform("John Smith & Sons");
        String expected = transformer.transform("John Smith \\& Sons");
        assertEquals(expected,got);
    }

    @Test
    void testLatexFormatMultiple(){
        String got = transformer.transform("John Smith & Sons 5 % i 3 $");
        String expected = transformer.transform("John Smith \\& Sons 5 \\% i 3 \\$");
        assertEquals(expected,got);
    }
    @Test
    void testLatexFormatEmpty(){
        String got = transformer.transform("");
        assertEquals("",got);
    }
}