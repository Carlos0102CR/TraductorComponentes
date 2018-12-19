package com.traductor.web;


import com.traductor.aws.AmazonClient;
import com.traductor.domain.Document;
import com.traductor.domain.User;
import com.traductor.repository.DocumentRepository;
import com.traductor.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/document")
@Controller
public class DocumentController {

    @Autowired
    DocumentRepository repo;
    @Autowired
    UserRepository usRepo;

    @Autowired
    AmazonClient AWSClient;

   /* @PostMapping
    @ResponseBody
    public String uploadFile(@RequestPart(value = "file") MultipartFile file) {
        return this.AWSClient.uploadFile(file);
    }*/  

    @GetMapping("/{id}")
    public String listDocuments(@PathVariable Long id,Model model) {
    	
    	List<Document> documentList;
		User user = usRepo.findById(id).get();
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
