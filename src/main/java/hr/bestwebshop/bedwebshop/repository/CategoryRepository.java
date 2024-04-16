package hr.bestwebshop.bedwebshop.repository;

import java.util.List;

import hr.bestwebshop.bedwebshop.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
