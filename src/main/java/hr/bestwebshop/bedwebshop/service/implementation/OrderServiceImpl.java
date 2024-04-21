package hr.bestwebshop.bedwebshop.service.implementation;

import hr.bestwebshop.bedwebshop.dto.OrderDTO;
import hr.bestwebshop.bedwebshop.model.Order;
import hr.bestwebshop.bedwebshop.model.User;
import hr.bestwebshop.bedwebshop.repository.OrderRepository;
import hr.bestwebshop.bedwebshop.service.abstraction.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;

    @Override
    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(this::convertOrderToOrderDto)
                .toList();
    }

    @Override
    public List<OrderDTO> getOrdersForUser(User user) {
        return orderRepository.findOrdersByUser(user)
                .stream()
                .map(this::convertOrderToOrderDto)
                .toList();
    }

    @Override
    public OrderDTO createOrder(OrderDTO orderDTO) {
        return convertOrderToOrderDto(orderRepository.save(convertOrderDtoToOrder(orderDTO)));
    }

    public Order convertOrderDtoToOrder(OrderDTO orderDTO) {
        return new Order(
                orderDTO.getId(),
                orderDTO.getDateOfOrder(),
                orderDTO.getTotalPrice(),
                orderDTO.getFirstname(),
                orderDTO.getLastname(),
                orderDTO.getPhoneNumber(),
                orderDTO.getAddress(),
                orderDTO.getCity(),
                orderDTO.getCountry(),
                orderDTO.getPaymentType(),
                orderDTO.getUser(),
                orderDTO.getOrderItems()
        );
    }

    public OrderDTO convertOrderToOrderDto(Order order) {
        return new OrderDTO(
                order.getId(),
                order.getDateOfOrder(),
                order.getTotalPrice(),
                order.getFirstname(),
                order.getLastname(),
                order.getPhoneNumber(),
                order.getAddress(),
                order.getCity(),
                order.getCountry(),
                order.getPaymentType().getId(),
                order.getPaymentType(),
                order.getUser().getId(),
                order.getUser(),
                order.getOrderItems()
        );
    }

}
