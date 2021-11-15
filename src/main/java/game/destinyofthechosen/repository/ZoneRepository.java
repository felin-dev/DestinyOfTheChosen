package game.destinyofthechosen.repository;

import game.destinyofthechosen.model.entity.ZoneEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ZoneRepository extends JpaRepository<ZoneEntity, UUID> {
    boolean existsByName(String zoneName);

    Optional<ZoneEntity> findByName(String zoneName);
}
