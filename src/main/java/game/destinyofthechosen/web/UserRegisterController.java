package game.destinyofthechosen.web;

import game.destinyofthechosen.model.binding.UserRegisterBindingModel;
import game.destinyofthechosen.model.service.UserRegisterServiceModel;
import game.destinyofthechosen.service.UserService;
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
@RequestMapping("/users")
public class UserRegisterController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    public UserRegisterController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @ModelAttribute("userRegisterBindingModel")
    public UserRegisterBindingModel userRegisterBindingModel() {
        return new UserRegisterBindingModel();
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String registerConfirm(@Valid UserRegisterBindingModel userRegisterBindingModel,
                                  BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes
                    .addFlashAttribute("userRegisterBindingModel", userRegisterBindingModel)
                    .addFlashAttribute("org.springframework.validation.BindingResult.userRegisterBindingModel", bindingResult);

            return "redirect:register";
        }

        if (!userRegisterBindingModel.getPassword().equals(userRegisterBindingModel.getConfirmPassword())) {
            redirectAttributes
                    .addFlashAttribute("userRegisterBindingModel", userRegisterBindingModel)
                    .addFlashAttribute("passwordMismatch", true);

            return "redirect:register";
        }

        try {
            userService.register(modelMapper.map(userRegisterBindingModel, UserRegisterServiceModel.class));
        } catch (IllegalArgumentException usernameOrEmailIsTaken) {
            redirectAttributes
                    .addFlashAttribute("userRegisterBindingModel", userRegisterBindingModel)
                    .addFlashAttribute("usernameOrEmailIsTaken", true);

            return "redirect:register";
        }

        return "redirect:/home";
    }
}
