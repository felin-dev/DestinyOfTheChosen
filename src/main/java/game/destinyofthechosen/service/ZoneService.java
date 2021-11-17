package game.destinyofthechosen.service;

import game.destinyofthechosen.model.service.ZoneCreationServiceModel;
import game.destinyofthechosen.model.view.ZoneViewModel;
import game.destinyofthechosen.model.view.ZoneWithEnemiesViewModel;

import java.io.IOException;
import java.util.List;

public interface ZoneService {

    boolean isZoneNameFree(String zoneName);

    void create(ZoneCreationServiceModel zoneModel) throws IOException;

    List<ZoneViewModel> getAllZones();

    ZoneWithEnemiesViewModel getZoneByLevelRequirement(String username,  Integer levelRequirement);
}