package game.destinyofthechosen.model.validator;

import game.destinyofthechosen.service.ItemService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueItemNameValidator implements ConstraintValidator<UniqueItemName, String> {

    private final ItemService itemService;

    public UniqueItemNameValidator(ItemService itemService) {
        this.itemService = itemService;
    }

    @Override
    public boolean isValid(String itemName, ConstraintValidatorContext context) {
        if (itemName == null) return false;

        return itemService.isItemNameFree(itemName);
    }
}
