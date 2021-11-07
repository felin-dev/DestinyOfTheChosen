package game.destinyofthechosen.repository;

import game.destinyofthechosen.model.entity.DropListEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DropListRepository extends JpaRepository<DropListEntity, UUID> {
}
