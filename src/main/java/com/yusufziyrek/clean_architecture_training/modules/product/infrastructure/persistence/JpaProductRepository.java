package com.yusufziyrek.clean_architecture_training.modules.product.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaProductRepository extends JpaRepository<ProductEntity, Long> {

}
