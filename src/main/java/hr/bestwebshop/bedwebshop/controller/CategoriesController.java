package hr.bestwebshop.bedwebshop.controller;

import hr.bestwebshop.bedwebshop.dto.CategoryDTO;
import hr.bestwebshop.bedwebshop.service.abstraction.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping("/bedswebshop/categories")
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

    @GetMapping("/updateCategory/{id}")
    public String showScreenForUpdateCategory(@PathVariable Integer id, Model model) {
        Optional<CategoryDTO> category = categoryService.getCategory(id);
        if(category.isEmpty()) return "redirect:getAllCategories";
        model.addAttribute("category", category.get());
        return "update_category";
    }

    @PostMapping("/updateCategory")
    public String updateCategory(@ModelAttribute CategoryDTO categoryDTO, Model model) {
        model.addAttribute("category", categoryDTO);
        categoryService.upsertCategory(categoryDTO);
        return "redirect:getAllCategories";
    }

    @GetMapping("/deleteCategory/{id}")
    public String showScreenForDeleteCategory(@PathVariable Integer id, Model model) {
        Optional<CategoryDTO> category = categoryService.getCategory(id);
        if(category.isEmpty()) return "redirect:getAllCategories";
        model.addAttribute("category", category.get());
        return "delete_category";
    }

    @PostMapping("/deleteCategory")
    public String deleteCategory(@ModelAttribute CategoryDTO categoryDTO, Model model) {
        model.addAttribute("category", categoryDTO);
        categoryService.deleteCategory(categoryDTO);
        return "redirect:getAllCategories";
    }
}
