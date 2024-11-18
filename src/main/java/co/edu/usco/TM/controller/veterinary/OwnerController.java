package co.edu.usco.TM.controller.veterinary;

import co.edu.usco.TM.dto.request.veterinary.ReqOwnerDTO;
import co.edu.usco.TM.client.OwnerClient;
import co.edu.usco.TM.dto.response.veterinary.ResOwnerDTO;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.io.IOException;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/owner")
public class OwnerController {

    @Autowired
    OwnerClient ownerClient;

    @Autowired
    ModelMapper modelMapper;
    
    private static final Logger logger = LoggerFactory.getLogger(OwnerController.class);

    @GetMapping("/register")
    public String showRegisterForm(Model model) {

        model.addAttribute("newOwner", new ReqOwnerDTO());
        model.addAttribute("action", "register");

        return "views/veterinary/owner/modifyOwnerForm";
    }

    @PostMapping("/create")
    public String register(@Valid @ModelAttribute("newOwner") ReqOwnerDTO newOwner,
            BindingResult result,
            @RequestParam("file") MultipartFile file) throws IOException {

        if (result.hasErrors()) {
            logger.info("Hay errores");
            logger.info(result.getAllErrors().toString());
            return "views/veterinary/owner/modifyOwnerForm";
        }

        String requestID = UUID.randomUUID().toString();
        ownerClient.SaveOwner(requestID, newOwner, file);

        return "redirect:/";
    }
    
    @GetMapping("/dashboard")
    public String getOwnerProfile(Model model) {
        try {

//            String token = (String) session.getAttribute("jwt");
//
//            if (token == null) {
//                logger.error("No se encontró el token en la sesión");
//                return "redirect:/login";
//            }
//
//            String bearerToken = "Bearer " + token;
//            logger.info("Enviando token para autenticar usuario: " + bearerToken);

            ResOwnerDTO ownerDetails = ownerClient.getOwnerDetails();
            
            logger.info("Obteniendo Detalles del Owner logeado");
            logger.info("Username: " + ownerDetails.getUsername());
            logger.info("Name: " + ownerDetails.getName());
            logger.info("Email: " + ownerDetails.getEmail());
            logger.info("Address: " + ownerDetails.getAddress());
            logger.info("ZipCode: " + ownerDetails.getZipCode());
            logger.info("Phone: " + ownerDetails.getPhone());
            
            model.addAttribute("owner", ownerDetails);
            return "views/veterinary/owner/dashboard";
        } catch (Exception ex) {
            logger.info("Excepción -->", ex);
            model.addAttribute("error", "No se pudo obtener la información del Dueño");
            return "views/veterinary/error";
        }
    }

    @GetMapping("/modify")
    public String showModifyForm(Model model) {

        ReqOwnerDTO modifyOwner = modelMapper.map(ownerClient.getOwnerDetails(), ReqOwnerDTO.class);

        model.addAttribute("newOwner", modifyOwner);
        model.addAttribute("action", "modify");

        return "views/veterinary/owner/modifyOwnerForm";

    }

    @PostMapping("/modify")
    public String modify(
            @Valid @ModelAttribute("newOwner") ReqOwnerDTO ownerDTO,
            @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {

        ownerClient.updateOwner(ownerDTO, file);

        return "redirect:/login";

    }


}
