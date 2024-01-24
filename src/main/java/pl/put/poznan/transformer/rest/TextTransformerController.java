package pl.put.poznan.transformer.rest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import pl.put.poznan.transformer.logic.*;

import java.util.Arrays;


@RestController
@RequestMapping("/base/{text}")
public class TextTransformerController {

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

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public String get(@PathVariable String text,
                              @RequestParam(value="transforms", defaultValue="upper,escape") String[] transforms) {
        return doTransformation(text, transforms);
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public String post(@PathVariable String text,
                      @RequestBody String[] transforms) {

        return doTransformation(text, transforms);
    }



}


