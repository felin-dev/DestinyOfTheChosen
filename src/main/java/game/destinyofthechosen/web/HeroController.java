package game.destinyofthechosen.web;

import game.destinyofthechosen.model.binding.HeroCreationBindingModel;
import game.destinyofthechosen.model.service.HeroCreationServiceModel;
import game.destinyofthechosen.service.HeroService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class HeroController {

    private final HeroService heroService;
    private final ModelMapper modelMapper;

    public HeroController(HeroService heroService, ModelMapper modelMapper) {
        this.heroService = heroService;
        this.modelMapper = modelMapper;
    }

    @ModelAttribute("heroCreationBindingModel")
    public HeroCreationBindingModel heroCreationBindingModel() {
        return new HeroCreationBindingModel();
    }

    @GetMapping("/heroes/create")
    public String createHero() {
        return "hero-creation";
    }

    @PostMapping("/heroes/create")
    public String createHeroConfirm(@Valid HeroCreationBindingModel heroCreationBindingModel,
                                    BindingResult bindingResult, RedirectAttributes redirectAttributes,
                                    Principal principal) {

        if (bindingResult.hasErrors()) {
            redirectAttributes
                    .addFlashAttribute("heroCreationBindingModel", heroCreationBindingModel)
                    .addFlashAttribute("org.springframework.validation.BindingResult.heroCreationBindingModel", bindingResult);

            return "redirect:/heroes/create";
        }

        heroService.createNewHero(
                modelMapper.map(heroCreationBindingModel, HeroCreationServiceModel.class),
                principal.getName());

        return "redirect:/home";
    }
}
