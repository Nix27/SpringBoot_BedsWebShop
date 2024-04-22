package hr.bestwebshop.bedwebshop.service.implementation;

import hr.bestwebshop.bedwebshop.dto.OrderDTO;
import hr.bestwebshop.bedwebshop.dto.ShoppingCartItemDTO;
import hr.bestwebshop.bedwebshop.model.Order;
import hr.bestwebshop.bedwebshop.model.OrderItem;
import hr.bestwebshop.bedwebshop.model.ShoppingCartItem;
import hr.bestwebshop.bedwebshop.model.User;
import hr.bestwebshop.bedwebshop.repository.*;
import hr.bestwebshop.bedwebshop.service.abstraction.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;
    private OrderItemRepository orderItemRepository;
    private ShoppingCartItemRepository shoppingCartItemRepository;
    private ProductRepository productRepository;
    private UserRepository userRepository;
    private PaymentTypeRepository paymentTypeRepository;

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
    public OrderDTO createOrder(OrderDTO orderDTO, List<ShoppingCartItemDTO> shoppingCartItems) {
        orderDTO.setDateOfOrder(new Date());
        Order order = orderRepository.save(convertOrderDtoToOrder(orderDTO));
        createOrderItemsForOrder(order, shoppingCartItems);
        deleteShoppingCartItemsForOrder(shoppingCartItems);
        return convertOrderToOrderDto(order);
    }

    private void deleteShoppingCartItemsForOrder(List<ShoppingCartItemDTO> shoppingCartItemDTOs) {
        List<ShoppingCartItem> shoppingCartItems = shoppingCartItemDTOs
                .stream()
                .map(this::convertShoppingCartItemDtoToShoppingCartItem)
                .toList();

        shoppingCartItemRepository.deleteAll(shoppingCartItems);
    }

    private void createOrderItemsForOrder(Order order, List<ShoppingCartItemDTO> shoppingCartItems) {
        for (ShoppingCartItemDTO item : shoppingCartItems) {
            orderItemRepository.save(new OrderItem(
                    0,
                    item.getQuantity(),
                    item.getPrice(),
                    item.getProduct(),
                    order
            ));
        }
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
                paymentTypeRepository.findById(orderDTO.getPaymentTypeId()).get(),
                userRepository.findById(orderDTO.getUserId()).get(),
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

    public ShoppingCartItem convertShoppingCartItemDtoToShoppingCartItem(ShoppingCartItemDTO shoppingCartItemDTO) {
        return new ShoppingCartItem(
                shoppingCartItemDTO.getId(),
                productRepository.findById(shoppingCartItemDTO.getProductId()).get(),
                (shoppingCartItemDTO.getUserId() != null ? userRepository.findById(shoppingCartItemDTO.getUserId()).get() : null),
                shoppingCartItemDTO.getQuantity()
        );
    }

}
