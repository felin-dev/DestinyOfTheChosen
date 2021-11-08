package game.destinyofthechosen.web;

import game.destinyofthechosen.model.binding.EnemyCreationBindingModel;
import game.destinyofthechosen.model.enumeration.ItemNameEnum;
import game.destinyofthechosen.model.service.EnemyCreationServiceModel;
import game.destinyofthechosen.service.EnemyService;
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
    private final ModelMapper modelMapper;

    public AdminController(EnemyService enemyService, ModelMapper modelMapper) {
        this.enemyService = enemyService;
        this.modelMapper = modelMapper;
    }

    @ModelAttribute("enemyCreationBindingModel")
    public EnemyCreationBindingModel enemyCreationBindingModel() {
        return new EnemyCreationBindingModel();
    }

    @ModelAttribute("enemyNameIsTaken")
    public Boolean enemyNameIsTaken() {
        return false;
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

        return "enemy-creation";
    }

    @PostMapping("/admin/enemies/create")
    public String createEnemyConfirm(@Valid EnemyCreationBindingModel enemyCreationBindingModel,
                                     BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes
                    .addFlashAttribute("enemyCreationBindingModel", enemyCreationBindingModel)
                    .addFlashAttribute("org.springframework.validation.BindingResult.enemyCreationBindingModel", bindingResult);

            return "redirect:/admin/enemies/create";
        }

        if (enemyCreationBindingModel.getGoldDropLowerThreshold() >= enemyCreationBindingModel.getGoldDropUpperThreshold()) {
            redirectAttributes
                    .addFlashAttribute("enemyCreationBindingModel", enemyCreationBindingModel)
                    .addFlashAttribute("goldLowerThresholdBiggerOrEqualThanUpperThreshold", true);

            return "redirect:/admin/enemies/create";
        }

        try {
            enemyService.createEnemy(modelMapper.map(enemyCreationBindingModel, EnemyCreationServiceModel.class));
        } catch (IllegalArgumentException usernameOrEmailIsTaken) {
            redirectAttributes
                    .addFlashAttribute("enemyCreationBindingModel", enemyCreationBindingModel)
                    .addFlashAttribute("enemyNameIsTaken", true);

            return "redirect:/admin/enemies/create";
        } catch (IOException imageNotSupported) {
            redirectAttributes
                    .addFlashAttribute("enemyCreationBindingModel", enemyCreationBindingModel)
                    .addFlashAttribute("imageNotSupported", true);

            return "redirect:/admin/enemies/create";
        }

        return "redirect:/home";
    }
}
