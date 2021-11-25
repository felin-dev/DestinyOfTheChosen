package game.destinyofthechosen.repository;

import game.destinyofthechosen.model.entity.ZoneEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ZoneRepository extends JpaRepository<ZoneEntity, UUID> {

    boolean existsByZoneName(String zoneName);

    Optional<ZoneEntity> findByZoneName(String zoneName);

    @Query("SELECT z FROM ZoneEntity z WHERE z.levelRequirement <= :levelRequirement ORDER BY z.levelRequirement")
    Optional<List<ZoneEntity>> findByLevelLowerOrEqualOrdered(Integer levelRequirement);
}
