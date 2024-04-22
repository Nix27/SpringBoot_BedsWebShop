package hr.bestwebshop.bedwebshop.service.implementation;

import hr.bestwebshop.bedwebshop.dto.ShoppingCartItemDTO;
import hr.bestwebshop.bedwebshop.model.ShoppingCartItem;
import hr.bestwebshop.bedwebshop.model.User;
import hr.bestwebshop.bedwebshop.repository.ProductRepository;
import hr.bestwebshop.bedwebshop.repository.ShoppingCartItemRepository;
import hr.bestwebshop.bedwebshop.repository.UserRepository;
import hr.bestwebshop.bedwebshop.service.abstraction.ShoppingCartService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private ShoppingCartItemRepository shoppingCartItemRepository;
    private ProductRepository productRepository;
    private UserRepository userRepository;

    @Override
    public List<ShoppingCartItemDTO> getAllShoppingCartItemsForUser(User user) {

        if(user != null) {
            return shoppingCartItemRepository.findShoppingCartItemsByUser(user)
                    .stream()
                    .map(this::convertShoppingCartItemToShoppingCartItemDto)
                    .toList();
        }

        return shoppingCartItemRepository.findShoppingCartItemsByUserNull()
                .stream()
                .map(this::convertShoppingCartItemToShoppingCartItemDto)
                .toList();
    }

    @Override
    public ShoppingCartItemDTO addShoppingCartItem(ShoppingCartItemDTO shoppingCartItemDTO) {
        ShoppingCartItem shoppingCartItemFromDto = convertShoppingCartItemDtoToShoppingCartItem(shoppingCartItemDTO);
        Optional<ShoppingCartItem> shoppingCartItemOptional = shoppingCartItemRepository.findShoppingCartItemByProductAndUser(shoppingCartItemFromDto.getProduct(), shoppingCartItemFromDto.getUser());
        if(shoppingCartItemOptional.isEmpty()) {
            return convertShoppingCartItemToShoppingCartItemDto(shoppingCartItemRepository.save(shoppingCartItemFromDto));
        }

        ShoppingCartItem shoppingCartItem = shoppingCartItemOptional.get();
        shoppingCartItem.setQuantity(shoppingCartItem.getQuantity() + shoppingCartItemDTO.getQuantity());
        return convertShoppingCartItemToShoppingCartItemDto(shoppingCartItemRepository.save(shoppingCartItem));
    }

    @Override
    public Double getTotalPrice(List<ShoppingCartItemDTO> shoppingCartItems) {
        Double totalPrice = 0.0;

        for (ShoppingCartItemDTO item : shoppingCartItems) {
            totalPrice += item.getPrice();
        }

        return totalPrice;
    }

    @Override
    public void deleteShoppingCartItem(Integer id) {
        ShoppingCartItem shoppingCartItem = shoppingCartItemRepository.findById(id).get();
        shoppingCartItemRepository.delete(shoppingCartItem);
    }

    @Override
    public void deleteShoppingCartItemsForUser(User user) {
        shoppingCartItemRepository.deleteAllByUser(user);
    }

    @Override
    public void incrementQuantityOfShoppingCartItem(Integer shoppingCartItemId) {
        ShoppingCartItem shoppingCartItem = shoppingCartItemRepository.findById(shoppingCartItemId).get();
        shoppingCartItem.setQuantity(shoppingCartItem.getQuantity() + 1);
        shoppingCartItemRepository.save(shoppingCartItem);
    }

    @Override
    public void decrementQuantityOfShoppingCartItem(Integer shoppingCartItemId) {
        ShoppingCartItem shoppingCartItem = shoppingCartItemRepository.findById(shoppingCartItemId).get();
        if(shoppingCartItem.getQuantity() == 1) {
            shoppingCartItemRepository.delete(shoppingCartItem);
            return;
        }

        shoppingCartItem.setQuantity(shoppingCartItem.getQuantity() - 1);
        shoppingCartItemRepository.save(shoppingCartItem);
    }

    public ShoppingCartItem convertShoppingCartItemDtoToShoppingCartItem(ShoppingCartItemDTO shoppingCartItemDTO) {
        return new ShoppingCartItem(
                shoppingCartItemDTO.getId(),
                productRepository.findById(shoppingCartItemDTO.getProductId()).get(),
                (shoppingCartItemDTO.getUserId() != null ? userRepository.findById(shoppingCartItemDTO.getUserId()).get() : null),
                shoppingCartItemDTO.getQuantity()
        );
    }

    public ShoppingCartItemDTO convertShoppingCartItemToShoppingCartItemDto(ShoppingCartItem shoppingCartItem) {
        return new ShoppingCartItemDTO(
                shoppingCartItem.getId(),
                shoppingCartItem.getProduct().getId(),
                shoppingCartItem.getProduct(),
                (shoppingCartItem.getUser() != null ? shoppingCartItem.getUser().getId() : null),
                shoppingCartItem.getUser(),
                shoppingCartItem.getQuantity(),
                shoppingCartItem.getProduct().getPrice() * shoppingCartItem.getQuantity()
        );
    }
}
