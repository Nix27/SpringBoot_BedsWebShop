package hr.bestwebshop.bedwebshop;

import hr.bestwebshop.bedwebshop.model.Role;
import hr.bestwebshop.bedwebshop.model.User;
import hr.bestwebshop.bedwebshop.repository.RoleRepository;
import hr.bestwebshop.bedwebshop.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class BedWebShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(BedWebShopApplication.class, args);
    }

    @Bean
    CommandLineRunner run(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if(roleRepository.findByName("ROLE_ADMIN").isPresent()) return;

            Role adminRole = roleRepository.save(new Role("ROLE_ADMIN"));
            roleRepository.save(new Role("ROLE_USER"));

            Set<Role> roles = new HashSet<>();
            roles.add(adminRole);

            User adminUser = new User(0, "admin", "pero", "peric", passwordEncoder.encode("pass123"), "pero@mail.com", "address1", "Zagreb", "Croatia", "1234567", roles);
            userRepository.save(adminUser);
        };
    }

}
