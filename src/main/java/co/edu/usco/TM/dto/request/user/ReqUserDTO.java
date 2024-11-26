
package co.edu.usco.TM.dto.request.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReqUserDTO {


    @NotBlank(message = "{ownerformregister.error.notblank}")
    @Pattern(regexp = "^(?!\\s*$)[A-Za-z0-9._-]{5,12}$", message = "{ownerformregister.error.username}")
    private String username;

    @NotBlank(message = "ownerformregister.error.notblank")
    @Pattern(regexp = "^(?!\\s*$)(?:\\b[A-Za-z]{3,}\\b(?:\\s+|$)){2,5}$", message = "ownerformregister.error.name")
    private String name;

    @NotBlank(message = "ownerformregister.error.notblank")
    @Pattern(regexp = "^(?!\\s*$)[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", message = "ownerformregister.error.email")
    private String email;

    @NotBlank(message = "ownerformregister.error.notblank")
    @Pattern(regexp = "^(?=.*[A-Za-z]{8,})(?=.*\\d{2,})(?=.*[_\\-./*])[A-Za-z\\d_\\-./*]{10,20}$", message = "ownerformregister.error.password")
    private String password;

    @Pattern(regexp = "^(?:\\b[A-Za-z]+\\b\\s+\\d+[A-Za-z]?(?:\\s+#\\d+[A-Za-z\\-]*)?)?$", message = "ownerformregister.error.address")
    private String address;

    @Pattern(regexp = "^(?:410\\d{3})?$", message = "ownerformregister.error.zipCode")
    private String zipCode;

    @NotBlank(message = "ownerformregister.error.notblank")
    @Pattern(regexp = "^3\\d{9}$", message = "ownerformregister.error.phone")
    private String phone;
}
