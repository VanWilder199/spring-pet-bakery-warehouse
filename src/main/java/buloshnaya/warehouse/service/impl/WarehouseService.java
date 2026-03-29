package buloshnaya.warehouse.service.impl;

import buloshnaya.warehouse.entity.WarehouseItemEntity;
import buloshnaya.warehouse.mapper.ProductMapper;
import buloshnaya.warehouse.model.Product;
import buloshnaya.warehouse.repository.WarehouseRepository;
import buloshnaya.warehouse.service.Warehouse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;


@Service
public class WarehouseService  implements Warehouse {

    private final WarehouseRepository warehouseRepository;
    private final ProductMapper mapper;

    public WarehouseService(WarehouseRepository warehouseRepository, ProductMapper mapper) {
        this.warehouseRepository = warehouseRepository;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Product> getProducts(UUID storeId, Integer page, Integer size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);

       return warehouseRepository.findAllByStoreId(storeId, pageable).map(mapper::toModel);
    }

    @Override
    public List<Product> addProducts(UUID storeId, List<Product> products) {

        List<WarehouseItemEntity> saved = warehouseRepository.saveAll(
                products.stream().map(p -> mapper.toEntity(p, storeId)).toList());


        return saved.stream().map(mapper::toModel).toList();
    }

    @Transactional
    @Override
    public Product updateProduct(UUID storeId, Long productId, Product product) {
        WarehouseItemEntity existingProductEntity = warehouseRepository.findByProductIdAndStoreId(productId, storeId).orElseThrow(() -> new EntityNotFoundException("Product not found"));

        existingProductEntity.setProductName(product.productName());
        existingProductEntity.setDescription(product.description());
        existingProductEntity.setCategory(product.category());
        existingProductEntity.setQuantity(product.quantity());

        WarehouseItemEntity saved = warehouseRepository.save(existingProductEntity);

        return mapper.toModel(saved);
    }

    @Override
    public void deleteProduct(UUID storeId, Long productId) {
        int deleted = warehouseRepository.deleteByProductIdAndStoreId(productId, storeId);
        if (deleted == 0) {
            throw new EntityNotFoundException("Product not found");
        }
    }
}
