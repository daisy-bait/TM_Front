package co.edu.usco.TM.controller.veterinary;

import co.edu.usco.TM.client.PetClient;
import co.edu.usco.TM.dto.request.veterinary.ReqPetDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/pet")
public class PetController {

    @Autowired
    PetClient petClient;

    @GetMapping("/register")
    public String showRegisterForm(Model model) {

        model.addAttribute("newPet", new ReqPetDTO());
        model.addAttribute("action", "register");

        return "views/veterinary/pet/modifyPetForm";
    }

    @PostMapping("/create")
    public String register(@Valid @ModelAttribute("newPet") ReqPetDTO newPet,
                           BindingResult result,
                           @RequestParam("file") MultipartFile file) {

        if (result.hasErrors()) {
            return "views/veterinary/pet/modifyPetForm";
        }

        petClient.savePet(newPet, file);
        return "redirect:/owner/dashboard";
    }

}
