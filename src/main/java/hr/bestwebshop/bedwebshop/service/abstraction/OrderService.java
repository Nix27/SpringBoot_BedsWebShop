package hr.bestwebshop.bedwebshop.service.abstraction;


import hr.bestwebshop.bedwebshop.dto.OrderDTO;
import hr.bestwebshop.bedwebshop.dto.ShoppingCartItemDTO;
import hr.bestwebshop.bedwebshop.model.User;

import java.util.List;

public interface OrderService {

    List<OrderDTO> getAllOrders();
    List<OrderDTO> getOrdersForUser(User user);
    OrderDTO createOrder(OrderDTO orderDTO, List<ShoppingCartItemDTO> shoppingCartItems);

}
