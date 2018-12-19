package com.traductor.web;


import com.traductor.aws.AmazonClient;
import com.traductor.domain.Document;
import com.traductor.domain.Language;
import com.traductor.domain.Translate;
import com.traductor.domain.User;
import com.traductor.domain.User;
import com.traductor.repository.DocumentRepository;
import com.traductor.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@RequestMapping("/document")
@Controller
public class DocumentController {

    @Autowired
    DocumentRepository repo;
    @Autowired
    UserRepository usRepo;

    @Autowired
    AmazonClient AWSClient;

    @PostMapping("/{id}")
    public String uploadFile(@PathVariable Long id,@RequestPart(value = "file") MultipartFile file,@ModelAttribute("translate") Translate translate) {
        Document document = new Document();

        document.setIdName(file.getOriginalFilename());
        document.setTitle(file.getOriginalFilename());
        document.setCreated(new Date());
        document.setIdUser(id);
        translate.setSourceLanguage(Language.Auto);
        document.setUrl(this.AWSClient.uploadFile(file,translate));
        repo.save(document);

        return document.getUrl();
    }

    @GetMapping("/{id}")
    public String listDocuments(@PathVariable Long id,Model model) {

        List<Document> documentList;
        User user = usRepo.findById(id).get();
        model.addAttribute("translate",new Translate());
        model.addAttribute("documents",repo.findAllByIdUser(id));
		model.addAttribute("user", user);
		documentList = obtainDocumentsOfUser(id);
		model.addAttribute("documents", documentList);

		return "documents";
    }

    private List<Document> obtainDocumentsOfUser(Long idUser) {
		List<Document> documentList = new ArrayList<>();
		List<Document> allDocuments;
		allDocuments = repo.findAll();

		for (Document document : allDocuments) {
			if (document.getIdUser() == idUser) {
				documentList.add(document);
			}
		}

		return documentList;
	}
}
