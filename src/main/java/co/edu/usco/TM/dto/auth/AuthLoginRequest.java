
package co.edu.usco.TM.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthLoginRequest {

    @NotBlank(message = "{ownerformregister.error.notblank}")
    @Pattern(regexp = "^(?!\\s*$)[A-Za-z0-9._-]{5,12}$", message = "{ownerformregister.error.username}")
    private String username;

    @NotBlank(message = "ownerformregister.error.notblank")
    @Pattern(regexp = "^(?=.*[A-Za-z]{8,})(?=.*\\d{2,})(?=.*[_\\-./*])[A-Za-z\\d_\\-./*]{10,20}$", message = "ownerformregister.error.password")
    private String password;
    
}
