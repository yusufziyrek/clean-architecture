package com.yusufziyrek.clean_architecture_training.modules.product.presentation.api;

import com.yusufziyrek.clean_architecture_training.modules.product.application.CreateProductUseCase;
import com.yusufziyrek.clean_architecture_training.modules.product.application.GetProductByIdUseCase;
import com.yusufziyrek.clean_architecture_training.modules.product.application.ListProductsUseCase;
import com.yusufziyrek.clean_architecture_training.modules.product.application.ReduceStockUseCase;
import com.yusufziyrek.clean_architecture_training.modules.product.domain.Product;
import com.yusufziyrek.clean_architecture_training.modules.product.presentation.dto.CreateProductRequest;
import com.yusufziyrek.clean_architecture_training.modules.product.presentation.dto.ProductResponse;
import com.yusufziyrek.clean_architecture_training.modules.product.presentation.dto.ProductStockResponse;
import com.yusufziyrek.clean_architecture_training.modules.product.presentation.dto.ReduceStockRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

	private final ReduceStockUseCase reduceStockUseCase;
	private final CreateProductUseCase createProductUseCase;
	private final GetProductByIdUseCase getProductByIdUseCase;
	private final ListProductsUseCase listProductsUseCase;

	@PostMapping
	public ResponseEntity<ProductResponse> createProduct(@RequestBody CreateProductRequest request) {
		Product product = createProductUseCase.execute(request.name(), request.price(), request.stock());
		return new ResponseEntity<>(ProductResponse.fromDomain(product), HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) {
		Product product = getProductByIdUseCase.execute(id);
		return ResponseEntity.ok(ProductResponse.fromDomain(product));
	}

	@GetMapping
	public ResponseEntity<List<ProductResponse>> getAllProducts() {
		List<ProductResponse> products = listProductsUseCase.execute().stream()
				.map(ProductResponse::fromDomain)
				.toList();
		return ResponseEntity.ok(products);
	}

	@PostMapping("/{id}/reduce-stock")
	public ProductStockResponse reduceStock(@PathVariable Long id, @RequestBody ReduceStockRequest request) {
		Double price = reduceStockUseCase.execute(id, request.quantity());
		return new ProductStockResponse(id, price);
	}
}