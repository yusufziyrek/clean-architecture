package com.yusufziyrek.clean_architecture_training.modules.product.presentation.api;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yusufziyrek.clean_architecture_training.modules.product.application.ReduceStockUseCase;
import com.yusufziyrek.clean_architecture_training.modules.product.presentation.dto.ProductStockResponse;
import com.yusufziyrek.clean_architecture_training.modules.product.presentation.dto.ReduceStockRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
	private final ReduceStockUseCase reduceStockUseCase;

	@PostMapping("/{id}/reduce-stock")
	public ProductStockResponse reduceStock(@PathVariable Long id, @RequestBody ReduceStockRequest request) {
		Double price = reduceStockUseCase.execute(id, request.quantity());
		return new ProductStockResponse(id, price);
	}
}