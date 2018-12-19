package com.traductor.web;

import com.traductor.domain.User;
import com.traductor.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RequestMapping("/user")
@Controller
public class UserController {


    @Autowired
    UserRepository repo;

    @RequestMapping(value = "/user")
	public String saveUser(Model model) {
		model.addAttribute("user", new User());
		return "user";
	}

	@RequestMapping(value = "user", method = RequestMethod.POST)
	public String registerUser(@ModelAttribute("user") User user) {
		repo.save(user);
		return "user";
	}
	
	@RequestMapping(value = "/login")
	public String saveUserLogin(Model model) {
		model.addAttribute("login", new User());
		return "login";
	}
    
    @RequestMapping(value = "/login", method = RequestMethod.POST)
	public String userLogin(Model model, @ModelAttribute("login") User user) {

		User us = repo.findByUsernameAndPassword(user.getUsername(), user.getPassword());

		if (us != null) {

			return "documentUpload";
		}

		return "login";

	}

    @PostMapping
    public String userSubmit(@ModelAttribute User user) {
        repo.save(user);

        return "index";
    }

    @PutMapping
    public String userUpdate(@ModelAttribute User user) {
        repo.save(user);

        return "index";
    }

    @DeleteMapping("/{id}")
    public String userDelete(@PathVariable Long id,Model model) {

        repo.deleteById(id);

        return "index";
    }
}
