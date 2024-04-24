package hr.bestwebshop.bedwebshop.controller;

import hr.bestwebshop.bedwebshop.service.abstraction.UserLoginService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/bedswebshop/userlogins")
public class UserLoginController {

    private UserLoginService userLoginService;

    @GetMapping("/allUserLogins")
    public String getAllUserLogins(Model model) {
        model.addAttribute("userLogins", userLoginService.getAllUserLogins());
        return "user_logins";
    }

}
