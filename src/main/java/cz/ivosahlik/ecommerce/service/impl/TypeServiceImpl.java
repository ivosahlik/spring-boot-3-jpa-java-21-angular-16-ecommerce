package cz.ivosahlik.ecommerce.service.impl;

import cz.ivosahlik.ecommerce.entity.Type;
import cz.ivosahlik.ecommerce.model.TypeResponse;
import cz.ivosahlik.ecommerce.repository.TypeRepository;
import cz.ivosahlik.ecommerce.service.TypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
@Log4j2
public class TypeServiceImpl implements TypeService {
    private final TypeRepository typeRepository;

    @Override
    public List<TypeResponse> getAllTypes() {
        log.info("Fetching all Types");
        return typeRepository.findAll().
                stream()
                .map(this::convertToTypeResponse)
                .toList();
    }

    private TypeResponse convertToTypeResponse(Type type) {
        return TypeResponse.builder()
                .id(type.getId())
                .name(type.getName())
                .build();
    }
}
