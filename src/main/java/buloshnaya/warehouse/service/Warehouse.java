package buloshnaya.warehouse.service;

import buloshnaya.warehouse.model.Product;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface Warehouse {
    Page<Product> getProducts(UUID storeId, Integer page, Integer size);
    List<Product> addProducts(UUID storeId, List<Product> product);
    Product updateProduct(UUID storeId, Long productId, Product product);
    void deleteProduct(UUID storeId, Long productId);
}
