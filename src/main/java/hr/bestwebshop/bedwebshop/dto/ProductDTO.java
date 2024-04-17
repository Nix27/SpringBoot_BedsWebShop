package hr.bestwebshop.bedwebshop.dto;

import hr.bestwebshop.bedwebshop.model.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private Integer id;
    private String name;
    private String description;
    private Double price;
    private byte[] imageBytes;
    private Integer categoryId;
    private Category category;

}
