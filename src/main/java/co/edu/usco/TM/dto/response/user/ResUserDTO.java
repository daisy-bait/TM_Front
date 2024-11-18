
package co.edu.usco.TM.dto.response.user;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class ResUserDTO {
    
    private Long id;
    private String username;
    private String name;
    private String email;
    private String role;
    private String imgURL;
}