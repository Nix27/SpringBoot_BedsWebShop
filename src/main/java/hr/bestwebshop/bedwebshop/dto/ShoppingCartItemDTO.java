package hr.bestwebshop.bedwebshop.dto;

import hr.bestwebshop.bedwebshop.model.Product;
import hr.bestwebshop.bedwebshop.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCartItemDTO {

    private Integer id;
    private Integer productId;
    private Product product;
    private Integer userId;
    private User user;
    private Integer quantity;
    private Double price;

}
