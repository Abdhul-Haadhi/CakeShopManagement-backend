package com.example.CakeShopManagement.repository;

import com.example.CakeShopManagement.entity.ProductEntity;
import com.example.CakeShopManagement.entity.RecipeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<RecipeEntity,Long> {
    List<RecipeEntity> findByProductProductId(Long productId);
    boolean existsByProductProductIdAndInventoryInventoryId(Long productId, Long inventoryId);

    List<RecipeEntity> findAllByProductProductId(Long productId);
//    void deleteByProductProductId(Long productId);

    @Query("""
    SELECT DISTINCT r.product 
    FROM RecipeEntity r 
    ORDER BY r.product.productName
    """)
    List<ProductEntity> findProductsWithRecipes();
}
