package game.destinyofthechosen.service.impl;

import game.destinyofthechosen.model.entity.EnemyEntity;
import game.destinyofthechosen.model.service.CloudinaryImage;
import game.destinyofthechosen.model.service.EnemyCreationServiceModel;
import game.destinyofthechosen.repository.DropListRepository;
import game.destinyofthechosen.repository.EnemyRepository;
import game.destinyofthechosen.service.CloudinaryService;
import game.destinyofthechosen.service.EnemyService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EnemyServiceImpl implements EnemyService {

    private final EnemyRepository enemyRepository;
    private final DropListRepository dropListRepository;
    private final CloudinaryService cloudinaryService;
    private final ModelMapper modelMapper;

    public EnemyServiceImpl(EnemyRepository enemyRepository, DropListRepository dropListRepository, CloudinaryService cloudinaryService, ModelMapper modelMapper) {
        this.enemyRepository = enemyRepository;
        this.dropListRepository = dropListRepository;
        this.cloudinaryService = cloudinaryService;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean isTheNameFree(String name) {
        return !enemyRepository.existsByName(name);
    }

    @Override
    public void createEnemy(EnemyCreationServiceModel enemyModel) throws IOException {

        if (enemyRepository.existsByName(enemyModel.getName())) {
            throw new IllegalArgumentException("Enemy name is already taken.");
        }

        CloudinaryImage cloudinaryImage = cloudinaryService
                .setFolderName("enemies")
                .upload(enemyModel.getImage());

        EnemyEntity enemyEntity = modelMapper.map(enemyModel, EnemyEntity.class);
        enemyEntity.setImageUrl(cloudinaryImage.getUrl());
        enemyEntity.setDropList(dropListRepository.findAllByName(enemyModel.getDropList()));

        // TODO save entity, add items and make image entity so the image can be deleted

        System.out.println(enemyEntity);
        System.out.println(enemyModel);
//        System.out.println(enemyModel.getImage().isEmpty());
    }
}
