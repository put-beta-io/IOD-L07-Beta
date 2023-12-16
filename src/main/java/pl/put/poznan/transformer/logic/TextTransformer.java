package pl.put.poznan.transformer.logic;

/**
 * Interface for text transformers.
 * Each transformer should implement this interface.
 */
public interface TextTransformer {
    String transform(String text);
}
