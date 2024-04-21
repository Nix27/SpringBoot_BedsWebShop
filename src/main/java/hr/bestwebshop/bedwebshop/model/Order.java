package hr.bestwebshop.bedwebshop.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Date dateOfOrder;
    private Double totalPrice;

    private String firstname;
    private String lastname;
    private String phoneNumber;
    private String address;
    private String city;
    private String country;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "paymentTypeId")
    private PaymentType paymentType;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "userId")
    private User user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order", cascade = CascadeType.DETACH)
    private List<OrderItem> orderItems;
}
