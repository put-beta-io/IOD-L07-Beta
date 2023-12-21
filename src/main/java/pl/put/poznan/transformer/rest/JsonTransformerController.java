package pl.put.poznan.transformer.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import pl.put.poznan.transformer.logic.TextTransformer;
import pl.put.poznan.transformer.logic.TextTransformerBuilder;

import java.util.Arrays;
import java.util.Map;

@RestController
@RequestMapping("/api/json")
public class JsonTransformerController {
    private static final Logger logger = LoggerFactory.getLogger(TextTransformerController.class);

    public String doTransformation(String text, String[] transforms) {
        logger.debug(text);
        logger.debug(Arrays.toString(transforms));

        TextTransformerBuilder builder = new TextTransformerBuilder();
        for (var transformation : transforms) {
            builder.addTransformation(transformation);
        }
        TextTransformer transformer = builder.getTransformer();

        return transformer.transform(text);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public String post(@RequestBody Map<String, Object> requestBody) {
        String text = (String) requestBody.get("text");
        String transforms = (String) requestBody.get("transforms");

        return doTransformation(text, transforms.split(","));
    }
}
