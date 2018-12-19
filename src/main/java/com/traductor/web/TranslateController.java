package com.traductor.web;

import com.traductor.aws.AmazonClient;
import com.traductor.domain.Document;
import com.traductor.domain.Translate;
import com.traductor.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class TranslateController {

    @Autowired
    DocumentRepository repo;

    @Autowired
    AmazonClient AWSClient;

    @GetMapping
    public String formTranslation(Model model) {
        Translate translate = new Translate();

        model.addAttribute("translate",translate);

        return "index";
    }

    @PostMapping
    @ResponseBody
    public String submitTranslation(@RequestBody Translate translate) {

        AWSClient.translate(translate);
        return "";
    }
}
