package co.edu.usco.TM.controller.user;

import co.edu.usco.TM.aspect.RequireRole;
import co.edu.usco.TM.client.veterinary.VetClient;
import co.edu.usco.TM.dto.request.user.ReqUserDTO;
import co.edu.usco.TM.client.veterinary.OwnerClient;
import co.edu.usco.TM.dto.response.Page.PageResponse;
import co.edu.usco.TM.dto.response.veterinary.ResVetDTO;
import co.edu.usco.TM.dto.shared.appointment.ResContactDTO;
import jakarta.validation.Valid;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/owner")
public class OwnerController {

    @Autowired
    OwnerClient ownerClient;

    @Autowired
    VetClient vetClient;

    @Autowired
    ModelMapper modelMapper;

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("newOwner", new ReqUserDTO());
        model.addAttribute("action", "register");

        return "views/veterinary/owner/modifyOwnerForm";
    }

    @PostMapping("/create")
    public String register(@Valid @ModelAttribute("newOwner") ReqUserDTO newOwner,
                           BindingResult result,
                           @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {

        if (result.hasErrors()) {
            return "views/veterinary/owner/modifyOwnerForm";
        }

        ownerClient.SaveOwner(newOwner, file);

        return "redirect:/login";
    }

    @GetMapping("/modify")
    public String showModifyForm(Model model) {

        ReqUserDTO modifyOwner = modelMapper.map(ownerClient.getOwnerDetails(), ReqUserDTO.class);

        model.addAttribute("newOwner", modifyOwner);
        model.addAttribute("action", "modify");

        return "views/veterinary/owner/modifyOwnerForm";

    }

    @PostMapping("/modify")
    public String modify(
            @Valid @ModelAttribute("newOwner") ReqUserDTO ownerDTO,
            BindingResult result, Model model,
            @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {

        if (result.hasErrors()) {
            model.addAttribute("action", "modify");
            return "views/veterinary/owner/modifyOwnerForm";
        }
        ownerClient.updateOwnerDetails(ownerDTO, file, false);

        return "redirect:/logout";

    }

    @RequireRole("ROLE_OWNER")
    @GetMapping("/contact/{event}")
    public String showAddContactList(
            @PathVariable("event") String event,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String specialty,
            @RequestParam(required = false) String veterinary,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @ModelAttribute("ownerContacts") List<ResVetDTO> ownerContacts,
            Model model
    ) {

        PageResponse<ResContactDTO> vetList = new PageResponse<>();
        PageResponse<ResVetDTO> availableVets = new PageResponse<>();

        Map<String, Object> filters = new HashMap<>();
        addFilters(filters, name, username, email, specialty, veterinary);

        if (event.equals("add")) {
            availableVets = vetClient.findFilteredVets(name, username, email, specialty, veterinary, page, size);

            List<ResVetDTO> excludeVets= availableVets.getContent().stream()
                    .filter(vet -> ownerContacts.stream().anyMatch(contact -> contact.getId().equals(vet.getId())))
                    .collect(Collectors.toList());

            model.addAttribute("excludeVets", excludeVets);
            model.addAttribute("availableVets", availableVets.getContent());
            model.addAttribute("currentPage", availableVets.getPageNumber());
            model.addAttribute("totalPages", availableVets.getTotalPages());
            model.addAttribute("totalElements", availableVets.getTotalElements());
            model.addAttribute("filters", filters);
            return "views/veterinary/owner/contacts/ownerAddContact";
        } else if (event.equals("request")) {
            vetList = ownerClient.listContacts(name, username, email, specialty, veterinary, "PENDING", page, size);
        } else if (event.equals("list")) {
            vetList = ownerClient.listContacts(name, username, email, specialty, veterinary, "ACCEPTED", page, size);
        }

        addModel(model, vetList, filters);
        model.addAttribute("event", event);

        return "views/veterinary/owner/contacts/ownerListContacts";
    }

    @RequireRole("ROLE_OWNER")
    @GetMapping("/contact/{action}/{id}")
    public String removeContact(
            @PathVariable Long id, @PathVariable String action,
            @ModelAttribute("filters") Map<String, Object> filters,
            @RequestParam(value = "event", required = false) String event, Model model) {

        model.addAttribute("filters", filters);

        if (action.equals("remove")) {
            ownerClient.removeContact(id);
        } else if (action.equals("add")) {
            ownerClient.addContact(id);
            return "redirect:/owner/contact/add";
        }

        return "redirect:/owner/contact/" + event;
    }

    public void addFilters(Map<String, Object> filters, String name, String username, String email, String specialty, String veterinary) {
        filters.put("name", name);
        filters.put("username", username);
        filters.put("email", email);
        filters.put("specialty", specialty);
        filters.put("veterinary", veterinary);
    }

    public void addModel(Model model, PageResponse<ResContactDTO> vetList, Map<String, Object> filters) {
        model.addAttribute("pageVetList", vetList.getContent());
        model.addAttribute("currentPage", vetList.getPageNumber());
        model.addAttribute("totalPages", vetList.getTotalPages());
        model.addAttribute("totalElements", vetList.getTotalElements());
        model.addAttribute("filters", filters);
    }


}
