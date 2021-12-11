package game.destinyofthechosen.web;

import game.destinyofthechosen.model.binding.ItemTransferBindingModel;
import game.destinyofthechosen.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.util.UUID;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // TODO
    @GetMapping("/users/profile")
    public String userProfile() {
        return "profile";
    }

    @GetMapping("/users/inventory")
    public String userInventory(Principal principal, Model model) {

        model.addAttribute("items", userService.getAllOwnedItems(principal.getName()));
        model.addAttribute("heroes", userService.getHeroesViewModel(principal.getName()));

        return "inventory";
    }

    @ModelAttribute("itemTransferBindingModel")
    public ItemTransferBindingModel itemTransferBindingModel() {
        return new ItemTransferBindingModel();
    }

    @PatchMapping("/users/weapons/transfer")
    public String transferWeapon(Principal principal, @Valid ItemTransferBindingModel itemTransferBindingModel,
                                 BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes
                    .addFlashAttribute("itemTransferBindingModel", itemTransferBindingModel)
                    .addFlashAttribute("org.springframework.validation.BindingResult.itemTransferBindingModel", bindingResult);

            return "redirect:/users/inventory";
        }

        userService.transferItemToHero(principal.getName(), itemTransferBindingModel.getHeroName(), itemTransferBindingModel.getItemId());

        return "redirect:/users/inventory";
    }

    @DeleteMapping("/users/weapons/{id}/throw")
    public String throwWeapon(Principal principal, @PathVariable UUID id) {

        userService.throwItem(principal.getName(), id);

        return "redirect:/users/inventory";
    }
}
