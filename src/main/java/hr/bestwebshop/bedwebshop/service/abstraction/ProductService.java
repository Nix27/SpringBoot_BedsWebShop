package hr.bestwebshop.bedwebshop.service.abstraction;

import hr.bestwebshop.bedwebshop.dto.ProductDTO;
import hr.bestwebshop.bedwebshop.model.ProductSearch;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<ProductDTO> getAllProducts();
    Optional<ProductDTO> getProduct(Integer id);
    ProductDTO upsertProduct(ProductDTO productDTO);
    void deleteProduct(ProductDTO productDTO);
    List<ProductDTO> filterProducts(ProductSearch productSearch);

}
