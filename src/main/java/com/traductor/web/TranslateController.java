package com.traductor.web;

import com.traductor.aws.AmazonClient;
import com.traductor.domain.Document;
import com.traductor.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequestMapping("/documents")
@Controller
public class TranslateController {

    @Autowired
    DocumentRepository repo;

    @Autowired
    AmazonClient AWSClient;

    @PostMapping
    @ResponseBody
    public String uploadFile(@RequestPart(value = "file") MultipartFile file) {
        return this.AWSClient.uploadFile(file);
    }

    @GetMapping("/{id}")
    public String uploadDocument(@PathVariable Long id, @RequestParam("file") MultipartFile file,
                                 RedirectAttributes redirectAttributes, Model model) {
        Document document = new Document();

        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded !");

        return "documentSuccess";
    }
}
