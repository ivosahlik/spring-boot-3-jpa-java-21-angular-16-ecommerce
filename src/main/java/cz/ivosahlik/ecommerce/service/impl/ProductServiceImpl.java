package cz.ivosahlik.ecommerce.service.impl;

import cz.ivosahlik.ecommerce.entity.Product;
import cz.ivosahlik.ecommerce.exceptions.ProductNotFoundException;
import cz.ivosahlik.ecommerce.model.ProductResponse;
import cz.ivosahlik.ecommerce.repository.ProductRepository;
import cz.ivosahlik.ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
@Log4j2
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public ProductResponse getProductById(Integer productId) {
        log.info("Fetching Product by Id: {}", productId);
        Product product =productRepository.findById(productId)
                .orElseThrow(()->new ProductNotFoundException("Product with given id doesn't exist"));
        return convertToProductResponse(product);
    }

    @Override
    public Page<ProductResponse> getProducts(Pageable pageable) {
        log.info("Fetching products");
        Page<Product> productPage = productRepository.findAll(pageable);
        return productPage
                .map(this::convertToProductResponse);
    }

    @Override
    public List<ProductResponse> searchProductsByName(String keyword) {
        log.info("Searching product(s) by name: {}", keyword);
        return productRepository.searchByName(keyword)
                .stream()
                .map(this::convertToProductResponse)
                .toList();
    }

    @Override
    public List<ProductResponse> searchProductsByBrand(Integer brandId) {
        log.info("Searching product(s) by brandId: {}", brandId);
        return productRepository.searchByBrand(brandId).
                stream()
                .map(this::convertToProductResponse)
                .toList();
    }

    @Override
    public List<ProductResponse> searchProductsByType(Integer typeId) {
        log.info("Searching product(s) by typeId: {}", typeId);
        return productRepository.searchByType(typeId).stream()
                .map(this::convertToProductResponse)
                .toList();
    }

    @Override
    public List<ProductResponse> searchProductsByBrandandType(Integer brandId, Integer typeId) {
        log.info("Searching product(s) by brandId: {}, and typeId: {}", brandId, typeId);
        return productRepository.searchByBrandAndType(brandId, typeId).
                stream()
                .map(this::convertToProductResponse)
                .toList();
    }

    @Override
    public List<ProductResponse> searchProductsByBrandTypeAndName(Integer brandId, Integer typeId, String keyword) {
        log.info("Searching product(s) by brandId: {}, typeId: {} and keyword: {}", brandId, typeId, keyword);
        return productRepository.searchByBrandTypeAndName(brandId, typeId, keyword).
                stream()
                .map(this::convertToProductResponse)
                .toList();
    }

    private ProductResponse convertToProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .pictureUrl(product.getPictureUrl())
                .productType(product.getType().getName())
                .productBrand(product.getBrand().getName())
                .build();
    }
}
