package hr.bestwebshop.bedwebshop.service.implementation;

import hr.bestwebshop.bedwebshop.dto.UserLoginDTO;
import hr.bestwebshop.bedwebshop.model.UserLogin;
import hr.bestwebshop.bedwebshop.repository.UserLoginRepository;
import hr.bestwebshop.bedwebshop.service.abstraction.UserLoginService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserLoginServiceImpl implements UserLoginService {

    private UserLoginRepository userLoginRepository;

    @Override
    public List<UserLoginDTO> getAllUserLogins() {
        return userLoginRepository.findAll()
                .stream()
                .map(this::convertUserLoginToUserLoginDto)
                .toList();
    }

    private UserLogin convertUserLoginDtoToUserLogin(UserLoginDTO userLoginDTO) {
        return new UserLogin(
                userLoginDTO.getId(),
                userLoginDTO.getUserEmail(),
                userLoginDTO.getIpAddress(),
                userLoginDTO.getTimeOfLogin()
        );
    }

    private UserLoginDTO convertUserLoginToUserLoginDto(UserLogin userLogin) {
        return new UserLoginDTO(
                userLogin.getId(),
                userLogin.getUserEmail(),
                userLogin.getIpAddress(),
                userLogin.getTimeOfLogin()
        );
    }

}
