
package co.edu.usco.TM.dto.request.veterinary;

import co.edu.usco.TM.dto.request.user.ReqUserDTO;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class ReqOwnerDTO extends ReqUserDTO{
    
    private String zipCode;
    private String address;
    private String phone;
}
