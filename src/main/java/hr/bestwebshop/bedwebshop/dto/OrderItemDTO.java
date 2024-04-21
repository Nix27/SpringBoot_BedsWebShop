package hr.bestwebshop.bedwebshop.dto;

import hr.bestwebshop.bedwebshop.model.Order;
import hr.bestwebshop.bedwebshop.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDTO {

    private Integer id;
    private Integer quantity;
    private Double price;
    private Integer productId;
    private Product product;
    private Integer orderId;
    private Order order;

}
