package com.traductor.web;

import com.traductor.domain.User;
import com.traductor.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("/user")
@Controller
public class UserController {


    @Autowired
    UserRepository repo;

    @GetMapping
	public String saveUser(Model model) {
		model.addAttribute("user", new User());
		return "register";
	}

	@PostMapping
	public String registerUser(@ModelAttribute("user") User user) {
		repo.save(user);
		return "redirect:/";
	}

    @GetMapping("/listar")
    @ResponseBody
    public List<User> listar() {

        return repo.findAll();
    }
	
	@GetMapping("/login")
	public String saveUserLogin(Model model) {
		model.addAttribute("login", new User());

		return "login";
	}
    
    @PostMapping("/login")
	public String userLogin(@ModelAttribute("login") User user) {

		User us = repo.findByUsernameAndPassword(user.getUsername(), user.getPassword());

		if (us != null) {

			return "documents/"+us.getIdUser();
		}

		return "login";

	}
}
