package hr.bestwebshop.bedwebshop.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationRequest {

    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String address;
    private String city;
    private String country;
    private String phoneNumber;

}
