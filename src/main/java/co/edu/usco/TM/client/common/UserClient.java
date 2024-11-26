package co.edu.usco.TM.client.common;

import co.edu.usco.TM.config.FeignClientConfig;
import co.edu.usco.TM.dto.response.Page.PageResponse;
import co.edu.usco.TM.dto.response.user.ResUserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "userClient", url = "http://localhost:8080/api/user", configuration = FeignClientConfig.class)
public interface UserClient {

    @GetMapping("/find")
    PageResponse<ResUserDTO> getUser(@RequestParam(required = false) String name,
                                     @RequestParam(required = false) String username,
                                     @RequestParam(required = false) String email);

}
