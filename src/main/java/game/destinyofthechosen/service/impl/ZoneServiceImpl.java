package game.destinyofthechosen.service.impl;

import game.destinyofthechosen.exception.ObjectNotFoundException;
import game.destinyofthechosen.exception.UserHasNoPermissionToAccessException;
import game.destinyofthechosen.model.entity.EnemyEntity;
import game.destinyofthechosen.model.entity.ZoneEntity;
import game.destinyofthechosen.model.service.CloudinaryImage;
import game.destinyofthechosen.model.service.ZoneCreationServiceModel;
import game.destinyofthechosen.model.view.EnemyViewModel;
import game.destinyofthechosen.model.view.ZoneViewModel;
import game.destinyofthechosen.model.view.ZoneWithEnemiesViewModel;
import game.destinyofthechosen.repository.ZoneRepository;
import game.destinyofthechosen.service.CloudinaryService;
import game.destinyofthechosen.service.UserService;
import game.destinyofthechosen.service.ZoneService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ZoneServiceImpl implements ZoneService {

    private final ZoneRepository zoneRepository;
    private final UserService userService;
    private final CloudinaryService cloudinaryService;
    private final ModelMapper modelMapper;

    public ZoneServiceImpl(ZoneRepository zoneRepository, UserService userService, CloudinaryService cloudinaryService, ModelMapper modelMapper) {
        this.zoneRepository = zoneRepository;
        this.userService = userService;
        this.cloudinaryService = cloudinaryService;
        this.modelMapper = modelMapper;
    }

    @Override
    public ZoneWithEnemiesViewModel getZoneByLevelRequirement(String username, Integer levelRequirement) {

        if (!userService.isOverTheLevelRequirement(username, levelRequirement))
            throw new UserHasNoPermissionToAccessException("User's level is too low for that zone.");

        List<ZoneEntity> zoneEntities = zoneRepository.findByLevelLowerOrEqual(levelRequirement)
                .orElseThrow(() -> new ObjectNotFoundException("There is no levels within that level requirement."));

        // TODO get the exact zone when zone view is ready
        ZoneEntity zoneEntity = zoneEntities.get(0);

        ZoneWithEnemiesViewModel zoneViewModel = modelMapper.map(zoneEntity, ZoneWithEnemiesViewModel.class);
        zoneViewModel
                .setEnemies(zoneEntity
                        .getEnemies()
                        .stream()
                        .sorted(Comparator.comparingInt(EnemyEntity::getLevel))
                        .map(enemyEntity -> modelMapper.map(enemyEntity, EnemyViewModel.class))
                        .collect(Collectors.toList()));

        return zoneViewModel;
    }

    @Override
    public List<ZoneViewModel> getAllZones() {
        return zoneRepository.findAll()
                .stream()
                .map(zoneEntity -> modelMapper.map(zoneEntity, ZoneViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public void create(ZoneCreationServiceModel zoneModel) throws IOException {

        CloudinaryImage cloudinaryImage = cloudinaryService
                .setFolderName("zones")
                .upload(zoneModel.getImage());

        ZoneEntity zoneEntity = modelMapper.map(zoneModel, ZoneEntity.class);
        zoneEntity.setImageUrl(cloudinaryImage.getUrl());

        zoneRepository.save(zoneEntity);
    }

    @Override
    public boolean isZoneNameFree(String zoneName) {
        return !zoneRepository.existsByName(zoneName);
    }
}
