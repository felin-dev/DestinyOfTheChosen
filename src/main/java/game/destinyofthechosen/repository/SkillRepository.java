package game.destinyofthechosen.repository;

import game.destinyofthechosen.model.entity.SkillEntity;
import game.destinyofthechosen.model.enumeration.HeroRoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SkillRepository extends JpaRepository<SkillEntity, UUID> {

    Optional<SkillEntity> findBySkillNameAndLevel(String skillName, Integer skillLevel);

    Optional<SkillEntity> findByRoleAndLevel(HeroRoleEnum heroRole, int skillLevel);
}
