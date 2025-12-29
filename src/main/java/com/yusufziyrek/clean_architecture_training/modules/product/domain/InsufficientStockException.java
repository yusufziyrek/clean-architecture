package com.yusufziyrek.clean_architecture_training.modules.product.domain;

import com.yusufziyrek.clean_architecture_training.common.exception.BaseException;

public class InsufficientStockException extends BaseException {
	public InsufficientStockException(String productName) {
		super("Insufficient stock for " + productName, "PRODUCT_STOCK_INSUFFICIENT");
	}
}