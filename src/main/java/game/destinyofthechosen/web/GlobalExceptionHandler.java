package game.destinyofthechosen.web;

import game.destinyofthechosen.exception.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ObjectNotFoundException.class)
    public ModelAndView handleDbExceptions(ObjectNotFoundException exception) {
        ModelAndView modelAndView = new ModelAndView("object-not-found");
        modelAndView.addObject("message", exception.getMessage());
        modelAndView.setStatus(HttpStatus.BAD_REQUEST);

        return modelAndView;
    }
}
