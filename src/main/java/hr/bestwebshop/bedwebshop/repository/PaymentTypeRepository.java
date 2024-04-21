package hr.bestwebshop.bedwebshop.repository;

import hr.bestwebshop.bedwebshop.model.PaymentType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentTypeRepository extends JpaRepository<PaymentType, Integer> {
}
