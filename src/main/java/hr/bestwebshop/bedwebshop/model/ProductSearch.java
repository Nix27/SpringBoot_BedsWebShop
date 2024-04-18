package hr.bestwebshop.bedwebshop.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductSearch {

    private String name;
    private Double priceFrom;
    private Double priceTo;
    private Integer categoryId;

}
