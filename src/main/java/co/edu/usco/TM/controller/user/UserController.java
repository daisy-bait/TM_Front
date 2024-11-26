package co.edu.usco.TM.controller.user;

import co.edu.usco.TM.aspect.RequireRole;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    @GetMapping("/dashboard")
    @RequireRole("AUTH")
    public String getOwnerProfile(Model model) throws Exception {
        try {
            return "views/user/dashboard";
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

}
