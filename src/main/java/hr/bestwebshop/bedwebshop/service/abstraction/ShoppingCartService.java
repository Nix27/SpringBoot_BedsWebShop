package hr.bestwebshop.bedwebshop.service.abstraction;

import hr.bestwebshop.bedwebshop.dto.ShoppingCartItemDTO;
import hr.bestwebshop.bedwebshop.model.User;

import java.util.List;
import java.util.Optional;

public interface ShoppingCartService {

    List<ShoppingCartItemDTO> getAllShoppingCartItemsForUser(User user, String uuid);
    List<ShoppingCartItemDTO> getAllShoppingCartItemsWithoutUser(String uuid);
    ShoppingCartItemDTO addShoppingCartItem(ShoppingCartItemDTO shoppingCartItemDTO);
    Double getTotalPrice(List<ShoppingCartItemDTO> shoppingCartItems);
    void deleteShoppingCartItem(Integer id);
    void incrementQuantityOfShoppingCartItem(Integer shoppingCartItemId);
    void decrementQuantityOfShoppingCartItem(Integer shoppingCartItemId);

}
