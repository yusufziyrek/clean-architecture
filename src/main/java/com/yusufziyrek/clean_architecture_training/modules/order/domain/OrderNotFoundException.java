package com.yusufziyrek.clean_architecture_training.modules.order.domain;

import java.util.UUID;

import com.yusufziyrek.clean_architecture_training.common.exception.BaseException;

public class OrderNotFoundException extends BaseException {
    public OrderNotFoundException(UUID id) {
        super("Order not found with ID: " + id, "ORDER_NOT_FOUND");
    }
}
