package pl.put.poznan.transformer.logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AbbreviationDecoratorTest {
    private TextTransformer transformer;
    @BeforeEach
    void setUp() {
        TextTransformerBuilder builder = new TextTransformerBuilder();
        builder.addTransformation("abbreviation");
        transformer = builder.getTransformer();
    }

    @Test
    void testAbbreviationSingle() {
        String got = transformer.transform("na przykład");
        assertEquals("np.",got);
    }

    @Test
    void testAbbreviationMultiple(){
        String got = transformer.transform("ulica na przykład między innymi");
        String expect = "ul. np. m.in";
        assertEquals(expect,got);
    }

    @Test
    void testAbbreviationEmpty(){
        String got = transformer.transform(" ");
        assertEquals(" ",got);
    }

    @Test
    void testAbbreviationBigLetters(){
        String got = transformer.transform("Roku Na Przykład Ciąg Dalszy Nastąpi");
        String expect = "R. NP. CDN.";
        assertEquals(expect,got);
    }
}