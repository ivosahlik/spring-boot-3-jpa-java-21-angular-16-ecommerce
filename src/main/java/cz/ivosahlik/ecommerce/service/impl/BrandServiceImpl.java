package cz.ivosahlik.ecommerce.service.impl;

import cz.ivosahlik.ecommerce.entity.Brand;
import cz.ivosahlik.ecommerce.model.BrandResponse;
import cz.ivosahlik.ecommerce.repository.BrandRepository;
import cz.ivosahlik.ecommerce.service.BrandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
@Log4j2
public class BrandServiceImpl implements BrandService {
    private final BrandRepository brandRepository;

    @Override
    public List<BrandResponse> getAllBrands() {
        log.info("Fetching all Brands!!!");
        return brandRepository.findAll()
                .stream()
                .map(this::convertToBrandResponse)
                .toList();

    }
    private BrandResponse convertToBrandResponse(Brand brand){
        return BrandResponse.builder()
                .id(brand.getId())
                .name(brand.getName())
                .build();
    }
}
