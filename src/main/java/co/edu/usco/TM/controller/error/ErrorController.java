package co.edu.usco.TM.controller.error;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/error")
public class ErrorController {

    @GetMapping("/403")
    public String showForbidden() {
        return "views/error/403";
    }

    @GetMapping("/404")
    public String showNotFound() {
        return "views/error/404";
    }

    @GetMapping("/500")
    public String showServerError() {
        return "views/error/500";
    }

}
