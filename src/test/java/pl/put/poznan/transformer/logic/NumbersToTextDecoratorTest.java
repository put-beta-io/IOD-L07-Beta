package pl.put.poznan.transformer.logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NumbersToTextDecoratorTest {
    private TextTransformer transformer;

    @BeforeEach
    void setUp() {
        TextTransformerBuilder builder = new TextTransformerBuilder();
        builder.addTransformation("numbers");
        transformer = builder.getTransformer();
    }

    @Test
    void testNumbersToTextSingle() {
        String got = transformer.transform("9 6 3");
        String expect = "dziewięć sześć trzy";
        assertEquals(expect, got);
    }

    @Test
    void testNumbersToTextDouble() {
        String got = transformer.transform("94 61 49");
        String expect = "dziewięćdziesiąt cztery sześćdziesiąt jeden czterdzieści dziewięć";
        assertEquals(expect, got);
    }

    @Test
    void testNumbersToTextTriple() {
        String got = transformer.transform("898 763 289");
        String expect = "osiemset dziewięćdziesiąt osiem siedemset sześćdziesiąt trzy dwieście osiemdziesiąt dziewięć";
        assertEquals(expect, got);
    }

    @Test
    void testNumbersToTextQuadra() {
        String got = transformer.transform("4704");
        String expect = "cztery tysiące siedemset cztery";
        assertEquals(expect, got);
    }

    @Test
    void testNumbersToTextEmpty(){
        String got = transformer.transform("");
        assertEquals("",got);
    }
}