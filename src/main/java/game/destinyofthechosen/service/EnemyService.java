package game.destinyofthechosen.service;

import game.destinyofthechosen.model.service.EnemyCreationServiceModel;
import game.destinyofthechosen.model.view.EnemyViewModel;

import java.io.IOException;
import java.util.UUID;

public interface EnemyService {

    boolean isTheNameFree(String name);

    void createEnemy(EnemyCreationServiceModel enemyModel) throws IOException;

    EnemyViewModel findById(UUID id);
}
