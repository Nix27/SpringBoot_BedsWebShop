package hr.bestwebshop.bedwebshop.controller;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import hr.bestwebshop.bedwebshop.dto.OrderDTO;
import hr.bestwebshop.bedwebshop.dto.ShoppingCartItemDTO;
import hr.bestwebshop.bedwebshop.enums.PaymentTypeEnum;
import hr.bestwebshop.bedwebshop.model.User;
import hr.bestwebshop.bedwebshop.service.abstraction.OrderService;
import hr.bestwebshop.bedwebshop.service.abstraction.PayPalService;
import hr.bestwebshop.bedwebshop.service.abstraction.PaymentTypeService;
import hr.bestwebshop.bedwebshop.service.abstraction.ShoppingCartService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.util.List;

@Controller
@AllArgsConstructor
@Slf4j
@RequestMapping("/bedswebshop/order")
@SessionAttributes({"order", "shoppingCartItems"})
public class OrderController {

    private OrderService orderService;
    private ShoppingCartService shoppingCartService;
    private PaymentTypeService paymentTypeService;
    private PayPalService payPalService;

    @GetMapping("/orderSummary")
    public String showOrderScreen(@CookieValue(name = "uuid", required = false) String uuid, Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<ShoppingCartItemDTO> shoppingCartItems = shoppingCartService.getAllShoppingCartItemsForUser(user, uuid);
        model.addAttribute("user", user);
        model.addAttribute("shoppingCartItems", shoppingCartItems);
        model.addAttribute("paymentTypes", paymentTypeService.getAllPaymentTypes());
        model.addAttribute("totalPrice", shoppingCartService.getTotalPrice(shoppingCartItems));
        model.addAttribute("order", new OrderDTO());
        return "order_summary";
    }

    @PostMapping("/createOrder")
    public String createOrder(@ModelAttribute OrderDTO orderDTO, Model model, SessionStatus sessionStatus, HttpServletRequest httpServletRequest) {
        if(paymentTypeService.getPaymentTypeById(orderDTO.getPaymentTypeId()).getName().equals(PaymentTypeEnum.PayPal.getName())) {
            try {
                Payment payment = payPalService.createPayment(
                        orderDTO.getTotalPrice(),
                        "EUR",
                        "paypal",
                        "sale",
                        "Beds webshop payment with paypal",
                        httpServletRequest.getScheme() + "://" + httpServletRequest.getServerName() + ":" + httpServletRequest.getServerPort() + "/bedswebshop/order/canceled",
                        httpServletRequest.getScheme() + "://" + httpServletRequest.getServerName() + ":" + httpServletRequest.getServerPort() + "/bedswebshop/order/success"
                );

                model.addAttribute("order", orderDTO);

                for(Links links : payment.getLinks()) {
                    if(links.getRel().equals("approval_url")) {
                        return "redirect:" + links.getHref();
                    }
                }
            } catch (PayPalRESTException e) {
                log.error("Error: ", e);
            }

            return "redirect:" + "/bedswebshop/order/fail";
        }
        List<ShoppingCartItemDTO> shoppingCartItems = (List<ShoppingCartItemDTO>)model.getAttribute("shoppingCartItems");
        orderService.createOrder(orderDTO, shoppingCartItems);
        sessionStatus.setComplete();
        return "redirect:" + "/bedswebshop/order/ordercreated";
    }

    @GetMapping("/success")
    public String paymentSuccess(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId, Model model, SessionStatus sessionStatus) {
        try {
            Payment payment = payPalService.executePayment(paymentId, payerId);
            if(payment.getState().equals("approved")){
                orderService.createOrder((OrderDTO) model.getAttribute("order"), (List<ShoppingCartItemDTO>) model.getAttribute("shoppingCartItems"));
                sessionStatus.setComplete();
                return "order_created";
            }
        } catch (PayPalRESTException e) {
            log.error("Error: ", e);
            return "order_failed";
        }

        return "order_created";
    }

    @GetMapping("/ordercreated")
    public String orderCreated() {
        return "order_created";
    }

    @GetMapping("/fail")
    public String orderFailed() {
        return "order_failed";
    }

    @GetMapping("/canceled")
    public String orderCanceled() {
        return "order_canceled";
    }

}
