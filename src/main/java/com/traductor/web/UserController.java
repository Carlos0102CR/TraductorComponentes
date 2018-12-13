package com.traductor.web;

import com.traductor.domain.User;
import com.traductor.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RequestMapping("/users")
@Controller
public class UserController {


    @Autowired
    UserRepository repo;

    @GetMapping
    public String userForm(Model model) {
        User user = new User();

        model.addAttribute("user",user);

        return "userRegistration";
    }


    @GetMapping("/{id}")
    public String userPerfil(@PathVariable Long id,Model model) {
        Optional<User> user = repo.findById(id);

        model.addAttribute("user",user);

        return "userPerfil";
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
