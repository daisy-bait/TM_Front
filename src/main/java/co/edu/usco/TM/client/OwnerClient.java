
package co.edu.usco.TM.client;

import co.edu.usco.TM.config.FeignClientConfig;
import co.edu.usco.TM.dto.request.veterinary.ReqOwnerDTO;
import co.edu.usco.TM.dto.response.veterinary.ResOwnerDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "ownerClient", url = "http://localhost:8080/api/owner", configuration = FeignClientConfig.class)
public interface OwnerClient {
    
    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    void SaveOwner(
            @RequestHeader String reqID,
            @RequestPart("owner") ReqOwnerDTO ownerDTO,
            @RequestPart("file") MultipartFile file);
    
    @GetMapping("/details")
    ResOwnerDTO getOwnerDetails();

    @PutMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    void updateOwner(
            @RequestPart("owner") ReqOwnerDTO ownerDTO,
            @RequestPart("file") MultipartFile file);
    
}
