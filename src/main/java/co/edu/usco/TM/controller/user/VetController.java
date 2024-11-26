package co.edu.usco.TM.controller.user;

import co.edu.usco.TM.aspect.RequireRole;
import co.edu.usco.TM.client.veterinary.OwnerClient;
import co.edu.usco.TM.client.veterinary.VetClient;
import co.edu.usco.TM.dto.request.veterinary.ReqVetDTO;
import co.edu.usco.TM.dto.response.Page.PageResponse;
import co.edu.usco.TM.dto.response.user.ResUserDTO;
import co.edu.usco.TM.dto.response.veterinary.ResVetDTO;
import co.edu.usco.TM.dto.shared.appointment.ResContactDTO;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/vet")
public class VetController {

    @Autowired
    VetClient vetClient;

    @Autowired
    OwnerClient ownerClient;

    @Autowired
    ModelMapper modelMapper;

    @GetMapping("/register")
    public String showRegisterForm(Model model) {

        model.addAttribute("newVet", new ReqVetDTO());
        model.addAttribute("action", "register");

        return "views/veterinary/vet/modifyVetForm";

    }

    @PostMapping("/create")
    public String register(
            @Valid @ModelAttribute("newVet") ReqVetDTO vetDTO,
            BindingResult bindingResult,
            @RequestParam(value = "image", required = false) MultipartFile image,
            @RequestParam("degree") MultipartFile degree) throws IOException {

        if (bindingResult.hasErrors()) {
            return "views/veterinary/vet/modifyVetForm";
        }

        vetClient.saveVet(vetDTO, image, degree);

        return "redirect:/vet/login";
    }

    @RequireRole("ROLE_VET")
    @GetMapping("/modify")
    public String showModifyForm(Model model) {

        ReqVetDTO modifyVet = modelMapper.map(vetClient.getVetDetails(), ReqVetDTO.class);

        model.addAttribute("newVet", modifyVet);
        model.addAttribute("action", "modify");

        return "views/veterinary/vet/modifyVetForm";
    }

    @PostMapping("/modify")
    public String modify(
            @Valid @ModelAttribute("newVet") ReqVetDTO vetDTO,
            BindingResult result, Model model,
            @RequestParam(value = "file", required = false) MultipartFile image,
            @RequestParam(value = "degree", required = false) MultipartFile degree
    ) {
        if (result.hasErrors()) {
            model.addAttribute("action", "modify");
            return "views/veterinary/vet/modifyVetForm";
        }

        vetClient.updateVetDetails(vetDTO, image, degree, false);
        return "redirect:/login";
    }

    @RequireRole("ROLE_VET")
    @GetMapping("/contact/{event}")
    public String showAddContactList(
            @PathVariable("event") String event,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String email,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @ModelAttribute("vetContacts") List<ResUserDTO> vetContacts,
            @ModelAttribute("user") ResVetDTO user,
            Model model
    ) {

        PageResponse<ResContactDTO> ownerList = new PageResponse<>();
        PageResponse<ResUserDTO> availableOwners = new PageResponse<>();

        Map<String, Object> filters = new HashMap<>();
        addFilters(filters, name, username, email);

        if (event.equals("add")) {
            availableOwners = ownerClient.findFilteredOwners(name, username, email, page, size);

            List<ResUserDTO> excludeOwners= availableOwners.getContent().stream()
                    .filter(owner -> vetContacts.stream().anyMatch(contact -> contact.getId().equals(owner.getId())))
                    .collect(Collectors.toList());

            model.addAttribute("excludeOwners", excludeOwners);
            model.addAttribute("availableOwners", availableOwners.getContent());
            model.addAttribute("currentPage", availableOwners.getPageNumber());
            model.addAttribute("totalPages", availableOwners.getTotalPages());
            model.addAttribute("totalElements", availableOwners.getTotalElements());
            model.addAttribute("filters", filters);
            return "views/veterinary/vet/contacts/vetAddContact";
        } else if (event.equals("request")) {
            ownerList = vetClient.listContacts(name, username, email, "PENDING", page, size);
        } else if (event.equals("list")) {
            ownerList = vetClient.listContacts(name, username, email, "ACCEPTED", page, size);
        }

        addModel(model, ownerList, filters);
        model.addAttribute("event", event);

        return "views/veterinary/vet/contacts/vetlistContacts";
    }

    @RequireRole("ROLE_VET")
    @GetMapping("/contact/{action}/{id}")
    public String removeContact(
            @PathVariable Long id, @PathVariable String action,
            @ModelAttribute("filters") Map<String, Object> filters,
            @RequestParam(value = "event", required = false) String event, Model model) {

        model.addAttribute("filters", filters);

        if (action.equals("remove")) {
            vetClient.removeContact(id);
        } else if (action.equals("add")) {
            vetClient.addContact(id);
            return "redirect:/vet/contact/add";
        }

        return "redirect:/vet/contact/" + event;
    }

    public void addFilters(Map<String, Object> filters, String name, String username, String email) {
        filters.put("name", name);
        filters.put("username", username);
        filters.put("email", email);
    }

    public void addModel(Model model, PageResponse<ResContactDTO> ownerList, Map<String, Object> filters) {
        model.addAttribute("pageOwnerList", ownerList.getContent());
        model.addAttribute("currentPage", ownerList.getPageNumber());
        model.addAttribute("totalPages", ownerList.getTotalPages());
        model.addAttribute("totalElements", ownerList.getTotalElements());
        model.addAttribute("filters", filters);
    }

}
