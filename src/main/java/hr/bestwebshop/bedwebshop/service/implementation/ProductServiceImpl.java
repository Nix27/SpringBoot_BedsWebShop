package hr.bestwebshop.bedwebshop.service.implementation;

import hr.bestwebshop.bedwebshop.dto.ProductDTO;
import hr.bestwebshop.bedwebshop.model.Product;
import hr.bestwebshop.bedwebshop.repository.CategoryRepository;
import hr.bestwebshop.bedwebshop.repository.ProductRepository;
import hr.bestwebshop.bedwebshop.service.abstraction.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;

    @Override
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(this::convertProductToProductDto)
                .toList();
    }

    @Override
    public Optional<ProductDTO> getProduct(Integer id) {
        Optional<Product> product = productRepository.findById(id);
        return product.map(this::convertProductToProductDto);
    }

    @Override
    public ProductDTO upsertProduct(ProductDTO productDTO) {
        if(productDTO.getId() != null && productDTO.getId() > 0 && productDTO.getImageBytes().length == 0) {
            productDTO.setImageBytes(productRepository.findById(productDTO.getId()).get().getImageBytes());
        }

        return convertProductToProductDto(productRepository.save(convertProductDtoToProduct(productDTO)));
    }

    @Override
    public void deleteProduct(ProductDTO productDTO) {
        productRepository.delete(convertProductDtoToProduct(productDTO));
    }

    private Product convertProductDtoToProduct(ProductDTO productDTO) {
        return new Product(
                productDTO.getId(),
                productDTO.getName(),
                productDTO.getDescription(),
                productDTO.getPrice(),
                productDTO.getImageBytes(),
                categoryRepository.findById(productDTO.getCategoryId()).get()
        );
    }

    private ProductDTO convertProductToProductDto(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getImageBytes(),
                product.getCategory().getId(),
                product.getCategory()
        );
    }

}
