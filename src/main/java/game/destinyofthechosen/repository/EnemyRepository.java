package game.destinyofthechosen.repository;

import game.destinyofthechosen.model.entity.EnemyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EnemyRepository extends JpaRepository<EnemyEntity, UUID> {
    boolean existsByEnemyName(String name);


    EnemyEntity findByEnemyName(String enemyName);
}
