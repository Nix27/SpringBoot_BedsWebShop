package hr.bestwebshop.bedwebshop.repository;

import hr.bestwebshop.bedwebshop.model.UserLogin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLoginRepository extends JpaRepository<UserLogin, Integer> {
}
