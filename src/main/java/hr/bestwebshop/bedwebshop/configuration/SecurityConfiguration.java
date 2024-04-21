package hr.bestwebshop.bedwebshop.configuration;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                .authorizeHttpRequests(request -> request
                        .requestMatchers(
                                "/login",
                                "/bedswebshop/user/register",
                                "/bedswebshop/home",
                                "/bedswebshop/filterProducts",
                                "/bedswebshop/productDetails/**",
                                "/bedswebshop/image/**",
                                "/bedswebshop/shoppingcart/**"
                                ).permitAll()
                        .requestMatchers(
                                "/bedswebshop/categories/**",
                                "/bedswebshop/products/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form.defaultSuccessUrl("/bedswebshop/home"))
                .logout((logout) -> logout.logoutSuccessUrl("/bedswebshop/home"))
                .logout(LogoutConfigurer::permitAll);

        return httpSecurity.build();

    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService) {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        return new ProviderManager(daoAuthenticationProvider);
    }

}
