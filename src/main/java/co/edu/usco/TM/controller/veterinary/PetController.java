package co.edu.usco.TM.controller.veterinary;

import co.edu.usco.TM.aspect.RequireRole;
import co.edu.usco.TM.client.veterinary.PetClient;
import co.edu.usco.TM.dto.request.veterinary.ReqPetDTO;
import co.edu.usco.TM.dto.response.Page.PageResponse;
import co.edu.usco.TM.dto.response.veterinary.ResPetDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/pet")
public class PetController {

    @Autowired
    PetClient petClient;

    @RequireRole("ROLE_OWNER")
    @GetMapping("/register")
    public String showRegisterForm(Model model) {

        model.addAttribute("newPet", new ReqPetDTO());
        model.addAttribute("action", "register");

        return "views/veterinary/pet/modifyPetForm";
    }

    @PostMapping("/create")
    public String register(@Valid @ModelAttribute("newPet") ReqPetDTO newPet,
                           BindingResult result,
                           @RequestParam(value = "file", required = false) MultipartFile file) {

        if (result.hasErrors()) {
            return "views/veterinary/pet/modifyPetForm";
        }

        petClient.savePet(newPet, file);
        return "redirect:/user/dashboard";
    }

    @RequireRole("ROLE_OWNER")
    @GetMapping("/find")
    public String listPets(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String specie,
            @RequestParam(required = false) Integer months,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            Model model
    ) {

        PageResponse<ResPetDTO> petList = petClient.find(name, specie, months, page, size);

        model.addAttribute("petList", petList.getContent());
        model.addAttribute("currentPage", petList.getPageNumber());
        model.addAttribute("totalPages", petList.getTotalPages());
        model.addAttribute("totalElements", petList.getTotalElements());

        Map<String, Object> filters = new HashMap<>();
        filters.put("name", name);
        filters.put("specie", specie);
        filters.put("months", months);

        model.addAttribute("filters", filters);

        return "views/veterinary/owner/listPets";
    }

    @GetMapping("/find/{id}")
    public String findPet(@PathVariable Long id, Model model) {

        model.addAttribute("pet", petClient.getPetDetails(id));
        return "views/veterinary/pet/info";

    }

    @RequireRole("ROLE_OWNER")
    @GetMapping("/modify/{id}")
    public String showModifyPetForm(@PathVariable Long id, Model model) {

        ResPetDTO modifyPet = petClient.getPetDetails(id);
        model.addAttribute("newPet", modifyPet);
        model.addAttribute("action", "modify");

        return "views/veterinary/pet/modifyPetForm";
    }

    @PostMapping("/modify/{id}")
    public String modify(
            @Valid @ModelAttribute("newPet") ReqPetDTO modifyPet,
            BindingResult result, Model model,
            @PathVariable Long id,
            @RequestParam(value = "file", required = false) MultipartFile file
    ) {
        if (result.hasErrors()) {
            model.addAttribute("action", "modify");
            return "views/veterinary/pet/modifyPetForm";
        }

        petClient.updatePet(modifyPet, id, file, false);

        return "redirect:/pet/find/" + id;
    }

}
