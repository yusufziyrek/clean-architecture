package com.yusufziyrek.clean_architecture_training.modules.product.domain;

import com.yusufziyrek.clean_architecture_training.common.exception.BaseException;

public class InvalidProductPriceException extends BaseException {
    public InvalidProductPriceException(Double price) {
        super("Product price must be positive. Provided: " + price, "PRODUCT_PRICE_INVALID");
    }
}
