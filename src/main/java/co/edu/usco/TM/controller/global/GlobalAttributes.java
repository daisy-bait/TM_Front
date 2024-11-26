package co.edu.usco.TM.controller.global;

import co.edu.usco.TM.client.veterinary.OwnerClient;
import co.edu.usco.TM.client.veterinary.VetClient;
import co.edu.usco.TM.dto.response.user.ResUserDTO;
import co.edu.usco.TM.dto.response.veterinary.ResVetDTO;
import co.edu.usco.TM.dto.shared.appointment.ResContactDTO;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalAttributes {

    @Autowired
    OwnerClient ownerClient;

    @Autowired
    VetClient vetClient;

    @ModelAttribute("isLoggedIn")
    public boolean isLoggedIn(HttpSession session) {
        return session.getAttribute("jwt") != null;
    }

    @ModelAttribute("user")
    public ResUserDTO getUser(HttpSession session, HttpServletRequest req) {

        String uri = req.getRequestURI();

        if (session.getAttribute("jwt") != null && (!uri.equals("/logout"))) {
            if (getRoles(session).contains("ROLE_VET")) {
                return vetClient.getVetDetails();
            } else {
                return ownerClient.getOwnerDetails();
            }
        }

        return (ResUserDTO) session.getAttribute("user");
    }

    @ModelAttribute("ownerContacts")
    public List<ResVetDTO> getOwnerContacts(HttpSession session) {

        if (session.getAttribute("jwt") != null && getRoles(session).contains("ROLE_OWNER")) {
            List<ResVetDTO> vets = ownerClient.listAllContacts();
            return ownerClient.listAllContacts();
        }

        return new ArrayList<>();
    }

    @ModelAttribute("vetContacts")
    public List<ResUserDTO> getVetContacts(HttpSession session) {

        if (session.getAttribute("jwt") != null && getRoles(session).contains("ROLE_VET")) {
            return vetClient.listAllContacts();
        }

        return new ArrayList<>();
    }

    @ModelAttribute("roles")
    public List<String> getRoles(HttpSession session) {
        Object roles = session.getAttribute("roles");
        if (roles instanceof List) {
            return (List<String>) roles;
        } else {
            return new ArrayList<>();
        }
    }

}
