
package co.edu.usco.TM.client.veterinary;

import co.edu.usco.TM.config.FeignClientConfig;
import co.edu.usco.TM.dto.request.user.ReqUserDTO;
import co.edu.usco.TM.dto.response.Page.PageResponse;
import co.edu.usco.TM.dto.response.user.ResUserDTO;
import co.edu.usco.TM.dto.response.veterinary.ResVetDTO;
import co.edu.usco.TM.dto.shared.appointment.ResContactDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@FeignClient(name = "ownerClient", url = "http://localhost:8080/api/owner", configuration = FeignClientConfig.class)
public interface OwnerClient {

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    void SaveOwner(
            @RequestPart("owner") ReqUserDTO ownerDTO,
            @RequestPart(value = "image", required = false) MultipartFile file);

    @GetMapping("/find")
    PageResponse<ResUserDTO> findFilteredOwners(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String email,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    );

    @GetMapping("/find/{id}")
    ResUserDTO findOwner(@PathVariable Long id);

    @GetMapping("/details")
    ResUserDTO getOwnerDetails();

    @PutMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    void updateOwnerDetails(
            @RequestPart("owner") ReqUserDTO ownerDTO,
            @RequestPart(value = "image", required = false) MultipartFile file,
            @RequestParam(name = "deleteImage", required = false) boolean deleteImage
    );

    @DeleteMapping("/disable")
    void disableOwner();

    @PostMapping("/contact/add/{id}")
    void addContact(@PathVariable Long id);

    @GetMapping("/contact/list")
    PageResponse<ResContactDTO> listContacts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String specialty,
            @RequestParam(required = false) String veterinary,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    );

    @GetMapping("/contact/list/all")
    List<ResVetDTO> listAllContacts();

    @DeleteMapping("/contact/remove/{id}")
    void removeContact(@PathVariable Long id);
}
