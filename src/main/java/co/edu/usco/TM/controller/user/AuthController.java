
package co.edu.usco.TM.controller.user;

import co.edu.usco.TM.client.common.AuthClient;
import co.edu.usco.TM.client.veterinary.OwnerClient;
import co.edu.usco.TM.client.veterinary.VetClient;
import co.edu.usco.TM.config.lang.Cookies;
import co.edu.usco.TM.dto.auth.AuthLoginRequest;
import co.edu.usco.TM.dto.auth.AuthResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Controller
public class AuthController {
    
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    
    @Autowired
    AuthClient authClient;

    @Autowired
    OwnerClient ownerClient;

    @Autowired
    VetClient vetClient;

    @Autowired
    Cookies cookies;

    @GetMapping
    public String showIndex() {
        return "index";
    }

    @GetMapping("/login")
    public String showLogin(Model model, @RequestParam(required = false) String error) {

        if (!model.containsAttribute("loginRequest")) {
            model.addAttribute("loginRequest", new AuthLoginRequest());
        } else {
        }

        if (error != null) {
            model.addAttribute("error", "Nombre de Usuario o contraseña no válidos");
        }
        
        return "views/login";
    }

    @PostMapping("/process-login")
    public String processLogin(@Valid @ModelAttribute("loginRequest") AuthLoginRequest loginRequest,
                               BindingResult result,
                               HttpSession session) {
        if (result.hasErrors()) {
            return "views/login";
        }

        try {
            
            AuthResponse response = authClient.login(loginRequest);
            
            if (response.isStatus()) {
                
                session.setAttribute("jwt", response.getJwt());
                session.setAttribute("username", response.getUsername());

                List<String> roles = authClient.getRoles();

                if (roles.contains("ROLE_VET")) {
                    session.setAttribute("user", vetClient.getVetDetails());
                } else {
                    session.setAttribute("user", ownerClient.getOwnerDetails());
                }

                session.setAttribute("roles", roles);
                return "redirect:/user/dashboard";
            } else {
                return "redirect:/login?error";
            }

        } catch (Exception ex) {
            return "redirect:/login?error";
        }
        
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response, HttpSession session) {

        String currentLang = cookies.getLanguageFromCookie(request);
        cookies.setLanguageCookies(currentLang, response);

        session.invalidate();
        return "redirect:/login?logout";
    }

}
