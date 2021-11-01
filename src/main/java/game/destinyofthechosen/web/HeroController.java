package game.destinyofthechosen.web;

import game.destinyofthechosen.model.binding.HeroCreationBindingModel;
import game.destinyofthechosen.model.service.HeroCreationServiceModel;
import game.destinyofthechosen.service.HeroService;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/heroes")
public class HeroController {

    private final HeroService heroService;
    private final ModelMapper modelMapper;

    public HeroController(HeroService heroService, ModelMapper modelMapper) {
        this.heroService = heroService;
        this.modelMapper = modelMapper;
    }

    @ModelAttribute("heroNameIsTaken")
    public Boolean heroNameIsTaken() {
        return false;
    }

    @ModelAttribute("heroCreationBindingModel")
    public HeroCreationBindingModel heroCreationBindingModel() {
        return new HeroCreationBindingModel();
    }

    @GetMapping("/create")
    public String createHero() {
        return "hero-creation";
    }

    @PostMapping("/create")
    public String createHeroConfirm(@Valid HeroCreationBindingModel heroCreationBindingModel,
                                    BindingResult bindingResult, RedirectAttributes redirectAttributes,
                                    Authentication authentication) {

        if (bindingResult.hasErrors()) {
            redirectAttributes
                    .addFlashAttribute("heroCreationBindingModel", heroCreationBindingModel)
                    .addFlashAttribute("org.springframework.validation.BindingResult.heroCreationBindingModel", bindingResult);

            return "redirect:createHero";
        }

        try {
            heroService.createNewHero(
                    modelMapper.map(heroCreationBindingModel, HeroCreationServiceModel.class),
                    authentication.name()
            );
        } catch (IllegalArgumentException usernameOrEmailIsTaken) {
            redirectAttributes
                    .addFlashAttribute("heroCreationBindingModel", heroCreationBindingModel)
                    .addFlashAttribute("heroNameIsTaken", true);

            return "redirect:createHero";
        }

        return "redirect:/home";
    }
}
