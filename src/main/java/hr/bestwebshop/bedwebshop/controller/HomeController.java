package hr.bestwebshop.bedwebshop.controller;

import hr.bestwebshop.bedwebshop.dto.ProductDTO;
import hr.bestwebshop.bedwebshop.dto.ShoppingCartItemDTO;
import hr.bestwebshop.bedwebshop.model.ProductSearch;
import hr.bestwebshop.bedwebshop.model.User;
import hr.bestwebshop.bedwebshop.service.abstraction.CategoryService;
import hr.bestwebshop.bedwebshop.service.abstraction.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping("/bedswebshop")
@SessionAttributes({"productSearch", "products", "categories"})
public class HomeController {

    private ProductService productService;
    private CategoryService categoryService;

    @GetMapping("/home")
    public String showHomePage(Model model) {
        if(!model.containsAttribute("productSearch")) {
            model.addAttribute("productSearch", new ProductSearch());
        }

        if(!model.containsAttribute("products")) {
            model.addAttribute("products", productService.getAllProducts());
        }

        if(!model.containsAttribute("categories")) {
            model.addAttribute("categories", categoryService.getAllCategories());
        }

        return "home";
    }

    @PostMapping("/filterProducts")
    public String filterProducts(@ModelAttribute ProductSearch productSearch, Model model) {
        List<ProductDTO> products = productService.filterProducts(productSearch);
        model.addAttribute("products", products);
        return "redirect:home";
    }

    @GetMapping("/productDetails/{id}")
    public String getProductDetails(@PathVariable Integer id, Model model) {
        Optional<ProductDTO> product = productService.getProduct(id);
        if(product.isEmpty()) return "redirect:showHomePage";
        model.addAttribute("product", product.get());
        model.addAttribute("shoppingCartItem", new ShoppingCartItemDTO());
        return "product_details";
    }

    @GetMapping("/image/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable Integer id) {
        byte[] image = productService.getProduct(id).get().getImageBytes();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        return new ResponseEntity<>(image, headers, HttpStatus.OK);
    }

}
