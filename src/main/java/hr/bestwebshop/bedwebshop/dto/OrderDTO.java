package hr.bestwebshop.bedwebshop.dto;

import hr.bestwebshop.bedwebshop.model.Order;
import hr.bestwebshop.bedwebshop.model.OrderItem;
import hr.bestwebshop.bedwebshop.model.PaymentType;
import hr.bestwebshop.bedwebshop.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

    private Integer id;
    private Date dateOfOrder;
    private Double totalPrice;
    private String firstname;
    private String lastname;
    private String phoneNumber;
    private String address;
    private String city;
    private String country;
    private Integer paymentTypeId;
    private PaymentType paymentType;
    private Integer userId;
    private User user;
    private List<OrderItem> orderItems;

}
