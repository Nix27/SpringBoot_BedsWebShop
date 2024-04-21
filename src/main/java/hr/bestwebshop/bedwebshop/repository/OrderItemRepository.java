package hr.bestwebshop.bedwebshop.repository;

import hr.bestwebshop.bedwebshop.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
}
