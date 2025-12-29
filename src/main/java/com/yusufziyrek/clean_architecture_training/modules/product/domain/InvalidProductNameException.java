package com.yusufziyrek.clean_architecture_training.modules.product.domain;

import com.yusufziyrek.clean_architecture_training.common.exception.BaseException;

public class InvalidProductNameException extends BaseException {
    public InvalidProductNameException() {
        super("Product name cannot be null or empty", "PRODUCT_NAME_INVALID");
    }
}
