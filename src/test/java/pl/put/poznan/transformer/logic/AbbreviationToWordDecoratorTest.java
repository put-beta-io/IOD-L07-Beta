package pl.put.poznan.transformer.logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
    void testAbbreviationToWordMultiple(){
        String got = transformer.transform("ul. np. m.in");
        String expect = "ulica na przykład między innymi";
        assertEquals(expect,got);
    }

    @Test
    void testAbbreviationToWordEmpty(){
        String got = transformer.transform("");
        assertEquals("",got);
    }

    @Test
    void testAbbreviationBigLetters(){
        String got = transformer.transform("R. Np. Cdn.");
        String expect = "Roku Na przykład Ciąg dalszy nastąpi";
        assertEquals(expect,got);
    }
}