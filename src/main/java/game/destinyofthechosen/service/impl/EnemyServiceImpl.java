package game.destinyofthechosen.service.impl;

import game.destinyofthechosen.exception.ObjectNotFoundException;
import game.destinyofthechosen.model.entity.*;
import game.destinyofthechosen.model.service.CloudinaryImage;
import game.destinyofthechosen.model.service.EnemyCreationServiceModel;
import game.destinyofthechosen.model.view.EnemyViewModel;
import game.destinyofthechosen.model.view.ItemViewModel;
import game.destinyofthechosen.repository.DropListRepository;
import game.destinyofthechosen.repository.EnemyRepository;
import game.destinyofthechosen.repository.ZoneRepository;
import game.destinyofthechosen.service.CloudinaryService;
import game.destinyofthechosen.service.EnemyService;
import game.destinyofthechosen.service.ItemService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EnemyServiceImpl implements EnemyService {

    private final ItemService itemService;
    private final EnemyRepository enemyRepository;
    private final ZoneRepository zoneRepository;
    private final DropListRepository dropListRepository;
    private final CloudinaryService cloudinaryService;
    private final ModelMapper modelMapper;

    public EnemyServiceImpl(ItemService itemService, EnemyRepository enemyRepository, ZoneRepository zoneRepository, DropListRepository dropListRepository, CloudinaryService cloudinaryService, ModelMapper modelMapper) {
        this.itemService = itemService;
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
                        .save(new DropListEntity()
                                .setItemId(itemDropId)
                                .setEnemy(enemyEntity)));
    }

    @Override
    @Transactional
    public EnemyViewModel findById(UUID id) {
        EnemyEntity enemyEntity = enemyRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("There is no enemy with such id."));

        EnemyViewModel enemyView = modelMapper.map(enemyEntity,
                EnemyViewModel.class);

        enemyView
                .setZoneName(enemyEntity.getZone().getZoneName())
                .setZoneLevelRequirement(enemyEntity.getZone().getLevelRequirement())
                .setZoneImageUrl(enemyEntity.getZone().getImageUrl())
                .setCurrentHealth(enemyView.getHealth())
                .setDropList(enemyEntity
                        .getDropList()
                        .stream()
                        .map(dropListEntity -> {
                            ItemEntity itemEntity = itemService.getItemById(dropListEntity.getItemId());

                            ItemViewModel itemView = modelMapper.map(itemEntity, ItemViewModel.class);
                            itemView.setStats(itemEntity
                                    .getStats()
                                    .stream()
                                    .collect(Collectors.toMap(stat -> stat.getStat().getType(), StatEntity::getValue)));

                            return itemView;
                        })
                        .collect(Collectors.toList())
                );

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
                        1, 280, 290, 24, 18, 26, findZoneByName("Forest")),
                new EnemyEntity("Enraged Boar", "https://res.cloudinary.com/felin/image/upload/v1638026309/DestinyOfTheChosen/enemies/EnragedBoar.png",
                        2, 440, 550, 47, 22, 31, findZoneByName("Forest")),
                new EnemyEntity("Pack Wolf", "https://res.cloudinary.com/felin/image/upload/v1638026038/DestinyOfTheChosen/enemies/PackWolf.png",
                        3, 790, 940, 54, 34, 46, findZoneByName("Forest")),
                new EnemyEntity("Lone Wolf", "https://res.cloudinary.com/felin/image/upload/v1638025309/DestinyOfTheChosen/enemies/LoneWolf.png",
                        4, 960, 1370, 77, 44, 57, findZoneByName("Forest")),
                // Jungle enemies
                new EnemyEntity("GrinusBeast", "https://res.cloudinary.com/felin/image/upload/v1638027533/DestinyOfTheChosen/enemies/GrinusBeast.png",
                        5, 1520, 2090, 105, 77, 95, findZoneByName("Jungle")),
                new EnemyEntity("Death Claw", "https://res.cloudinary.com/felin/image/upload/v1638029169/DestinyOfTheChosen/enemies/DeathClaw.png",
                        6, 2040, 3130, 125, 88, 115, findZoneByName("Jungle")),
                new EnemyEntity("Drake", "https://res.cloudinary.com/felin/image/upload/v1638030552/DestinyOfTheChosen/enemies/Drake.png",
                        7, 3560, 3510, 110, 112, 135, findZoneByName("Jungle")),
                new EnemyEntity("Jassau", "https://res.cloudinary.com/felin/image/upload/v1638030833/DestinyOfTheChosen/enemies/Jassau.png",
                        8, 4680, 3550, 148, 134, 158, findZoneByName("Jungle")),
                new EnemyEntity("Meduntaag", "https://res.cloudinary.com/felin/image/upload/v1638031168/DestinyOfTheChosen/enemies/Meduntaag.png",
                        9, 6100, 4020, 182, 152, 184, findZoneByName("Jungle")),
                // Bridge enemies
                new EnemyEntity("Saraquiel", "https://res.cloudinary.com/felin/image/upload/v1638032241/DestinyOfTheChosen/enemies/Saraquiel.png",
                        10, 7840, 8980, 204, 201, 226, findZoneByName("The Bridge")),
                new EnemyEntity("Bormiel", "https://res.cloudinary.com/felin/image/upload/v1638031545/DestinyOfTheChosen/enemies/Bormiel.png",
                        11, 9560, 9190, 222, 252, 286, findZoneByName("The Bridge")),
                new EnemyEntity("Tearney", "https://res.cloudinary.com/felin/image/upload/v1638033089/DestinyOfTheChosen/enemies/Tearney.png",
                        12, 12980, 9595, 256, 297, 316, findZoneByName("The Bridge")),
                new EnemyEntity("Legolas", "https://res.cloudinary.com/felin/image/upload/v1638033089/DestinyOfTheChosen/enemies/Legolas.png",
                        13, 17100, 9525, 306, 323, 356, findZoneByName("The Bridge")),
                new EnemyEntity("Orc Chief", "https://res.cloudinary.com/felin/image/upload/v1638034088/DestinyOfTheChosen/enemies/OrcChief.png",
                        14, 25620, 12110, 305, 365, 384, findZoneByName("The Bridge")),
                // Dark Pass enemies
                new EnemyEntity("Gnoll Warlord", "https://res.cloudinary.com/felin/image/upload/v1638034787/DestinyOfTheChosen/enemies/GnollWarlord.png",
                        15, 33140, 12885, 318, 397, 432, findZoneByName("Dark Pass")),
                new EnemyEntity("Kaylessea", "https://res.cloudinary.com/felin/image/upload/v1638035448/DestinyOfTheChosen/enemies/Kaylessea.png",
                        16, 44860, 17885, 448, 417, 492, findZoneByName("Dark Pass")),
                new EnemyEntity("Chaoshag", "https://res.cloudinary.com/felin/image/upload/v1638039280/DestinyOfTheChosen/enemies/Chaoshag.png",
                        17, 55980, 22930, 455, 525, 580, findZoneByName("Dark Pass")),
                // Graveyard enemies
                new EnemyEntity("Ghoul", "https://res.cloudinary.com/felin/image/upload/v1638041948/DestinyOfTheChosen/enemies/Ghoull.png",
                        18, 88900, 22140, 620, 680, 730, findZoneByName("Graveyard")),
                new EnemyEntity("Trennaxath", "https://res.cloudinary.com/felin/image/upload/v1638042132/DestinyOfTheChosen/enemies/Trennaxath.png",
                        19, 221700, 65320, 340, 1200, 1400, findZoneByName("Graveyard"))
        );

        enemyRepository.saveAll(enemyEntities);

        enemyEntities.get(2).setDropList(getDropListForLevel(enemyEntities.get(2)));
        enemyEntities.get(8).setDropList(getDropListForLevel(enemyEntities.get(8)));
        enemyEntities.get(14).setDropList(getDropListForLevel(enemyEntities.get(14)));
        enemyEntities.get(17).setDropList(getDropListForLevel(enemyEntities.get(17)));
    }

    private List<DropListEntity> getDropListForLevel(EnemyEntity enemyEntity) {
        return itemService.getItemsFromLevelRequirement(enemyEntity.getLevel())
                .stream()
                .map(itemEntity -> {
                    DropListEntity itemDrop = new DropListEntity()
                            .setEnemy(enemyEntity)
                            .setItemId(itemEntity.getId());
                    dropListRepository
                            .save(itemDrop);
                    return itemDrop;
                })
                .collect(Collectors.toList());
    }

    private ZoneEntity findZoneByName(String zoneName) {
        return zoneRepository
                .findByZoneName(zoneName)
                .orElseThrow(() -> new ObjectNotFoundException("There is no zone with such name."));
    }
}
