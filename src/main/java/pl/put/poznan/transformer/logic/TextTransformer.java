package pl.put.poznan.transformer.logic;

import javax.sql.rowset.serial.SerialStruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This is just an example to show that the logic should be outside the REST service.
 */
public class TextTransformer {

    private final String[] transforms;
    private static final Map<String, Function<String, String>> transformMap = Map.ofEntries(
            Map.entry("toUpper", TextTransformer::toUpper),
            Map.entry("toLower", TextTransformer::toLower),
            Map.entry("capitalize", TextTransformer::capitalize)
    );

    public TextTransformer(String[] transforms){
        this.transforms = transforms;
    }

    public String transform(String text, List<String> transforms){
        var transformer = TextTransformer.transformBuilder(transforms);
        return transformer.apply(text);
    }

    static private Function<String, String> transformBuilder(List<String> transforms){
        var functions = transforms.stream().map(TextTransformer.transformMap::get).collect(Collectors.toList());
        return s -> {
            for (var function : functions){
                s = function.apply(s);
            }
            return s;
        };
    }

    static private String toUpper(String text){
        return text.toUpperCase();
    }

    static private String toLower(String text){
        return text.toLowerCase();
    }

    static private String capitalize(String text){
        var split = List.of(text.split(" "));
        var words = new ArrayList<String>();
        for (String word : split){
            String capitalize = word.substring(0, 1).toUpperCase() + word.substring(1);
            words.add(capitalize);
        }
        return String.join(" ", words);
    }
    
    static public String reverse(String text){
        var indices = new ArrayList<Integer>();
        for (int i=0; i<text.length(); i++){
            if(Character.isUpperCase(text.charAt(i))){
                indices.add(i);
            }
        }
        var reversed = new StringBuilder(text).reverse();
        for (int i=0; i<text.length(); i++){
            if (indices.contains(i)){
                var c = Character.toUpperCase(reversed.charAt(i));
                reversed.setCharAt(i, c);
            }
        }
        return reversed.toString();
    }

    static public String deleteDuplicates(String text){
        String[] words = text.split(" ");
        var result = new StringBuilder();

        int i = 0;
        while (i < words.length) {
            result.append(words[i]).append(" ");
            while (i < words.length - 1 && words[i].equals(words[i + 1])) {
                i++;
            }
            i++;
        }
        return result.toString();
    }
}
