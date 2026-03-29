package buloshnaya.warehouse.repository;


import buloshnaya.warehouse.entity.WarehouseItemEntity;
import buloshnaya.warehouse.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface WarehouseRepository extends JpaRepository<WarehouseItemEntity, Long> {

    Page<WarehouseItemEntity> findAllByStoreId(
            @Param("storeId") UUID storeId,
            Pageable pageable);


    Optional<WarehouseItemEntity> findByProductIdAndStoreId(Long productId, UUID storeId);

    int deleteByProductIdAndStoreId(Long productId, UUID storeId);

}
