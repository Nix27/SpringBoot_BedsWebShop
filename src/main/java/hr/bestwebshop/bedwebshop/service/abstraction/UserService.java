package hr.bestwebshop.bedwebshop.service.abstraction;

import hr.bestwebshop.bedwebshop.model.RegistrationRequest;
import hr.bestwebshop.bedwebshop.model.User;

public interface UserService {

    void register(RegistrationRequest registrationRequest) throws Exception;

}
