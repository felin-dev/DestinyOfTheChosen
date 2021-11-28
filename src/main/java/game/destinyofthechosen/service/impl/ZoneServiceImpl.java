package game.destinyofthechosen.service.impl;

import game.destinyofthechosen.exception.ObjectNotFoundException;
import game.destinyofthechosen.exception.UserHasNoPermissionToAccessException;
import game.destinyofthechosen.model.entity.EnemyEntity;
import game.destinyofthechosen.model.entity.ZoneEntity;
import game.destinyofthechosen.model.service.CloudinaryImage;
import game.destinyofthechosen.model.service.ZoneCreationServiceModel;
import game.destinyofthechosen.model.view.ZoneViewModel;
import game.destinyofthechosen.model.view.ZoneWithEnemiesViewModel;
import game.destinyofthechosen.repository.ZoneRepository;
import game.destinyofthechosen.service.CloudinaryService;
import game.destinyofthechosen.service.EnemyService;
import game.destinyofthechosen.service.HeroService;
import game.destinyofthechosen.service.ZoneService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ZoneServiceImpl implements ZoneService {

    private final ZoneRepository zoneRepository;
    private final HeroService heroService;
    private final EnemyService enemyService;
    private final CloudinaryService cloudinaryService;
    private final ModelMapper modelMapper;

    public ZoneServiceImpl(ZoneRepository zoneRepository, HeroService heroService, EnemyService enemyService, CloudinaryService cloudinaryService, ModelMapper modelMapper) {
        this.zoneRepository = zoneRepository;
        this.heroService = heroService;
        this.enemyService = enemyService;
        this.cloudinaryService = cloudinaryService;
        this.modelMapper = modelMapper;
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
    public List<ZoneViewModel> getZonesInHeroLevelRange(String username, Integer levelRequirement) {

        userIsOverTheLevelRequirement(username, levelRequirement);

        List<ZoneEntity> zoneEntities = zoneRepository.findByLevelLowerOrEqualOrdered(levelRequirement)
                .orElseThrow(() -> new ObjectNotFoundException("There is no levels within that level requirement."));

        return zoneEntities
                .stream()
                .map(zoneEntity -> modelMapper.map(zoneEntity, ZoneViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ZoneWithEnemiesViewModel getZoneById(String username, UUID id) {

        ZoneEntity zoneEntity = zoneRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("There is no zone with such id."));

        userIsOverTheLevelRequirement(username, zoneEntity.getLevelRequirement());

        ZoneWithEnemiesViewModel zoneViewModel = modelMapper.map(zoneEntity, ZoneWithEnemiesViewModel.class);
        zoneViewModel
                .setEnemies(zoneEntity
                        .getEnemies()
                        .stream()
                        .sorted(Comparator.comparingInt(EnemyEntity::getLevel))
                        .map(enemyEntity -> enemyService.findById(enemyEntity.getId()))
                        .collect(Collectors.toList()));

        return zoneViewModel;
    }

    @Override
    public List<ZoneViewModel> getAllZones() {
        return zoneRepository.findAll()
                .stream()
                .sorted(Comparator.comparingInt(ZoneEntity::getLevelRequirement))
                .map(zoneEntity -> modelMapper.map(zoneEntity, ZoneViewModel.class))
                .collect(Collectors.toList());
    }

    private void userIsOverTheLevelRequirement(String username, Integer levelRequirement) {
        if (!heroService.isOverTheLevelRequirement(username, levelRequirement))
            throw new UserHasNoPermissionToAccessException("User's level is too low for that zone.");
    }

    @Override
    public boolean isZoneNameFree(String zoneName) {
        return !zoneRepository.existsByZoneName(zoneName);
    }

    @Override
    public void initialize() {
        if (zoneRepository.count() != 0) return;

        List<ZoneEntity> zones = List.of(
                new ZoneEntity("Forest", "https://res.cloudinary.com/felin/image/upload/v1637008467/DestinyOfTheChosen/zones/Forest.jpg", 1),
                new ZoneEntity("Jungle", "https://res.cloudinary.com/felin/image/upload/v1637008467/DestinyOfTheChosen/zones/Jungle.jpg", 5),
                new ZoneEntity("The Bridge", "https://res.cloudinary.com/felin/image/upload/v1637008467/DestinyOfTheChosen/zones/TheBridge.jpg", 10),
                new ZoneEntity("Dark Pass", "https://res.cloudinary.com/felin/image/upload/v1637008467/DestinyOfTheChosen/zones/DarkPass.jpg", 15),
                new ZoneEntity("Graveyard", "https://res.cloudinary.com/felin/image/upload/v1637008467/DestinyOfTheChosen/zones/Graveyard.jpg", 18)
        );

        zoneRepository.saveAll(zones);
    }
}
