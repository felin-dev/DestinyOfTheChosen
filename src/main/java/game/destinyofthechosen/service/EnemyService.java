package game.destinyofthechosen.service;

import game.destinyofthechosen.model.service.EnemyCreationServiceModel;

import java.io.IOException;

public interface EnemyService {

    boolean isTheNameFree(String name);

    void createEnemy(EnemyCreationServiceModel enemyModel) throws IOException;
}
