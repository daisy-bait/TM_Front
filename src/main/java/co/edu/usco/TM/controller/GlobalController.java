
package co.edu.usco.TM.controller;

import co.edu.usco.TM.client.AuthClient;
import co.edu.usco.TM.dto.auth.AuthLoginRequest;
import co.edu.usco.TM.dto.auth.AuthResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class GlobalController {
    
    private static final Logger logger = LoggerFactory.getLogger(GlobalController.class);
    
    @Autowired
    AuthClient authClient;
    
    @GetMapping
    public String showIndex() {
        return "index";
    }
    
    @GetMapping("/login")
    public String showLogin(Model model, @RequestParam(required = false) String error) {

        if (!model.containsAttribute("loginRequest")) {
            logger.info("Showing new login request");
            model.addAttribute("loginRequest", new AuthLoginRequest());
        } else {
            logger.info("Not creating new loginRequest");
        }

        if (error != null) {
            model.addAttribute("error", "Username or password is incorrect");
        }
        
        return "views/login";
    }
    
    @PostMapping("/process-login")
    public String processLogin(@Valid @ModelAttribute("loginRequest") AuthLoginRequest loginRequest,
                               BindingResult result,
                               HttpSession session) {

        if (result.hasErrors()) {
            logger.info(result.getAllErrors().toString());
            logger.info("Showing new login request");
            return "views/login";
        }

        try {
            
            logger.info("Enviando solicitud de autenticación para usuario: " + loginRequest.getUsername());
            logger.info("Contraseña --> " + loginRequest.getPassword());
            
            AuthResponse response = authClient.login(loginRequest);
            
            logger.info("Respuesta del servicio de autenticación: " + response.getMessage());
            logger.info("Response username: " + response.getUsername());
            logger.info("Response jwt: " + response.getJwt());
            logger.info("Response status: " + response.isStatus());
            
            if (response.isStatus()) {
                
                session.setAttribute("jwt", response.getJwt());
                session.setAttribute("username", response.getUsername());
                
                return "redirect:/owner/dashboard";
            } else {
                return "redirect:/login?error";
            }
        } catch (Exception ex) {
            logger.info("Excepción --> ", ex);
            return "redirect:/login?error";
        }
        
    }
    
    @GetMapping("/403")
    public String showForbidden() {
        return "views/error/403";
    }
    
//    @RequestMapping(value = "/logout", method = RequestMethod.GET)
//    public String logoutPage(HttpServletRequest request, HttpServletResponse response
//    ) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if (auth != null) {
//            new SecurityContextLogoutHandler().logout(request, response, auth);
//        }
//        return "redirect:/loginPage?logout"; 
//
//    }
}
