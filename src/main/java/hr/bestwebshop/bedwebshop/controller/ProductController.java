package hr.bestwebshop.bedwebshop.controller;

import hr.bestwebshop.bedwebshop.dto.ProductDTO;
import hr.bestwebshop.bedwebshop.service.abstraction.CategoryService;
import hr.bestwebshop.bedwebshop.service.abstraction.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping("/bedswebshop/products")
public class ProductController {

    private ProductService productService;
    private CategoryService categoryService;

    @GetMapping("/getAllProducts")
    public String getAllProducts(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "all_products";
    }

    @GetMapping("/addNewProduct")
    public String showScreenForNewProduct(Model model) {
        model.addAttribute("newProduct", new ProductDTO());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "add_new_product";
    }

    @PostMapping("/addNewProduct")
    public String addNewProduct(@ModelAttribute ProductDTO productDTO, @RequestParam("productImage") MultipartFile productImage, Model model) {

        try {
            productDTO.setImageBytes(productImage.getBytes());
            model.addAttribute("newProduct", productDTO);
            productService.upsertProduct(productDTO);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:getAllProducts";
    }

    @GetMapping("/updateProduct/{id}")
    public String showScreenForUpdateProduct(@PathVariable Integer id, Model model) {
        Optional<ProductDTO> product = productService.getProduct(id);
        if(product.isEmpty()) return "redirect:getAllProducts";
        model.addAttribute("product", product.get());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "update_product";
    }

    @PostMapping("/updateProduct")
    public String updateProduct(@ModelAttribute ProductDTO productDTO, @RequestParam("productImage") MultipartFile productImage, Model model) {
        try {
            productDTO.setImageBytes(productImage.getBytes());
            model.addAttribute("product", productDTO);
            productService.upsertProduct(productDTO);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:getAllProducts";
    }

    @GetMapping("/deleteProduct/{id}")
    public String showScreenForDeleteProduct(@PathVariable Integer id, Model model) {
        Optional<ProductDTO> product = productService.getProduct(id);
        if(product.isEmpty()) return "redirect:getAllProducts";
        model.addAttribute("product", product.get());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "delete_product";
    }

    @PostMapping("/deleteProduct")
    public String deleteProduct(@ModelAttribute ProductDTO productDTO, Model model) {
        model.addAttribute("product", productDTO);
        productService.deleteProduct(productDTO);
        return "redirect:getAllProducts";
    }

}
