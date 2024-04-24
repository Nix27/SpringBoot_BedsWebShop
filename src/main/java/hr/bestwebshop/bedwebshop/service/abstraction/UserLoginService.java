package hr.bestwebshop.bedwebshop.service.abstraction;

import hr.bestwebshop.bedwebshop.dto.UserLoginDTO;
import java.util.List;

public interface UserLoginService {

    List<UserLoginDTO> getAllUserLogins();

}
