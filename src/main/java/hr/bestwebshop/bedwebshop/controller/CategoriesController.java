package hr.bestwebshop.bedwebshop.controller;

import hr.bestwebshop.bedwebshop.dto.CategoryDTO;
import hr.bestwebshop.bedwebshop.service.abstraction.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/bedswebshop")
public class CategoriesController {

    private CategoryService categoryService;

    @GetMapping("/getAllCategories")
    public String getAllCategories(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        return "all_categories";
    }

    @GetMapping("/addNewCategory")
    public String showScreenForNewCategory(Model model) {
        model.addAttribute("newCategory", new CategoryDTO());
        return "add_new_category";
    }

    @PostMapping("/addNewCategory")
    public String addNewCategory(@ModelAttribute CategoryDTO categoryDTO, Model model) {
        model.addAttribute("newCategory", categoryDTO);
        categoryService.upsertCategory(categoryDTO);
        return "redirect:getAllCategories";
    }

}
