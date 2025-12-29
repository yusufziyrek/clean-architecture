package com.yusufziyrek.clean_architecture_training.modules.product.domain;

import com.yusufziyrek.clean_architecture_training.common.exception.BaseException;

public class ProductNotFoundException extends BaseException {
    public ProductNotFoundException(Long id) {
        super("Ürün bulunamadı. ID: " + id, "PRODUCT_NOT_FOUND");
    }
}