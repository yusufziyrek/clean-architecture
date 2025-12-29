package com.yusufziyrek.clean_architecture_training.modules.order.domain;

import com.yusufziyrek.clean_architecture_training.common.exception.BaseException;

public class InvalidOrderQuantityException extends BaseException {
    public InvalidOrderQuantityException(Integer wrongQuantity) {
        super("Order quantity must be at least 1. Provided: " + wrongQuantity, "ORDER_QUANTITY_INVALID");
    }
}
