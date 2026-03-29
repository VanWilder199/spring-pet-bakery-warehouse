package buloshnaya.warehouse.mapper;

import buloshnaya.warehouse.entity.WarehouseItemEntity;
import buloshnaya.warehouse.model.Product;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ProductMapper {

    public WarehouseItemEntity toEntity(Product product, UUID storeId) {
        WarehouseItemEntity entity = new WarehouseItemEntity();
        entity.setProductName(product.productName());
        entity.setStoreId(storeId);
        entity.setPrice(product.price());
        entity.setQuantity(product.quantity());
        return entity;
    }

    public Product toModel(WarehouseItemEntity entity) {
        return new Product(
                entity.getProductId(),
                entity.getProductName(),
                entity.getDescription(),
                entity.getPrice(),
                entity.getQuantity(),
                entity.getCategory()
        );
    }
}