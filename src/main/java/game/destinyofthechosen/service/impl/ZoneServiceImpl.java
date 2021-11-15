package game.destinyofthechosen.service.impl;

import game.destinyofthechosen.model.entity.ZoneEntity;
import game.destinyofthechosen.model.service.CloudinaryImage;
import game.destinyofthechosen.model.service.ZoneCreationServiceModel;
import game.destinyofthechosen.model.view.ZoneViewModel;
import game.destinyofthechosen.repository.ZoneRepository;
import game.destinyofthechosen.service.CloudinaryService;
import game.destinyofthechosen.service.ZoneService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ZoneServiceImpl implements ZoneService {

    private final ZoneRepository zoneRepository;
    private final CloudinaryService cloudinaryService;
    private final ModelMapper modelMapper;

    public ZoneServiceImpl(ZoneRepository zoneRepository, CloudinaryService cloudinaryService, ModelMapper modelMapper) {
        this.zoneRepository = zoneRepository;
        this.cloudinaryService = cloudinaryService;
        this.modelMapper = modelMapper;
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
