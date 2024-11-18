
package co.edu.usco.TM.dto.request.veterinary;

import co.edu.usco.TM.dto.request.user.ReqUserDTO;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class ReqVetDTO extends ReqUserDTO{
    
    private String address;
    private String zipCode;
    private String phone;
    private String specialty;
    private String veterinary;
}
