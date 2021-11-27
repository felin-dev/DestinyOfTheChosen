package game.destinyofthechosen.service;

import game.destinyofthechosen.model.entity.ZoneEntity;
import game.destinyofthechosen.model.service.ZoneCreationServiceModel;
import game.destinyofthechosen.model.view.ZoneViewModel;
import game.destinyofthechosen.model.view.ZoneWithEnemiesViewModel;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface ZoneService {

    boolean isZoneNameFree(String zoneName);

    void create(ZoneCreationServiceModel zoneModel) throws IOException;

    List<ZoneViewModel> getAllZones();

    ZoneWithEnemiesViewModel getZoneById(String username, UUID id);

    List<ZoneViewModel> getZonesInHeroLevelRange(String name, Integer level);

    void initialize();
}
