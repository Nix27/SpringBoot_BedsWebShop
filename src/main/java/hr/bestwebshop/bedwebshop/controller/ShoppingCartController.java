package hr.bestwebshop.bedwebshop.controller;

import hr.bestwebshop.bedwebshop.dto.ShoppingCartItemDTO;
import hr.bestwebshop.bedwebshop.model.User;
import hr.bestwebshop.bedwebshop.service.abstraction.ShoppingCartService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/bedswebshop/shoppingcart")
public class ShoppingCartController {

    private ShoppingCartService shoppingCartService;

    @PostMapping("/addToShoppingCart")
    public String addToShoppingCart(@ModelAttribute ShoppingCartItemDTO shoppingCartItemDTO) {
        shoppingCartService.addShoppingCartItem(shoppingCartItemDTO);
        return "redirect:/bedswebshop/home";
    }

    @GetMapping("/getAllShoppingCartItems")
    public String getAllShoppingCartItems(Model model) {
        List<ShoppingCartItemDTO> shoppingCartItems;
        if(!SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser")) {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            shoppingCartItems = shoppingCartService.getAllShoppingCartItemsForUser(user);
        } else {
            shoppingCartItems = shoppingCartService.getAllShoppingCartItemsForUser(null);
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
