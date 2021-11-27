package game.destinyofthechosen.service.impl;

import game.destinyofthechosen.model.entity.ItemEntity;
import game.destinyofthechosen.model.entity.StatEntity;
import game.destinyofthechosen.model.enumeration.ItemTypeEnum;
import game.destinyofthechosen.model.enumeration.StatEnum;
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
                .map(stat -> new StatEntity()
                        .setStat(stat.getKey())
                        .setValue(stat.getValue())
                        .setItem(itemEntity))
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

    @Override
    public void initialize() {
        if (itemRepository.count() != 0) return;

        List<ItemEntity> items = List.of(
                // Swords
                new ItemEntity("Broadsword", ItemTypeEnum.SWORD, "https://res.cloudinary.com/felin/image/upload/v1637786913/DestinyOfTheChosen/Items/Swords/Broadsword.png", 3),
                new ItemEntity("Swift Blade", ItemTypeEnum.SWORD, "https://res.cloudinary.com/felin/image/upload/v1637786913/DestinyOfTheChosen/Items/Swords/SwiftBlade.png", 9),
                new ItemEntity("Doom Blade", ItemTypeEnum.SWORD, "https://res.cloudinary.com/felin/image/upload/v1637786913/DestinyOfTheChosen/Items/Swords/DoomBlade.png", 18),
                // Bows
                new ItemEntity("Composite Bow", ItemTypeEnum.BOW, "https://res.cloudinary.com/felin/image/upload/v1637786913/DestinyOfTheChosen/Items/Swords/CompositeBow.png", 3),
                new ItemEntity("Maple Bow", ItemTypeEnum.BOW, "https://res.cloudinary.com/felin/image/upload/v1637786913/DestinyOfTheChosen/Items/Swords/MapleBow.png", 9),
                new ItemEntity("Crossfire", ItemTypeEnum.BOW, "https://res.cloudinary.com/felin/image/upload/v1637786913/DestinyOfTheChosen/Items/Swords/Crossfire.png", 18),
                // Staves
                new ItemEntity("Ash Wood Scepter", ItemTypeEnum.STAFF, "https://res.cloudinary.com/felin/image/upload/v1637786913/DestinyOfTheChosen/Items/Swords/AshWoodScepter.png", 3),
                new ItemEntity("War Staff", ItemTypeEnum.STAFF, "https://res.cloudinary.com/felin/image/upload/v1637786913/DestinyOfTheChosen/Items/Swords/WarStaff.png", 9),
                new ItemEntity("Staff of Fate", ItemTypeEnum.STAFF, "https://res.cloudinary.com/felin/image/upload/v1637786913/DestinyOfTheChosen/Items/Swords/StaffOfFate.png", 18)

        );

        itemRepository.saveAll(items);

        List<StatEntity> stats = List.of(
                // Swords' stats
                // Broadsword's stats
                new StatEntity()
                        .setStat(StatEnum.ATTACK)
                        .setValue(6)
                        .setItem(items.get(0)),
                new StatEntity()
                        .setStat(StatEnum.DEFENSE)
                        .setValue(4)
                        .setItem(items.get(0)),
                new StatEntity()
                        .setStat(StatEnum.STRENGTH)
                        .setValue(1)
                        .setItem(items.get(0)),
                new StatEntity()
                        .setStat(StatEnum.VITALITY)
                        .setValue(2)
                        .setItem(items.get(0)),
                // Swift Blade's stats
                new StatEntity()
                        .setStat(StatEnum.ATTACK)
                        .setValue(9)
                        .setItem(items.get(1)),
                new StatEntity()
                        .setStat(StatEnum.DEFENSE)
                        .setValue(7)
                        .setItem(items.get(1)),
                new StatEntity()
                        .setStat(StatEnum.STRENGTH)
                        .setValue(3)
                        .setItem(items.get(1)),
                new StatEntity()
                        .setStat(StatEnum.ENERGY)
                        .setValue(3)
                        .setItem(items.get(1)),
                new StatEntity()
                        .setStat(StatEnum.VITALITY)
                        .setValue(5)
                        .setItem(items.get(1)),
                // Doom Blade's stats
                new StatEntity()
                        .setStat(StatEnum.ATTACK)
                        .setValue(35)
                        .setItem(items.get(2)),
                new StatEntity()
                        .setStat(StatEnum.DEFENSE)
                        .setValue(32)
                        .setItem(items.get(2)),
                new StatEntity()
                        .setStat(StatEnum.STRENGTH)
                        .setValue(15)
                        .setItem(items.get(2)),
                new StatEntity()
                        .setStat(StatEnum.DEXTERITY)
                        .setValue(7)
                        .setItem(items.get(2)),
                new StatEntity()
                        .setStat(StatEnum.ENERGY)
                        .setValue(15)
                        .setItem(items.get(2)),
                new StatEntity()
                        .setStat(StatEnum.VITALITY)
                        .setValue(25)
                        .setItem(items.get(2)),
                // Bows' stats
                // Composite Bow's stats
                new StatEntity()
                        .setStat(StatEnum.ATTACK)
                        .setValue(14)
                        .setItem(items.get(3)),
                new StatEntity()
                        .setStat(StatEnum.DEXTERITY)
                        .setValue(2)
                        .setItem(items.get(3)),
                new StatEntity()
                        .setStat(StatEnum.ENERGY)
                        .setValue(1)
                        .setItem(items.get(3)),
                // Maple Bow's stats
                new StatEntity()
                        .setStat(StatEnum.ATTACK)
                        .setValue(23)
                        .setItem(items.get(4)),
                new StatEntity()
                        .setStat(StatEnum.DEXTERITY)
                        .setValue(6)
                        .setItem(items.get(4)),
                new StatEntity()
                        .setStat(StatEnum.ENERGY)
                        .setValue(4)
                        .setItem(items.get(4)),
                new StatEntity()
                        .setStat(StatEnum.VITALITY)
                        .setValue(1)
                        .setItem(items.get(4)),
                // Crossfire's stats
                new StatEntity()
                        .setStat(StatEnum.ATTACK)
                        .setValue(99)
                        .setItem(items.get(5)),
                new StatEntity()
                        .setStat(StatEnum.DEXTERITY)
                        .setValue(33)
                        .setItem(items.get(5)),
                new StatEntity()
                        .setStat(StatEnum.STRENGTH)
                        .setValue(15)
                        .setItem(items.get(5)),
                new StatEntity()
                        .setStat(StatEnum.ENERGY)
                        .setValue(7)
                        .setItem(items.get(5)),
                new StatEntity()
                        .setStat(StatEnum.VITALITY)
                        .setValue(7)
                        .setItem(items.get(5)),
                // Staves' stats
                // Ash Wood Scepter's stats
                new StatEntity()
                        .setStat(StatEnum.ATTACK)
                        .setValue(4)
                        .setItem(items.get(6)),
                new StatEntity()
                        .setStat(StatEnum.MAGIC_POWER)
                        .setValue(10)
                        .setItem(items.get(6)),
                new StatEntity()
                        .setStat(StatEnum.ENERGY)
                        .setValue(3)
                        .setItem(items.get(6)),
                // War Staff's stats
                new StatEntity()
                        .setStat(StatEnum.ATTACK)
                        .setValue(6)
                        .setItem(items.get(7)),
                new StatEntity()
                        .setStat(StatEnum.MAGIC_POWER)
                        .setValue(17)
                        .setItem(items.get(7)),
                new StatEntity()
                        .setStat(StatEnum.ENERGY)
                        .setValue(8)
                        .setItem(items.get(7)),
                new StatEntity()
                        .setStat(StatEnum.STRENGTH)
                        .setValue(1)
                        .setItem(items.get(7)),
                new StatEntity()
                        .setStat(StatEnum.DEXTERITY)
                        .setValue(1)
                        .setItem(items.get(7)),
                new StatEntity()
                        .setStat(StatEnum.VITALITY)
                        .setValue(1)
                        .setItem(items.get(7)),
                // Staff of Fate
                new StatEntity()
                        .setStat(StatEnum.ATTACK)
                        .setValue(33)
                        .setItem(items.get(8)),
                new StatEntity()
                        .setStat(StatEnum.MAGIC_POWER)
                        .setValue(66)
                        .setItem(items.get(8)),
                new StatEntity()
                        .setStat(StatEnum.ENERGY)
                        .setValue(41)
                        .setItem(items.get(8)),
                new StatEntity()
                        .setStat(StatEnum.STRENGTH)
                        .setValue(7)
                        .setItem(items.get(8)),
                new StatEntity()
                        .setStat(StatEnum.DEXTERITY)
                        .setValue(7)
                        .setItem(items.get(8)),
                new StatEntity()
                        .setStat(StatEnum.VITALITY)
                        .setValue(7)
                        .setItem(items.get(8))
        );

        statRepository.saveAll(stats);
    }
}
