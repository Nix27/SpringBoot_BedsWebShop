package hr.bestwebshop.bedwebshop.repository;

import hr.bestwebshop.bedwebshop.model.Product;
import hr.bestwebshop.bedwebshop.model.ShoppingCartItem;
import hr.bestwebshop.bedwebshop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ShoppingCartItemRepository extends JpaRepository<ShoppingCartItem, Integer> {
    List<ShoppingCartItem> findShoppingCartItemsByUser(User user);
    List<ShoppingCartItem> findShoppingCartItemByUuid(String uuid);
    List<ShoppingCartItem> findShoppingCartItemsByUserNull();
    Optional<ShoppingCartItem> findShoppingCartItemByProductAndUser(Product product, User user);
    void deleteAllByUser(User user);
}
