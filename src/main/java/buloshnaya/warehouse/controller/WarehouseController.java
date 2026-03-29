package buloshnaya.warehouse.controller;


import buloshnaya.warehouse.model.Product;
import buloshnaya.warehouse.service.impl.WarehouseService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/warehouse")
public class WarehouseController {
    private static final Logger logger = LoggerFactory.getLogger(WarehouseController.class);

    private final WarehouseService warehouseService;

    public WarehouseController(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }


    @GetMapping("/products")
    public ResponseEntity<Page<Product>> getProducts(
            @RequestHeader("X-User-Store-Id") UUID storeId,
            @RequestParam(value = "size", required = false) Integer size,
            @RequestParam(value = "page", required = false) Integer page
    ) {
        logger.info("Fetching products with page: {}, size: {}", page, size);
        Page<Product> products = warehouseService.getProducts(storeId, page, size);
        return ResponseEntity.ok(products);
    }

    @PostMapping("/products")
    public ResponseEntity<List<Product>> createProducts(
            @RequestHeader("X-User-Store-Id") UUID storeId,
            @RequestBody @Valid List<Product> productList
    ) {
        logger.info("Adding products: {}", productList);
        List<Product> addedProducts = warehouseService.addProducts(storeId,productList);

        return ResponseEntity.status(HttpStatus.CREATED).body(addedProducts);
    }

    @PutMapping("/products/{productId}")
    public ResponseEntity<Product> updateProduct(
            @RequestHeader("X-User-Store-Id") UUID storeId,
            @PathVariable("productId") Long productId,
            @RequestBody @Valid Product product
    ) {
        logger.info("Updating products: {}", product);

        Product updatedProduct = warehouseService.updateProduct(storeId,productId, product);

        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/products/{productId}")
    public ResponseEntity<Void> deleteProduct(
            @RequestHeader("X-User-Store-Id") UUID storeId,
            @PathVariable("productId") Long productId
    ) {
        logger.info("Deleting products: {}", productId);
       warehouseService.deleteProduct(storeId,productId);

        return ResponseEntity.noContent().build();
    }
}
