package game.destinyofthechosen.model.binding;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class HeroSelectBindingModel {

    @NotNull
    private UUID id;

    public UUID getCurrentHeroId() {
        return id;
    }

    public HeroSelectBindingModel setCurrentHeroId(UUID currentHeroId) {
        this.id = currentHeroId;
        return this;
    }
}
