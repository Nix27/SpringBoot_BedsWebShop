package hr.bestwebshop.bedwebshop.service.abstraction;

import hr.bestwebshop.bedwebshop.dto.ShoppingCartItemDTO;
import hr.bestwebshop.bedwebshop.model.User;

import java.util.List;
import java.util.Optional;

public interface ShoppingCartService {

    List<ShoppingCartItemDTO> getAllShoppingCartItemsForUser(User user);
    ShoppingCartItemDTO addShoppingCartItem(ShoppingCartItemDTO shoppingCartItemDTO);
    void deleteShoppingCartItem(Integer id);
    void deleteShoppingCartItemsForUser(User user);
    void incrementQuantityOfShoppingCartItem(Integer shoppingCartItemId);
    void decrementQuantityOfShoppingCartItem(Integer shoppingCartItemId);

}
