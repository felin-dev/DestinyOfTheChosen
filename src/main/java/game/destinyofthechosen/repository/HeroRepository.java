package game.destinyofthechosen.repository;

import game.destinyofthechosen.model.entity.HeroEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface HeroRepository extends JpaRepository<HeroEntity, UUID> {
    boolean existsByName(String heroName);

    Optional<HeroEntity> findHeroById(UUID id);
}
