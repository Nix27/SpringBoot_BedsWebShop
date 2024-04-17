package hr.bestwebshop.bedwebshop.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;
    private Double price;
    @Lob
    private byte[] imageBytes;
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "categoryId")
    private Category category;

}