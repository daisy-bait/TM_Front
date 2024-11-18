
package co.edu.usco.TM.dto.request.user;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class ReqUserDTO {
    
    private Long id;

    @NotEmpty
    private String username;
    private String name;
    private String email;
    private String imgURL;
    private String password;
}
