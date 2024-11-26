package co.edu.usco.TM.client.veterinary;

import co.edu.usco.TM.config.FeignClientConfig;
import co.edu.usco.TM.dto.request.veterinary.ReqPetDTO;
import co.edu.usco.TM.dto.response.Page.PageResponse;
import co.edu.usco.TM.dto.response.veterinary.ResPetDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "petClient", url = "http://localhost:8080/api/pet", configuration = FeignClientConfig.class)
public interface PetClient {

    @PostMapping(value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    void savePet(
            @RequestPart("pet") ReqPetDTO petDTO,
            @RequestPart("file") MultipartFile file);

    @GetMapping("/find")
    PageResponse<ResPetDTO> find(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String specie,
            @RequestParam(required = false) Integer months,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    );

    @GetMapping("/find/{id}")
    ResPetDTO getPetDetails(
            @PathVariable Long id
    );

    @PutMapping(value = "/update/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    void updatePet(
            @RequestPart("pet") ReqPetDTO petDTO,
            @PathVariable Long id,
            @RequestPart(value = "file", required = false) MultipartFile file,
            @RequestParam(name = "deleteImage", required = false) boolean deleteImage
    );

    @DeleteMapping("/disable/{id}")
    void disablePet(
            @PathVariable Long id
    );

}
