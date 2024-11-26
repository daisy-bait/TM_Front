
package co.edu.usco.TM.client.common;

import co.edu.usco.TM.config.FeignClientConfig;
import co.edu.usco.TM.dto.auth.AuthLoginRequest;
import co.edu.usco.TM.dto.auth.AuthResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "authClient", url = "http://localhost:8080/api/auth", configuration = FeignClientConfig.class)
public interface AuthClient {
    
    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    AuthResponse login(@RequestBody AuthLoginRequest request);

    @GetMapping("/roles")
    List<String> getRoles();
}
