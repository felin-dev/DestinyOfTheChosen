package game.destinyofthechosen.service.impl;

import game.destinyofthechosen.exception.ObjectNotFoundException;
import game.destinyofthechosen.model.entity.DropListEntity;
import game.destinyofthechosen.model.entity.EnemyEntity;
import game.destinyofthechosen.model.entity.ZoneEntity;
import game.destinyofthechosen.model.enumeration.ItemNameEnum;
import game.destinyofthechosen.model.service.CloudinaryImage;
import game.destinyofthechosen.model.service.EnemyCreationServiceModel;
import game.destinyofthechosen.repository.DropListRepository;
import game.destinyofthechosen.repository.EnemyRepository;
import game.destinyofthechosen.repository.ZoneRepository;
import game.destinyofthechosen.service.CloudinaryService;
import game.destinyofthechosen.service.EnemyService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

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

        ZoneEntity zone = zoneRepository
                .findByName(enemyModel.getZoneName())
                .orElseThrow(() -> new ObjectNotFoundException("There is no zone with such name."));

        enemyEntity.setZone(zone);
        enemyRepository.save(enemyEntity);

        makeDropListEntities(enemyModel.getDropList(), enemyEntity);
    }

    private void makeDropListEntities(List<ItemNameEnum> dropList, EnemyEntity enemyEntity) {
        dropList
                .forEach(dropItem -> dropListRepository
                        .save(
                                new DropListEntity()
                                        .setItemName(dropItem)
                                        .setEnemy(enemyEntity)));
    }

    @Override
    public boolean isTheNameFree(String name) {
        return !enemyRepository.existsByName(name);
    }
}
