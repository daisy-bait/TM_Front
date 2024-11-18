
package co.edu.usco.TM.client;

import co.edu.usco.TM.config.FeignClientConfig;
import co.edu.usco.TM.dto.auth.AuthLoginRequest;
import co.edu.usco.TM.dto.auth.AuthResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "authClient", url = "http://localhost:8080/api/auth", configuration = FeignClientConfig.class)
public interface AuthClient {
    
    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    AuthResponse login(@RequestBody AuthLoginRequest request);
    
}
