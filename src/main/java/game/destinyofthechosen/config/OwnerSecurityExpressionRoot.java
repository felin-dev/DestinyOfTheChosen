package game.destinyofthechosen.config;

import game.destinyofthechosen.exception.UserNotLoggedInException;
import game.destinyofthechosen.model.binding.HeroSelectBindingModel;
import game.destinyofthechosen.model.service.HeroSelectServiceModel;
import game.destinyofthechosen.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

public class OwnerSecurityExpressionRoot extends SecurityExpressionRoot implements
        MethodSecurityExpressionOperations {

    private final ModelMapper modelMapper = new ModelMapper();

    private UserService userService;
    private Object filterObject;
    private Object returnObject;
    /**
     * Creates a new instance
     *
     * @param authentication the {@link Authentication} to use. Cannot be null.
     */
    public OwnerSecurityExpressionRoot(Authentication authentication) {
        super(authentication);
    }

    public boolean ownsThisHero(HeroSelectBindingModel heroSelect) {
        if (heroSelect.getCurrentHeroId() == null) return false;

        return userService.ownsThisHero(currentUsername(),
                modelMapper.map(heroSelect, HeroSelectServiceModel.class));
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public String currentUsername() {
        Authentication auth = getAuthentication();

        if (auth.getPrincipal() instanceof UserDetails) return ((UserDetails)auth.getPrincipal()).getUsername();

        throw new UserNotLoggedInException("You must be logged in.");
    }

    @Override
    public void setFilterObject(Object filterObject) {
        this.filterObject = filterObject;
    }

    @Override
    public Object getFilterObject() {
        return filterObject;
    }

    @Override
    public void setReturnObject(Object returnObject) {
        this.returnObject = returnObject;
    }

    @Override
    public Object getReturnObject() {
        return returnObject;
    }

    @Override
    public Object getThis() {
        return this;
    }
}
