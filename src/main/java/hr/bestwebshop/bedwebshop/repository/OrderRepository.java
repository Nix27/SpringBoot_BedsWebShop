package hr.bestwebshop.bedwebshop.repository;

import hr.bestwebshop.bedwebshop.model.Order;
import hr.bestwebshop.bedwebshop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findOrdersByUser(User user);
}
