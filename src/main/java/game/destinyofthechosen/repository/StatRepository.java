package game.destinyofthechosen.repository;

import game.destinyofthechosen.model.entity.StatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StatRepository extends JpaRepository<StatEntity, UUID> {
}
