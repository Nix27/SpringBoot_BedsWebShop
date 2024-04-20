package hr.bestwebshop.bedwebshop.service.abstraction;

import hr.bestwebshop.bedwebshop.model.RegistrationRequest;
import hr.bestwebshop.bedwebshop.model.User;

import java.util.Optional;

public interface UserService {

    void register(RegistrationRequest registrationRequest) throws Exception;

}
