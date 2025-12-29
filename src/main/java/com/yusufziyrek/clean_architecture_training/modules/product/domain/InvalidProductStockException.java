package com.yusufziyrek.clean_architecture_training.modules.product.domain;

import com.yusufziyrek.clean_architecture_training.common.exception.BaseException;

public class InvalidProductStockException extends BaseException {
    public InvalidProductStockException(Integer stock) {
        super("Product stock cannot be negative. Provided: " + stock, "PRODUCT_STOCK_INVALID");
    }
}
