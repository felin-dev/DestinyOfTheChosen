package game.destinyofthechosen.service.impl;

import game.destinyofthechosen.model.entity.ItemEntity;
import game.destinyofthechosen.model.entity.StatEntity;
import game.destinyofthechosen.model.service.CloudinaryImage;
import game.destinyofthechosen.model.service.ItemCreationServiceModel;
import game.destinyofthechosen.model.view.ItemViewModel;
import game.destinyofthechosen.repository.ItemRepository;
import game.destinyofthechosen.repository.StatRepository;
import game.destinyofthechosen.service.CloudinaryService;
import game.destinyofthechosen.service.ItemService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final StatRepository statRepository;
    private final CloudinaryService cloudinaryService;
    private final ModelMapper modelMapper;

    public ItemServiceImpl(ItemRepository itemRepository, StatRepository statRepository, CloudinaryService cloudinaryService, ModelMapper modelMapper) {
        this.itemRepository = itemRepository;
        this.statRepository = statRepository;
        this.cloudinaryService = cloudinaryService;
        this.modelMapper = modelMapper;
    }

    @Override
    public void create(ItemCreationServiceModel itemModel) throws IOException {
        cloudinaryService.setFolderName("Items/" +
                (itemModel.getType().getName().equals("Staff") ? "Staves" : itemModel.getType().getName() + "s"));
        CloudinaryImage cloudinaryImage = cloudinaryService.upload(itemModel.getImage());

        ItemEntity itemEntity = modelMapper.map(itemModel, ItemEntity.class);
        itemEntity
                .setImageUrl(cloudinaryImage.getUrl());

        itemRepository.save(itemEntity);

        List<StatEntity> stats = itemModel
                .getStats()
                .entrySet()
                .stream()
                .filter(stat -> stat.getValue() > 0)
                .map(stat -> new StatEntity().setStat(stat.getKey()).setValue(stat.getValue()))
                .collect(Collectors.toList());

        statRepository.saveAll(stats);
    }

    @Override
    public List<ItemViewModel> getAllItems() {
        return itemRepository
                .findAllOrderByTypeThenByLevelRequirement()
                .stream()
                .map(itemEntity -> modelMapper.map(itemEntity, ItemViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public boolean isItemNameFree(String itemName) {
        return !itemRepository.existsByItemName(itemName);
    }
}