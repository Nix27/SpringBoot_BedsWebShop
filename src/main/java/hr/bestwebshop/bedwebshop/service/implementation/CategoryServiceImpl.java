package hr.bestwebshop.bedwebshop.service.implementation;

import hr.bestwebshop.bedwebshop.dto.CategoryDTO;
import hr.bestwebshop.bedwebshop.model.Category;
import hr.bestwebshop.bedwebshop.repository.CategoryRepository;
import hr.bestwebshop.bedwebshop.service.abstraction.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;

    @Override
    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(this::convertCategoryToCategoryDto)
                .toList();
    }

    @Override
    public Optional<CategoryDTO> getCategory(Integer id) {
        Optional<Category> category = categoryRepository.findById(id);
        return category.map(this::convertCategoryToCategoryDto);
    }

    @Override
    public CategoryDTO upsertCategory(CategoryDTO newCategory) {
        return convertCategoryToCategoryDto(categoryRepository.save(convertCategoryDtoToCategory(newCategory)));
    }

    @Override
    public void deleteCategory(CategoryDTO category) {
        categoryRepository.delete(convertCategoryDtoToCategory(category));
    }

    private Category convertCategoryDtoToCategory(CategoryDTO categoryDTO) {
        return new Category(
                categoryDTO.getId(),
                categoryDTO.getName()
        );
    }

    private CategoryDTO convertCategoryToCategoryDto(Category category) {
        return new CategoryDTO(
                category.getId(),
                category.getName()
        );
    }
}
