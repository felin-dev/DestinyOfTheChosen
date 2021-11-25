package game.destinyofthechosen.repository;

import game.destinyofthechosen.model.entity.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ItemRepository extends JpaRepository<ItemEntity, UUID> {

    @Query("SELECT i FROM ItemEntity i ORDER BY i.type, i.levelRequirement")
    List<ItemEntity> findAllOrderByTypeThenByLevelRequirement();

    boolean existsByItemName(String itemName);
}