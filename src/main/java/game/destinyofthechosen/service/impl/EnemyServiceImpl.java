package game.destinyofthechosen.service.impl;

import game.destinyofthechosen.exception.ObjectNotFoundException;
import game.destinyofthechosen.model.entity.DropListEntity;
import game.destinyofthechosen.model.entity.EnemyEntity;
import game.destinyofthechosen.model.entity.ZoneEntity;
import game.destinyofthechosen.model.service.CloudinaryImage;
import game.destinyofthechosen.model.service.EnemyCreationServiceModel;
import game.destinyofthechosen.model.view.EnemyViewModel;
import game.destinyofthechosen.repository.DropListRepository;
import game.destinyofthechosen.repository.EnemyRepository;
import game.destinyofthechosen.repository.ZoneRepository;
import game.destinyofthechosen.service.CloudinaryService;
import game.destinyofthechosen.service.EnemyService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class EnemyServiceImpl implements EnemyService {

    private final EnemyRepository enemyRepository;
    private final ZoneRepository zoneRepository;
    private final DropListRepository dropListRepository;
    private final CloudinaryService cloudinaryService;
    private final ModelMapper modelMapper;

    public EnemyServiceImpl(EnemyRepository enemyRepository, ZoneRepository zoneRepository, DropListRepository dropListRepository, CloudinaryService cloudinaryService, ModelMapper modelMapper) {
        this.enemyRepository = enemyRepository;
        this.zoneRepository = zoneRepository;
        this.dropListRepository = dropListRepository;
        this.cloudinaryService = cloudinaryService;
        this.modelMapper = modelMapper;
    }

    @Override
    public void createEnemy(EnemyCreationServiceModel enemyModel) throws IOException {

        CloudinaryImage cloudinaryImage = cloudinaryService
                .setFolderName("enemies")
                .upload(enemyModel.getImage());

        EnemyEntity enemyEntity = modelMapper.map(enemyModel, EnemyEntity.class);
        enemyEntity.setImageUrl(cloudinaryImage.getUrl());

        ZoneEntity zone = findZoneByName(enemyModel.getZoneName());

        enemyEntity.setZone(zone);
        enemyRepository.save(enemyEntity);

        makeDropListEntities(enemyModel.getDropList(), enemyEntity);
    }

    private void makeDropListEntities(List<UUID> dropList, EnemyEntity enemyEntity) {
        dropList
                .forEach(itemDropId -> dropListRepository
                        .save(
                                new DropListEntity()
                                        .setItemName(itemDropId)
                                        .setEnemy(enemyEntity)));
    }

    @Override
    public EnemyViewModel findById(UUID id) {
        EnemyEntity enemyEntity = enemyRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("There is no enemy with such id."));

        EnemyViewModel enemyView = modelMapper.map(enemyEntity,
                EnemyViewModel.class);

        enemyView
                .setZoneImageUrl(enemyEntity.getZone().getImageUrl())
                .setCurrentHealth(enemyView.getHealth());

        return enemyView;
    }

    @Override
    public boolean isTheNameFree(String name) {
        return !enemyRepository.existsByEnemyName(name);
    }

    @Override
    public void initialize() {
        if (enemyRepository.count() != 0) return;

        List<EnemyEntity> enemyEntities = List.of(
                // Forest enemies
                new EnemyEntity("Baby Boar", "https://res.cloudinary.com/felin/image/upload/v1638026395/DestinyOfTheChosen/enemies/BabyBoar.png",
                        1, 280, 190, 24, 18, 26, findZoneByName("Forest")),
                new EnemyEntity("Enraged Boar", "https://res.cloudinary.com/felin/image/upload/v1638026309/DestinyOfTheChosen/enemies/EnragedBoar.png",
                        2, 320, 240, 27, 22, 31, findZoneByName("Forest")),
                new EnemyEntity("Pack Wolf", "https://res.cloudinary.com/felin/image/upload/v1638026038/DestinyOfTheChosen/enemies/PackWolf.png",
                        3, 440, 210, 44, 34, 46, findZoneByName("Forest")),
                new EnemyEntity("Lone Wolf", "https://res.cloudinary.com/felin/image/upload/v1638025309/DestinyOfTheChosen/enemies/LoneWolf.png",
                        4, 560, 250, 51, 44, 57, findZoneByName("Forest")),
                // Jungle enemies
                new EnemyEntity("GrinusBeast", "https://res.cloudinary.com/felin/image/upload/v1638027533/DestinyOfTheChosen/enemies/GrinusBeast.png",
                        5, 720, 590, 45, 77, 95, findZoneByName("Jungle")),
                new EnemyEntity("Death Claw", "https://res.cloudinary.com/felin/image/upload/v1638029169/DestinyOfTheChosen/enemies/DeathClaw.png",
                        6, 840, 470, 65, 88, 115, findZoneByName("Jungle")),
                new EnemyEntity("Drake", "https://res.cloudinary.com/felin/image/upload/v1638030552/DestinyOfTheChosen/enemies/Drake.png",
                        7, 960, 510, 58, 112, 135, findZoneByName("Jungle")),
                new EnemyEntity("Jassau", "https://res.cloudinary.com/felin/image/upload/v1638030833/DestinyOfTheChosen/enemies/Jassau.png",
                        8, 1280, 450, 78, 134, 158, findZoneByName("Jungle")),
                new EnemyEntity("Meduntaag", "https://res.cloudinary.com/felin/image/upload/v1638031168/DestinyOfTheChosen/enemies/Meduntaag.png",
                        9, 1500, 630, 52, 152, 184, findZoneByName("Jungle")),
                // Bridge enemies
                new EnemyEntity("Saraquiel", "https://res.cloudinary.com/felin/image/upload/v1638032241/DestinyOfTheChosen/enemies/Saraquiel.png",
                        10, 1840, 580, 64, 201, 226, findZoneByName("The Bridge")),
                new EnemyEntity("Bormiel", "https://res.cloudinary.com/felin/image/upload/v1638031545/DestinyOfTheChosen/enemies/Bormiel.png",
                        11, 2060, 790, 52, 252, 286, findZoneByName("The Bridge")),
                new EnemyEntity("Tearney", "https://res.cloudinary.com/felin/image/upload/v1638033089/DestinyOfTheChosen/enemies/Tearney.png",
                        12, 2380, 895, 56, 297, 316, findZoneByName("The Bridge")),
                new EnemyEntity("Legolas", "https://res.cloudinary.com/felin/image/upload/v1638033089/DestinyOfTheChosen/enemies/Legolas.png",
                        13, 2600, 725, 136, 323, 356, findZoneByName("The Bridge")),
                new EnemyEntity("Orc Chief", "https://res.cloudinary.com/felin/image/upload/v1638034088/DestinyOfTheChosen/enemies/OrcChief.png",
                        14, 2820, 1120, 45, 365, 384, findZoneByName("The Bridge")),
                // Dark Pass enemies
                new EnemyEntity("Gnoll Warlord", "https://res.cloudinary.com/felin/image/upload/v1638034787/DestinyOfTheChosen/enemies/GnollWarlord.png",
                        15, 3140, 885, 78, 397, 432, findZoneByName("Dark Pass")),
                new EnemyEntity("Kaylessea", "https://res.cloudinary.com/felin/image/upload/v1638035448/DestinyOfTheChosen/enemies/Kaylessea.png",
                        16, 3360, 785, 148, 417, 492, findZoneByName("Dark Pass")),
                new EnemyEntity("Chaoshag", "https://res.cloudinary.com/felin/image/upload/v1638039280/DestinyOfTheChosen/enemies/Chaoshag.png",
                        17, 3580, 830, 95, 525, 580, findZoneByName("Dark Pass")),
                // Graveyard enemies
                new EnemyEntity("Ghoul", "https://res.cloudinary.com/felin/image/upload/v1638041948/DestinyOfTheChosen/enemies/Ghoull.png",
                        18, 3900, 1340, 86, 680, 730, findZoneByName("Graveyard")),
                new EnemyEntity("Trennaxath", "https://res.cloudinary.com/felin/image/upload/v1638042132/DestinyOfTheChosen/enemies/Trennaxath.png",
                        19, 5700, 1120, 210, 1200, 1400, findZoneByName("Graveyard"))
        );

        enemyRepository.saveAll(enemyEntities);
    }

    private ZoneEntity findZoneByName(String zoneName) {
        return zoneRepository
                .findByZoneName(zoneName)
                .orElseThrow(() -> new ObjectNotFoundException("There is no zone with such name."));
    }
}
