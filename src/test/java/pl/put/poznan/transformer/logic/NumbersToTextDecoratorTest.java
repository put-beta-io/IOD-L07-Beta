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
    void testNumbersToTextEmpty() {
        String got = transformer.transform("");
        assertEquals("", got);
    }

    @Test
    void testNumbersToTextDecimal() {
        String got = transformer.transform("1,23 9,83");
        String expect = "jeden i dwadzieścia trzy setne dziewięć i osiemdziesiąt trzy setne";
        assertEquals(expect, got);
    }

    @Test
    void testNumberToTextZeros() {
        String got = transformer.transform("0 00 000 01 0,15");
        String expect = "zero zero zero jeden zero i piętnaście setnych";
        assertEquals(expect, got);
    }

    @Test
    void testNumberToTextSentence() {
        String got = transformer.transform("Pies burek waga 21,37 jest teraz, pusty 0 i niepełny 2137");
        String expect = "Pies burek waga dwadzieścia jeden i trzydzieści siedem setnych jest teraz, pusty zero i niepełny dwa tysiące sto trzydzieści siedem";
        assertEquals(expect, got);
    }
}