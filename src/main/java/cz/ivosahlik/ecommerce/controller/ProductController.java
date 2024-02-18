package cz.ivosahlik.ecommerce.controller;

import cz.ivosahlik.ecommerce.model.BrandResponse;
import cz.ivosahlik.ecommerce.model.ProductResponse;
import cz.ivosahlik.ecommerce.model.TypeResponse;
import cz.ivosahlik.ecommerce.service.BrandService;
import cz.ivosahlik.ecommerce.service.ProductService;
import cz.ivosahlik.ecommerce.service.TypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;
    private final TypeService typeService;
    private final BrandService brandService;

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable("id") Integer productId) {
        ProductResponse productResponse = productService.getProductById(productId);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<Page<ProductResponse>> getProducts(
            @PageableDefault(size = 10) Pageable pageable,
            @RequestParam(name = "keyword", required = false) String keyword,
            @RequestParam(name = "brandId", required = false) Integer brandId,
            @RequestParam(name = "typeId", required = false) Integer typeId,
            @RequestParam(name = "sort", defaultValue = "name") String sort,
            @RequestParam(name = "order", defaultValue = "asc") String order
    ) {
        Page<ProductResponse> productResponsePage;
        if (brandId != null && typeId != null && keyword != null && !keyword.isEmpty()) {
            List<ProductResponse> productResponses = productService.searchProductsByBrandTypeAndName(brandId, typeId, keyword);
            productResponsePage = new PageImpl<>(productResponses, pageable, productResponses.size());
        } else if (brandId != null && typeId != null) {
            List<ProductResponse> productResponses = productService.searchProductsByBrandandType(brandId, typeId);
            productResponsePage = new PageImpl<>(productResponses, pageable, productResponses.size());
        } else if (brandId != null) {
            List<ProductResponse> productResponses = productService.searchProductsByBrand(brandId);
            productResponsePage = new PageImpl<>(productResponses, pageable, productResponses.size());
        } else if (typeId != null) {
            List<ProductResponse> productResponses = productService.searchProductsByType(typeId);
            productResponsePage = new PageImpl<>(productResponses, pageable, productResponses.size());
        } else if (keyword != null && !keyword.isEmpty()) {
            List<ProductResponse> productResponses = productService.searchProductsByName(keyword);
            productResponsePage = new PageImpl<>(productResponses, pageable, productResponses.size());
        } else {
            Sort.Direction direction = "asc".equalsIgnoreCase(order) ? Sort.Direction.ASC : Sort.Direction.DESC;
            Sort sorting = Sort.by(direction, sort);
            productResponsePage = productService.getProducts(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sorting));
        }
        return new ResponseEntity<>(productResponsePage, HttpStatus.OK);
    }

    @GetMapping("/brands")
    public ResponseEntity<List<BrandResponse>> getBrands() {
        List<BrandResponse> brandResponses = brandService.getAllBrands();
        return new ResponseEntity<>(brandResponses, HttpStatus.OK);
    }

    @GetMapping("/types")
    public ResponseEntity<List<TypeResponse>> getTypes() {
        List<TypeResponse> typeResponses = typeService.getAllTypes();
        return new ResponseEntity<>(typeResponses, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductResponse>> searchProducts(@RequestParam("keyword") String keyword) {
        List<ProductResponse> productResponses = productService.searchProductsByName(keyword);
        return new ResponseEntity<>(productResponses, HttpStatus.OK);
    }
}
