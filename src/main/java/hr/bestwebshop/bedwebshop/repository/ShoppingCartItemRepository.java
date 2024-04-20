package hr.bestwebshop.bedwebshop.repository;

import hr.bestwebshop.bedwebshop.model.ShoppingCartItem;
import hr.bestwebshop.bedwebshop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ShoppingCartItemRepository extends JpaRepository<ShoppingCartItem, Integer> {
    List<ShoppingCartItem> findShoppingCartItemsByUser(User user);
    void deleteAllByUser(User user);
}
