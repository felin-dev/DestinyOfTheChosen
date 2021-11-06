package game.destinyofthechosen.repository;

import game.destinyofthechosen.model.entity.DropListEntity;
import game.destinyofthechosen.model.enumeration.ItemNameEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DropListRepository extends JpaRepository<DropListEntity, UUID> {

    @Query("SELECT i FROM ItemEntity i WHERE i.itemNameEnum IN :items")
    List<DropListEntity> findAllByName(List<ItemNameEnum> items);
}
