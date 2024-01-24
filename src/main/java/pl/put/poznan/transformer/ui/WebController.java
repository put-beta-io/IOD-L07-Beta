package pl.put.poznan.transformer.ui;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.put.poznan.transformer.logic.TextTransformer;
import pl.put.poznan.transformer.logic.TextTransformerBuilder;

import java.util.Arrays;


@Controller
@RequestMapping("/")
public class WebController {
    public String doTransformation(String text, String[] transforms) {
        TextTransformerBuilder builder = new TextTransformerBuilder();
        for (var transformation : transforms) {
            builder.addTransformation(transformation);
        }
        TextTransformer transformer = builder.getTransformer();

        return transformer.transform(text);
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getIndex() {
        return "index";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String postIndex(@RequestParam String text,
                           @RequestParam String[] transforms, Model model) {
        String txt = doTransformation(text, transforms);
        model.addAttribute("txt", txt);
        return "index";
    }
}
