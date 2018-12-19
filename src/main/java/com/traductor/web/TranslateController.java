package com.traductor.web;

import com.traductor.aws.AmazonClient;
import com.traductor.domain.Translate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class TranslateController {

    @Autowired
    AmazonClient AWSClient;

    @GetMapping
    public String formTranslation(Model model) {
        Translate translate = new Translate();

        model.addAttribute("translate",translate);

        return "index";
    }

    @PostMapping("/translate")
    @ResponseBody
    public String submitTranslation(@RequestBody Translate translate,Model model) {
        model.addAttribute("translate",AWSClient.translate(translate));
        return "index";
    }
}