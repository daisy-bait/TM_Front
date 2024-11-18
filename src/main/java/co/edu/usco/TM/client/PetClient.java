package co.edu.usco.TM.client;

import co.edu.usco.TM.config.FeignClientConfig;
import co.edu.usco.TM.dto.request.veterinary.ReqPetDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "petClient", url = "http://localhost:8080/api/owner/pet", configuration = FeignClientConfig.class)
public interface PetClient {

    @PostMapping(value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    void savePet(
            @RequestPart("pet") ReqPetDTO petDTO,
            @RequestPart("file")MultipartFile file);

    @GetMapping("/details")
    void getPetDetails(
            @RequestParam("petID") Long id
    );

}
