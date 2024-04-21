package hr.bestwebshop.bedwebshop.controller;

import hr.bestwebshop.bedwebshop.dto.OrderDTO;
import hr.bestwebshop.bedwebshop.dto.ShoppingCartItemDTO;
import hr.bestwebshop.bedwebshop.model.User;
import hr.bestwebshop.bedwebshop.service.abstraction.OrderService;
import hr.bestwebshop.bedwebshop.service.abstraction.PaymentTypeService;
import hr.bestwebshop.bedwebshop.service.abstraction.ShoppingCartService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/bedswebshop/order")
public class OrderController {

    private OrderService orderService;
    private ShoppingCartService shoppingCartService;
    private PaymentTypeService paymentTypeService;

    @GetMapping("/orderSummary")
    public String showOrderScreen(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<ShoppingCartItemDTO> shoppingCartItems = shoppingCartService.getAllShoppingCartItemsForUser(user);
        model.addAttribute("user", user);
        model.addAttribute("shoppingCartItems", shoppingCartItems);
        model.addAttribute("paymentTypes", paymentTypeService.getAllPaymentTypes());
        model.addAttribute("totalPrice", shoppingCartService.getTotalPrice(shoppingCartItems));
        model.addAttribute("order", new OrderDTO());
        return "order_summary";
    }

    @PostMapping("/createOrder")
    public String createOrder(@ModelAttribute OrderDTO orderDTO) {
        return "redirect:/bedswebshop/home";
    }

}
