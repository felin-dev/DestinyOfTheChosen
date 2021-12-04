package game.destinyofthechosen.service.impl;

import game.destinyofthechosen.exception.ObjectNotFoundException;
import game.destinyofthechosen.model.entity.ItemEntity;
import game.destinyofthechosen.model.entity.StatEntity;
import game.destinyofthechosen.model.enumeration.HeroRoleEnum;
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
import java.util.Map;
import java.util.UUID;
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
    public List<ItemEntity> getItemsFromLevelRequirement(Integer itemsLevel) {
        return itemRepository.getAllByLevelRequirement(itemsLevel);
    }

    @Override
    public ItemEntity getItemById(UUID itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Item with id: " + itemId + " is does not exist."));
    }

    @Override
    public ItemViewModel getItemViewById(UUID itemId) {

        ItemEntity itemEntity = itemRepository.findById(itemId)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("There is no item with id: %s.", itemId)));

        ItemViewModel itemView = modelMapper.map(itemEntity, ItemViewModel.class);
        ItemTypeEnum type = itemEntity.getType();
        switch (type) {
            case SWORD -> itemView.setHeroRole(HeroRoleEnum.WARRIOR);
            case BOW -> itemView.setHeroRole(HeroRoleEnum.HUNTER);
            case STAFF -> itemView.setHeroRole(HeroRoleEnum.MAGE);
        }

        Map<String, Integer> stats = itemEntity.getStats()
                .stream()
                .collect(Collectors.toMap(stat -> stat.getStat().getType(), StatEntity::getValue));

        itemView.setStats(stats);

        return itemView;
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
    public boolean isItemNameFree(String itemName) {
        return !itemRepository.existsByItemName(itemName);
    }

    @Override
    public void initialize() {
        if (itemRepository.count() != 0) return;

        List<ItemEntity> items = List.of(
                // Swords
                new ItemEntity("Broad Sword", ItemTypeEnum.SWORD, "https://res.cloudinary.com/felin/image/upload/v1638468172/DestinyOfTheChosen/Items/Swords/BroadSword.png", 3),
                new ItemEntity("Deathraze", ItemTypeEnum.SWORD, "https://res.cloudinary.com/felin/image/upload/v1638468194/DestinyOfTheChosen/Items/Swords/Deathraze.png", 9),
                new ItemEntity("Swift Blade", ItemTypeEnum.SWORD, "https://res.cloudinary.com/felin/image/upload/v1638468235/DestinyOfTheChosen/Items/Swords/SwiftBlade.png", 15),
                new ItemEntity("Doom Blade", ItemTypeEnum.SWORD, "https://res.cloudinary.com/felin/image/upload/v1638468259/DestinyOfTheChosen/Items/Swords/DoomBlade.png", 18),
                // Bows
                new ItemEntity("Composite Bow", ItemTypeEnum.BOW, "https://res.cloudinary.com/felin/image/upload/v1638468378/DestinyOfTheChosen/Items/Bows/CompositeBow.png", 3),
                new ItemEntity("Maple Bow", ItemTypeEnum.BOW, "https://res.cloudinary.com/felin/image/upload/v1638468462/DestinyOfTheChosen/Items/Bows/MapleBow.png", 9),
                new ItemEntity("Light Bow", ItemTypeEnum.BOW, "https://res.cloudinary.com/felin/image/upload/v1638468442/DestinyOfTheChosen/Items/Bows/LightBow.png", 15),
                new ItemEntity("Crossfire", ItemTypeEnum.BOW, "https://res.cloudinary.com/felin/image/upload/v1638468422/DestinyOfTheChosen/Items/Bows/Crossfire.png", 18),
                // Staves
                new ItemEntity("Ash Wood Scepter", ItemTypeEnum.STAFF, "https://res.cloudinary.com/felin/image/upload/v1638468560/DestinyOfTheChosen/Items/Staves/AshWoodScepter.png", 3),
                new ItemEntity("War Staff", ItemTypeEnum.STAFF, "https://res.cloudinary.com/felin/image/upload/v1638468522/DestinyOfTheChosen/Items/Staves/WarStaff.png", 9),
                new ItemEntity("Devotion", ItemTypeEnum.STAFF, "https://res.cloudinary.com/felin/image/upload/v1638468540/DestinyOfTheChosen/Items/Staves/Devotion.png", 15),
                new ItemEntity("Staff of Fate", ItemTypeEnum.STAFF, "https://res.cloudinary.com/felin/image/upload/v1638468507/DestinyOfTheChosen/Items/Staves/StaffOfFate.png", 18)

        );

        itemRepository.saveAll(items);

        List<StatEntity> stats = List.of(
                // Swords' stats
                // Broadsword's stats
                new StatEntity()
                        .setStat(StatEnum.ATTACK)
                        .setValue(11)
                        .setItem(items.get(0)),
                new StatEntity()
                        .setStat(StatEnum.DEFENSE)
                        .setValue(9)
                        .setItem(items.get(0)),
                new StatEntity()
                        .setStat(StatEnum.STRENGTH)
                        .setValue(6)
                        .setItem(items.get(0)),
                new StatEntity()
                        .setStat(StatEnum.VITALITY)
                        .setValue(7)
                        .setItem(items.get(0)),
                // Deathraze's stats
                new StatEntity()
                        .setStat(StatEnum.ATTACK)
                        .setValue(49)
                        .setItem(items.get(1)),
                new StatEntity()
                        .setStat(StatEnum.DEFENSE)
                        .setValue(27)
                        .setItem(items.get(1)),
                new StatEntity()
                        .setStat(StatEnum.STRENGTH)
                        .setValue(81)
                        .setItem(items.get(1)),
                new StatEntity()
                        .setStat(StatEnum.VITALITY)
                        .setValue(20)
                        .setItem(items.get(1)),
                // Swift Blade's
                new StatEntity()
                        .setStat(StatEnum.ATTACK)
                        .setValue(84)
                        .setItem(items.get(2)),
                new StatEntity()
                        .setStat(StatEnum.DEFENSE)
                        .setValue(54)
                        .setItem(items.get(2)),
                new StatEntity()
                        .setStat(StatEnum.STRENGTH)
                        .setValue(120)
                        .setItem(items.get(2)),
                new StatEntity()
                        .setStat(StatEnum.VITALITY)
                        .setValue(66)
                        .setItem(items.get(2)),
                // Doom Blade's stats
                new StatEntity()
                        .setStat(StatEnum.ATTACK)
                        .setValue(125)
                        .setItem(items.get(3)),
                new StatEntity()
                        .setStat(StatEnum.DEFENSE)
                        .setValue(112)
                        .setItem(items.get(3)),
                new StatEntity()
                        .setStat(StatEnum.STRENGTH)
                        .setValue(247)
                        .setItem(items.get(3)),
                new StatEntity()
                        .setStat(StatEnum.VITALITY)
                        .setValue(85)
                        .setItem(items.get(3)),
                // Bows' stats
                // Composite Bow's stats
                new StatEntity()
                        .setStat(StatEnum.ATTACK)
                        .setValue(34)
                        .setItem(items.get(4)),
                new StatEntity()
                        .setStat(StatEnum.DEXTERITY)
                        .setValue(7)
                        .setItem(items.get(4)),
                new StatEntity()
                        .setStat(StatEnum.VITALITY)
                        .setValue(6)
                        .setItem(items.get(4)),
                // Maple Bow's stats
                new StatEntity()
                        .setStat(StatEnum.ATTACK)
                        .setValue(123)
                        .setItem(items.get(5)),
                new StatEntity()
                        .setStat(StatEnum.DEXTERITY)
                        .setValue(85)
                        .setItem(items.get(5)),
                new StatEntity()
                        .setStat(StatEnum.VITALITY)
                        .setValue(16)
                        .setItem(items.get(5)),
                // Light Bow's stats
                new StatEntity()
                        .setStat(StatEnum.ATTACK)
                        .setValue(172)
                        .setItem(items.get(6)),
                new StatEntity()
                        .setStat(StatEnum.DEXTERITY)
                        .setValue(126)
                        .setItem(items.get(6)),
                new StatEntity()
                        .setStat(StatEnum.VITALITY)
                        .setValue(50)
                        .setItem(items.get(6)),
                // Crossfire's stats
                new StatEntity()
                        .setStat(StatEnum.ATTACK)
                        .setValue(349)
                        .setItem(items.get(7)),
                new StatEntity()
                        .setStat(StatEnum.DEXTERITY)
                        .setValue(255)
                        .setItem(items.get(7)),
                new StatEntity()
                        .setStat(StatEnum.VITALITY)
                        .setValue(77)
                        .setItem(items.get(7)),
                // Staves' stats
                // Ash Wood Scepter's stats
                new StatEntity()
                        .setStat(StatEnum.MAGIC_POWER)
                        .setValue(34)
                        .setItem(items.get(8)),
                new StatEntity()
                        .setStat(StatEnum.ENERGY)
                        .setValue(8)
                        .setItem(items.get(8)),
                new StatEntity()
                        .setStat(StatEnum.VITALITY)
                        .setValue(5)
                        .setItem(items.get(8)),
                // War Staff's stats
                new StatEntity()
                        .setStat(StatEnum.MAGIC_POWER)
                        .setValue(123)
                        .setItem(items.get(9)),
                new StatEntity()
                        .setStat(StatEnum.ENERGY)
                        .setValue(86)
                        .setItem(items.get(9)),
                new StatEntity()
                        .setStat(StatEnum.VITALITY)
                        .setValue(15)
                        .setItem(items.get(9)),
                // Devotion' stats
                new StatEntity()
                        .setStat(StatEnum.MAGIC_POWER)
                        .setValue(172)
                        .setItem(items.get(10)),
                new StatEntity()
                        .setStat(StatEnum.ENERGY)
                        .setValue(127)
                        .setItem(items.get(10)),
                new StatEntity()
                        .setStat(StatEnum.VITALITY)
                        .setValue(49)
                        .setItem(items.get(10)),
                // Staff of Fate
                new StatEntity()
                        .setStat(StatEnum.MAGIC_POWER)
                        .setValue(349)
                        .setItem(items.get(11)),
                new StatEntity()
                        .setStat(StatEnum.ENERGY)
                        .setValue(255)
                        .setItem(items.get(11)),
                new StatEntity()
                        .setStat(StatEnum.VITALITY)
                        .setValue(77)
                        .setItem(items.get(11))
        );

        statRepository.saveAll(stats);
    }
}
