package game.destinyofthechosen.model.binding;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class ItemTransferBindingModel {

    @NotNull
    private UUID itemId;
    @NotNull
    private String heroName;

    public UUID getItemId() {
        return itemId;
    }

    public ItemTransferBindingModel setItemId(UUID itemId) {
        this.itemId = itemId;
        return this;
    }

    public String getHeroName() {
        return heroName;
    }

    public ItemTransferBindingModel setHeroName(String heroName) {
        this.heroName = heroName;
        return this;
    }
}
