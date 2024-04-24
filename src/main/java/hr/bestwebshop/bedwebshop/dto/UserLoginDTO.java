package hr.bestwebshop.bedwebshop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginDTO {

    private Integer id;
    private String userEmail;
    private String ipAddress;
    private LocalDateTime timeOfLogin;

}
