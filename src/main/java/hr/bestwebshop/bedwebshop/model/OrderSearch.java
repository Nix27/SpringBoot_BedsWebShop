package hr.bestwebshop.bedwebshop.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderSearch {

    private String username;
    private String fromDate;
    private String toDate;

}
