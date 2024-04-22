package hr.bestwebshop.bedwebshop.controller;

import hr.bestwebshop.bedwebshop.dto.ShoppingCartItemDTO;
import hr.bestwebshop.bedwebshop.model.User;
import hr.bestwebshop.bedwebshop.service.abstraction.ShoppingCartService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
@AllArgsConstructor
@RequestMapping("/bedswebshop/shoppingcart")
public class ShoppingCartController {

    private ShoppingCartService shoppingCartService;

    @PostMapping("/addToShoppingCart")
    public String addToShoppingCart(@CookieValue(value = "uuid", required = false) String uuid, @ModelAttribute ShoppingCartItemDTO shoppingCartItemDTO, HttpServletResponse response) {
        if(shoppingCartItemDTO.getUserId() == null) {
            if(uuid == null) {
                uuid = UUID.randomUUID().toString();
            }

            shoppingCartItemDTO.setUuid(uuid);
            shoppingCartItemDTO.setUuidExpiryTime(LocalDateTime.now().plusDays(2));

            Cookie uuidCookie = new Cookie("uuid", uuid);
            uuidCookie.setMaxAge((int) Duration.ofDays(2).getSeconds());
            uuidCookie.setPath("/");

            response.addCookie(uuidCookie);
        }


        shoppingCartService.addShoppingCartItem(shoppingCartItemDTO);
        return "redirect:/bedswebshop/home";
    }

    @GetMapping("/getAllShoppingCartItems")
    public String getAllShoppingCartItems(@CookieValue(value = "uuid", required = false) String uuid, Model model) {
        List<ShoppingCartItemDTO> shoppingCartItems;
        if(!SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser")) {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            shoppingCartItems = shoppingCartService.getAllShoppingCartItemsForUser(user, uuid);
        } else {
            shoppingCartItems = shoppingCartService.getAllShoppingCartItemsWithoutUser(uuid);
        }

        model.addAttribute("shoppingCartItems", shoppingCartItems);
        model.addAttribute("totalprice", shoppingCartService.getTotalPrice(shoppingCartItems));

        return "shoppingcart";
    }

    @GetMapping("/incrementQuantityOfItem/{id}")
    public String incrementQuantityOfShoppingCartItem(@PathVariable Integer id) {
        shoppingCartService.incrementQuantityOfShoppingCartItem(id);
        return "redirect:/bedswebshop/shoppingcart/getAllShoppingCartItems";
    }

    @GetMapping("/decrementQuantityOfItem/{id}")
    public String decrementQuantityOfShoppingCartItem(@PathVariable Integer id) {
        shoppingCartService.decrementQuantityOfShoppingCartItem(id);
        return "redirect:/bedswebshop/shoppingcart/getAllShoppingCartItems";
    }

    @GetMapping("/deleteShoppingCartItem/{id}")
    public String deleteShoppingCartItem(@PathVariable Integer id){
        shoppingCartService.deleteShoppingCartItem(id);
        return "redirect:/bedswebshop/shoppingcart/getAllShoppingCartItems";
    }

}
