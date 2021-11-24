package game.destinyofthechosen.service;

import game.destinyofthechosen.model.service.ItemCreationServiceModel;
import game.destinyofthechosen.model.view.ItemViewModel;

import java.io.IOException;
import java.util.List;

public interface ItemService {

    List<ItemViewModel> getAllItems();

    void create(ItemCreationServiceModel map) throws IOException;

    boolean isItemNameFree(String itemName);
}
