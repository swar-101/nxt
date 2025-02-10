package com.example.product_catalog_service.convertor.uuid;

import com.example.product_catalog_service.convertor.IdConvertor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UUIDIdConvertor implements IdConvertor {

    @Override
    public Long toInternalId(String externalId) {
        UUID uuid = UUID.fromString(externalId);
        return uuid.getMostSignificantBits();
    }

    @Override
    public String toExternalId(Long internalId) {
        UUID uuid = new UUID(internalId, internalId); // using the same ID for simplicity
        return uuid.toString();
    }
}