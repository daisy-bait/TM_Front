package co.edu.usco.TM.client.veterinary;

import co.edu.usco.TM.config.FeignClientConfig;
import co.edu.usco.TM.dto.request.veterinary.ReqVetDTO;
import co.edu.usco.TM.dto.response.Page.PageResponse;
import co.edu.usco.TM.dto.response.user.ResUserDTO;
import co.edu.usco.TM.dto.response.veterinary.ResVetDTO;
import co.edu.usco.TM.dto.shared.appointment.ResContactDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@FeignClient(name = "vetClient", url = "http://localhost:8080/api/vet", configuration = FeignClientConfig.class)
public interface VetClient {

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    void saveVet(
            @RequestPart("vet") ReqVetDTO vetDTO,
            @RequestPart(value = "image", required = false) MultipartFile image,
            @RequestPart("degree") MultipartFile degree);

    @GetMapping("/find")
    PageResponse<ResVetDTO> findFilteredVets(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String specialty,
            @RequestParam(required = false) String veterinary,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    );

    @GetMapping("/find/{id}")
    ResVetDTO findVet(@PathVariable Long id);

    @GetMapping("/details")
    ResVetDTO getVetDetails();

    @PutMapping("/update")
    void updateVetDetails(
            @RequestPart("vet") ReqVetDTO vetDTO,
            @RequestPart(name = "image", value = "image", required = false) MultipartFile image,
            @RequestPart(name = "degree", value = "degree", required = false) MultipartFile degree,
            @RequestParam(name = "deleteImage", required = false) boolean deleteImg
    );

    @DeleteMapping("/disable")
    void disableVet();

    @PostMapping("/contact/add/{id}")
    void addContact(@PathVariable Long id);

    @GetMapping("/contact/list")
    PageResponse<ResContactDTO> listContacts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    );

    @GetMapping("/contact/list/all")
    List<ResUserDTO> listAllContacts();

    @DeleteMapping("/contact/remove/{id}")
    void removeContact(@PathVariable Long id);

}
