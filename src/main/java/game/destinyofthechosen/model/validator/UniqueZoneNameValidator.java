package game.destinyofthechosen.model.validator;

import game.destinyofthechosen.service.ZoneService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueZoneNameValidator implements ConstraintValidator<UniqueZoneName, String> {

    private final ZoneService zoneService;

    public UniqueZoneNameValidator(ZoneService zoneService) {
        this.zoneService = zoneService;
    }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {

        if (name == null) return false;

        return zoneService.isZoneNameFree(name);
    }
}
