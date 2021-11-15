package game.destinyofthechosen.web;

import game.destinyofthechosen.model.binding.EnemyCreationBindingModel;
import game.destinyofthechosen.model.binding.ZoneCreationBindingModel;
import game.destinyofthechosen.model.enumeration.ItemNameEnum;
import game.destinyofthechosen.model.service.EnemyCreationServiceModel;
import game.destinyofthechosen.model.service.ZoneCreationServiceModel;
import game.destinyofthechosen.service.EnemyService;
import game.destinyofthechosen.service.ZoneService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;

@Controller
public class AdminController {

    private final EnemyService enemyService;
    private final ZoneService zoneService;
    private final ModelMapper modelMapper;

    public AdminController(EnemyService enemyService, ZoneService zoneService, ModelMapper modelMapper) {
        this.enemyService = enemyService;
        this.zoneService = zoneService;
        this.modelMapper = modelMapper;
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

        model.addAttribute("items", ItemNameEnum.values());
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
        } catch (IOException imageNotSupported) {
            redirectAttributes
                    .addFlashAttribute("enemyCreationBindingModel", enemyCreationBindingModel)
                    .addFlashAttribute("imageNotSupported", true);

            return "redirect:create";
        }

        return "redirect:/home";
    }
}
