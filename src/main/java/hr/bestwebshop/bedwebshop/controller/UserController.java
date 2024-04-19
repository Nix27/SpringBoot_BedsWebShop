package hr.bestwebshop.bedwebshop.controller;

import hr.bestwebshop.bedwebshop.model.RegistrationRequest;
import hr.bestwebshop.bedwebshop.model.User;
import hr.bestwebshop.bedwebshop.repository.UserRepository;
import hr.bestwebshop.bedwebshop.service.abstraction.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping("/bedswebshop/user")
public class UserController {

    private UserService userService;
    private UserRepository userRepository;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("registrationRequest", new RegistrationRequest());
        return "registration";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute RegistrationRequest registrationRequest, Model model) {
        try {
            userService.register(registrationRequest);
            return "/login";
        } catch (Exception e) {
            e.printStackTrace();
            return "registration";
        }
    }
}
