package cz.ivosahlik.ecommerce.controller;

import cz.ivosahlik.ecommerce.entity.Basket;
import cz.ivosahlik.ecommerce.entity.BasketItem;
import cz.ivosahlik.ecommerce.model.BasketItemResponse;
import cz.ivosahlik.ecommerce.model.BasketResponse;
import cz.ivosahlik.ecommerce.service.BasketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/baskets")
public class BasketController {
    private final BasketService basketService;

    @GetMapping
    public List<BasketResponse> getAllBaskets(){
        return basketService.getAllBaskets();
    }

    @GetMapping("/{basketId}")
    public BasketResponse getBasketById(@PathVariable String basketId){
        return basketService.getBasketById(basketId);
    }
    @DeleteMapping("/{basketId}")
    public void deleteBasketById(@PathVariable String basketId){
        basketService.deleteBasketById(basketId);
    }

    @PostMapping
    public ResponseEntity<BasketResponse> createBasket(@RequestBody BasketResponse basketResponse){
        Basket basket = convertToBasketEntity(basketResponse);
        BasketResponse createdBasket = basketService.createBasket(basket);
        return new ResponseEntity<>(createdBasket, HttpStatus.CREATED);
    }

    private Basket convertToBasketEntity(BasketResponse basketResponse) {
        Basket basket = new Basket();
        basket.setId(basketResponse.getId());
        basket.setItems(mapBasketItemResponsesToEntities(basketResponse.getItems()));
        return basket;
    }

    private List<BasketItem> mapBasketItemResponsesToEntities(List<BasketItemResponse> itemResponses) {
        return itemResponses.stream()
                .map(this::convertToBasketItemEntity)
                .toList();
    }

    private BasketItem convertToBasketItemEntity(BasketItemResponse itemResponse) {
        BasketItem basketItem = new BasketItem();
        basketItem.setId(itemResponse.getId());
        basketItem.setName(itemResponse.getName());
        basketItem.setDescription(itemResponse.getDescription());
        basketItem.setPrice(itemResponse.getPrice());
        basketItem.setPictureUrl(itemResponse.getPictureUrl());
        basketItem.setProductBrand(itemResponse.getProductBrand());
        basketItem.setProductType(itemResponse.getProductType());
        basketItem.setQuantity(itemResponse.getQuantity());
        return basketItem;
    }
}
