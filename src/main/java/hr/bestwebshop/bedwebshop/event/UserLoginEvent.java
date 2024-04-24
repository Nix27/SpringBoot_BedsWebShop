package hr.bestwebshop.bedwebshop.event;

import hr.bestwebshop.bedwebshop.model.User;
import hr.bestwebshop.bedwebshop.model.UserLogin;
import hr.bestwebshop.bedwebshop.repository.UserLoginRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;

@Component
@AllArgsConstructor
public class UserLoginEvent {

    private UserLoginRepository userLoginRepository;

    @EventListener
    public void onSuccess(AuthenticationSuccessEvent success) {
        User user = (User) success.getAuthentication().getPrincipal();
        String ipAddress = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getRemoteAddr();
        userLoginRepository.save(new UserLogin(0, user.getEmail(), ipAddress, LocalDateTime.now()));
    }
}
