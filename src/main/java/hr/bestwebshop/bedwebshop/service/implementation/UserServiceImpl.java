package hr.bestwebshop.bedwebshop.service.implementation;

import hr.bestwebshop.bedwebshop.enums.UserRole;
import hr.bestwebshop.bedwebshop.model.RegistrationRequest;
import hr.bestwebshop.bedwebshop.model.Role;
import hr.bestwebshop.bedwebshop.model.User;
import hr.bestwebshop.bedwebshop.repository.RoleRepository;
import hr.bestwebshop.bedwebshop.repository.UserRepository;
import hr.bestwebshop.bedwebshop.service.abstraction.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserDetailsService, UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findUserByUsername(username);
        if(user.isEmpty()) throw new UsernameNotFoundException("User not found");
        return user.get();
    }

    @Override
    public void register(RegistrationRequest registrationRequest) throws Exception {

        Optional<User> user = userRepository.findUserByUsernameOrEmail(registrationRequest.getUsername(), registrationRequest.getEmail());

        if(user.isPresent()) throw new Exception("User already exists with same username or email");

        String encodedPassword = passwordEncoder.encode(registrationRequest.getPassword());
        Role userRole = roleRepository.findByName(UserRole.USER.name()).get();

        Set<Role> userRoles = new HashSet<>();
        userRoles.add(userRole);

        userRepository.save(new User(
                0,
                registrationRequest.getUsername(),
                registrationRequest.getFirstname(),
                registrationRequest.getLastname(),
                encodedPassword,
                registrationRequest.getEmail(),
                registrationRequest.getAddress(),
                registrationRequest.getCity(),
                registrationRequest.getCountry(),
                registrationRequest.getPhoneNumber(),
                userRoles
        ));
    }
}
