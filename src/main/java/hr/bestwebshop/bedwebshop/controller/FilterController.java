package hr.bestwebshop.bedwebshop.controller;

import hr.bestwebshop.bedwebshop.dto.CategoryDTO;
import hr.bestwebshop.bedwebshop.dto.ProductDTO;
import hr.bestwebshop.bedwebshop.service.abstraction.CategoryService;
import hr.bestwebshop.bedwebshop.service.abstraction.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/bedswebshop/ajaxCategories")
public class FilterController {

    private CategoryService categoryService;

    @GetMapping("/getAllCategories")
    public List<CategoryDTO> filterCategories() {
        return categoryService.getAllCategories();
    }

}
