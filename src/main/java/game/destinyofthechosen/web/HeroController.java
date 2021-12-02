package game.destinyofthechosen.web;

import game.destinyofthechosen.model.binding.HeroCreationBindingModel;
import game.destinyofthechosen.model.binding.HeroSelectBindingModel;
import game.destinyofthechosen.model.binding.StatUpBindingModel;
import game.destinyofthechosen.model.service.HeroCreationServiceModel;
import game.destinyofthechosen.model.service.HeroSelectServiceModel;
import game.destinyofthechosen.model.service.StatUpServiceModel;
import game.destinyofthechosen.service.HeroService;
import game.destinyofthechosen.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class HeroController {

    private final UserService userService;
    private final HeroService heroService;
    private final ModelMapper modelMapper;

    public HeroController(UserService userService, HeroService heroService, ModelMapper modelMapper) {
        this.userService = userService;
        this.heroService = heroService;
        this.modelMapper = modelMapper;
    }

    @ModelAttribute("stats")
    public StatUpBindingModel statUpBindingModel() {
        return new StatUpBindingModel();
    }

    @ModelAttribute("notEnoughStats")
    public Boolean notEnoughStats() {
        return false;
    }

    @GetMapping("/heroes/info")
    public String selectHero(Model model, Principal principal) {

        model.addAttribute("hero", heroService.getCurrentHeroInfo(principal.getName()));

        return "hero-info";
    }

    @PatchMapping("/heroes/stat-up")
    public String addStats(@Valid StatUpBindingModel statUpBindingModel, BindingResult bindingResult,
                           RedirectAttributes redirectAttributes, Principal principal) {

        if (bindingResult.hasErrors()) {
            redirectAttributes
                    .addFlashAttribute("statUpBindingModel", statUpBindingModel)
                    .addFlashAttribute("notEnoughStats", true);

            return "redirect:info";
        }

        heroService.addStats(modelMapper.map(statUpBindingModel, StatUpServiceModel.class), principal.getName());

        return "redirect:info";
    }

    @ModelAttribute("heroSelectBindingModel")
    public HeroSelectBindingModel heroSelectBindingModel() {
        return new HeroSelectBindingModel();
    }

    @GetMapping("/heroes/select")
    public String heroInfo(Model model, Principal principal) {

        model.addAttribute("user", userService.getUserWithOwnedHeroes(principal.getName()));

        return "hero-select";
    }

    @SuppressWarnings("SpringElInspection")
    @PreAuthorize("ownsThisHero(#heroSelectBindingModel)")
    @PostMapping("/heroes/select")
    public String selectHeroConfirm(@Valid HeroSelectBindingModel heroSelectBindingModel,
                                    BindingResult bindingResult, Principal principal) {

        if (bindingResult.hasErrors()) return "redirect:select";

        userService.selectNewHero(principal.getName(),
                modelMapper.map(heroSelectBindingModel, HeroSelectServiceModel.class));

        return "redirect:/";
    }

    @SuppressWarnings("SpringElInspection")
    @PreAuthorize("ownsThisHero(#heroSelectBindingModel)")
    @DeleteMapping("/heroes/delete")
    public String deleteHeroConfirm(@Valid HeroSelectBindingModel heroSelectBindingModel,
                                    BindingResult bindingResult, Principal principal) {

        if (bindingResult.hasErrors()) return "redirect:select";

        userService.deleteHero(principal.getName(),
                modelMapper.map(heroSelectBindingModel, HeroSelectServiceModel.class));

        return "redirect:/";
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

            return "redirect:create";
        }

        heroService.createNewHero(
                modelMapper.map(heroCreationBindingModel, HeroCreationServiceModel.class),
                principal.getName());

        return "redirect:/home";
    }
}
