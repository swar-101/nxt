package com.example.product_catalog_service.convertor;

public interface IdConvertor {
    Long toInternalId(String externalId);
    String toExternalId(Long internalId);
}
