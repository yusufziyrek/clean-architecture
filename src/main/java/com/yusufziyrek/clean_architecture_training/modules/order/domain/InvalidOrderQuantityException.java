package com.yusufziyrek.clean_architecture_training.modules.order.domain;

import com.yusufziyrek.clean_architecture_training.common.exception.BaseException;

public class InvalidOrderQuantityException extends BaseException {
    public InvalidOrderQuantityException(Integer wrongQuantity) {
        super("Sipariş adedi en az 1 olmalıdır. Girilen: " + wrongQuantity, "ORDER_QUANTITY_INVALID");
    }
}
