package game.destinyofthechosen.service;

import game.destinyofthechosen.model.entity.ItemEntity;
import game.destinyofthechosen.model.service.ItemCreationServiceModel;
import game.destinyofthechosen.model.view.ItemViewModel;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface ItemService {

    List<ItemEntity> getItemsFromLevelRequirement(Integer itemsLevel);

    ItemEntity getItemById(UUID itemId);

    List<ItemViewModel> getAllItems();

    void create(ItemCreationServiceModel map) throws IOException;

    boolean isItemNameFree(String itemName);

    void initialize();
}
