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

@RequestMapping("/document")
@Controller
public class DocumentController {

    @Autowired
    DocumentRepository repo;

    @Autowired
    AmazonClient AWSClient;

   /* @PostMapping
    @ResponseBody
    public String uploadFile(@RequestPart(value = "file") MultipartFile file) {
        return this.AWSClient.uploadFile(file);
    }*/

    @GetMapping("/{id}")
    public String listDocuments(@PathVariable Long id,Model model) {

        model.addAttribute("documents",repo.findAllByIdUser(id));

        return "documents";
    }
}
