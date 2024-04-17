package hr.bestwebshop.bedwebshop.repository;

import hr.bestwebshop.bedwebshop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
