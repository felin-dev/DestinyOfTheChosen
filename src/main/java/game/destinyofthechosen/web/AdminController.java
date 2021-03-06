package game.destinyofthechosen.web;

import game.destinyofthechosen.model.binding.EnemyCreationBindingModel;
import game.destinyofthechosen.model.binding.ItemCreationBindingModel;
import game.destinyofthechosen.model.binding.UsernameBindingModel;
import game.destinyofthechosen.model.binding.ZoneCreationBindingModel;
import game.destinyofthechosen.model.service.EnemyCreationServiceModel;
import game.destinyofthechosen.model.service.ItemCreationServiceModel;
import game.destinyofthechosen.model.service.UsernameServiceModel;
import game.destinyofthechosen.model.service.ZoneCreationServiceModel;
import game.destinyofthechosen.service.EnemyService;
import game.destinyofthechosen.service.ItemService;
import game.destinyofthechosen.service.UserService;
import game.destinyofthechosen.service.ZoneService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;

@Controller
public class AdminController {

    private final UserService userService;
    private final EnemyService enemyService;
    private final ZoneService zoneService;
    private final ItemService itemService;
    private final ModelMapper modelMapper;

    public AdminController(UserService userService, EnemyService enemyService, ZoneService zoneService, ItemService itemService, ModelMapper modelMapper) {
        this.userService = userService;
        this.enemyService = enemyService;
        this.zoneService = zoneService;
        this.itemService = itemService;
        this.modelMapper = modelMapper;
    }

    @ModelAttribute("usernameBindingModel")
    public UsernameBindingModel usernameBindingModel() {
        return new UsernameBindingModel();
    }

    @ModelAttribute("adminCreatedInfo")
    public String adminCreatedInfo() {
        return null;
    }

    @ModelAttribute("adminRemovedInfo")
    public String adminRemovedInfo() {
        return null;
    }

    @GetMapping("/admin/admin-control")
    public String adminControl() {

        return "admin-control";
    }

    @PostMapping("/admin/add-admin-role")
    public String adminControlMakeAdmin(Principal principal, @Valid UsernameBindingModel usernameBindingModel,
                                        BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes
                    .addFlashAttribute("usernameBindingModel", usernameBindingModel)
                    .addFlashAttribute("org.springframework.validation.BindingResult.usernameBindingModel", bindingResult);

            return "redirect:admin-control";
        }

        userService.makeUserAnAdmin(principal.getName(), modelMapper.map(usernameBindingModel, UsernameServiceModel.class));
        redirectAttributes
                .addFlashAttribute("adminCreatedInfo",
                        usernameBindingModel.getUsername() + " is now an admin.");

        return "redirect:admin-control";
    }

    @DeleteMapping("/admin/remove-admin-role")
    public String adminControlRemoveAdmin(Principal principal, @Valid UsernameBindingModel usernameBindingModel,
                                        BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes
                    .addFlashAttribute("usernameBindingModel", usernameBindingModel)
                    .addFlashAttribute("org.springframework.validation.BindingResult.usernameBindingModel", bindingResult);

            return "redirect:admin-control";
        }

        userService.removeAdminRoleFromUser(principal.getName(), modelMapper.map(usernameBindingModel, UsernameServiceModel.class));
        redirectAttributes
                .addFlashAttribute("adminRemovedInfo",
                        usernameBindingModel.getUsername() + " is no longer an admin.");

        return "redirect:admin-control";
    }

    @ModelAttribute("zoneCreationBindingModel")
    public ZoneCreationBindingModel zoneCreationBindingModel() {
        return new ZoneCreationBindingModel();
    }

    @GetMapping("/admin/zones/create")
    public String createZone() {

        return "zone-creation";
    }

    @PostMapping("/admin/zones/create")
    public String createZoneConfirm(@Valid ZoneCreationBindingModel zoneCreationBindingModel,
                                    BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes
                    .addFlashAttribute("zoneCreationBindingModel", zoneCreationBindingModel)
                    .addFlashAttribute("org.springframework.validation.BindingResult.zoneCreationBindingModel", bindingResult);

            return "redirect:create";
        }

        try {
        zoneService.create(modelMapper.map(zoneCreationBindingModel, ZoneCreationServiceModel.class));
        } catch (IOException imageNotSupported) {
            redirectAttributes
                    .addFlashAttribute("zoneCreationBindingModel", zoneCreationBindingModel)
                    .addFlashAttribute("imageNotSupported", true);

            return "redirect:create";
        }

        return "redirect:/home";
    }

    @ModelAttribute("itemCreationBindingModel")
    public ItemCreationBindingModel itemCreationBindingModel() {
        return new ItemCreationBindingModel();
    }

    @GetMapping("/admin/items/create")
    public String createItem() {

        return "item-creation";
    }

    @PostMapping("/admin/items/create")
    public String createItemConfirm(@Valid ItemCreationBindingModel itemCreationBindingModel,
                                    BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes
                    .addFlashAttribute("itemCreationBindingModel", itemCreationBindingModel)
                    .addFlashAttribute("org.springframework.validation.BindingResult.itemCreationBindingModel", bindingResult);

            return "redirect:create";
        }

        try {
            itemService.create(modelMapper.map(itemCreationBindingModel, ItemCreationServiceModel.class));
        } catch (IOException | RuntimeException imageNotSupported) {
            redirectAttributes
                    .addFlashAttribute("itemCreationBindingModel", itemCreationBindingModel)
                    .addFlashAttribute("imageNotSupported", true);

            return "redirect:create";
        }

        return "redirect:/home";
    }

    @ModelAttribute("enemyCreationBindingModel")
    public EnemyCreationBindingModel enemyCreationBindingModel() {
        return new EnemyCreationBindingModel();
    }

    @ModelAttribute("goldLowerThresholdBiggerOrEqualThanUpperThreshold")
    public Boolean goldLowerThresholdBiggerOrEqualThanUpperThreshold() {
        return false;
    }

    @ModelAttribute("imageNotSupported")
    public Boolean imageNotSupported() {
        return false;
    }

    @GetMapping("/admin/enemies/create")
    public String createEnemy(Model model) {

        model.addAttribute("items", itemService.getAllItems());
        model.addAttribute("zones", zoneService.getAllZones());

        return "enemy-creation";
    }

    @PostMapping("/admin/enemies/create")
    public String createEnemyConfirm(@Valid EnemyCreationBindingModel enemyCreationBindingModel,
                                     BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes
                    .addFlashAttribute("enemyCreationBindingModel", enemyCreationBindingModel)
                    .addFlashAttribute("org.springframework.validation.BindingResult.enemyCreationBindingModel", bindingResult);

            return "redirect:create";
        }

        if (enemyCreationBindingModel.getGoldDropLowerThreshold() >= enemyCreationBindingModel.getGoldDropUpperThreshold()) {
            redirectAttributes
                    .addFlashAttribute("enemyCreationBindingModel", enemyCreationBindingModel)
                    .addFlashAttribute("goldLowerThresholdBiggerOrEqualThanUpperThreshold", true);

            return "redirect:create";
        }

        try {
            enemyService.createEnemy(modelMapper.map(enemyCreationBindingModel, EnemyCreationServiceModel.class));
        } catch (IOException | RuntimeException imageNotSupported) {
            redirectAttributes
                    .addFlashAttribute("enemyCreationBindingModel", enemyCreationBindingModel)
                    .addFlashAttribute("imageNotSupported", true);

            return "redirect:create";
        }

        return "redirect:/home";
    }
}
