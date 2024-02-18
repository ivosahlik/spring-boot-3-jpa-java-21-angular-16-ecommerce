package cz.ivosahlik.ecommerce.service;

import cz.ivosahlik.ecommerce.model.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    ProductResponse getProductById(Integer productId);
    Page<ProductResponse> getProducts(Pageable pageable);
    List<ProductResponse> searchProductsByName(String keyword);
    List<ProductResponse> searchProductsByBrand(Integer brandId);
    List<ProductResponse> searchProductsByType(Integer typeId);
    List<ProductResponse> searchProductsByBrandandType(Integer brandId, Integer typeId);
    List<ProductResponse> searchProductsByBrandTypeAndName(Integer brandId, Integer typeId, String keyword);
}
