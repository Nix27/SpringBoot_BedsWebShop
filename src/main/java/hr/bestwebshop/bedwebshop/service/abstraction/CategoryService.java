package hr.bestwebshop.bedwebshop.service.abstraction;

import hr.bestwebshop.bedwebshop.dto.CategoryDTO;
import java.util.List;
import java.util.Optional;

public interface CategoryService {

    List<CategoryDTO> getAllCategories();
    Optional<CategoryDTO> getCategory(Integer id);
    CategoryDTO upsertCategory(CategoryDTO newCategory);
    void deleteCategory(CategoryDTO category);

}
