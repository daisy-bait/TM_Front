
package co.edu.usco.TM.dto.appointment;

import co.edu.usco.TM.dto.request.veterinary.ReqPetDTO;
import co.edu.usco.TM.dto.response.veterinary.ResOwnerDTO;
import co.edu.usco.TM.dto.response.veterinary.ResVetDTO;
import java.time.LocalDateTime;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class AppointmentDTO {
    
    private Long id;
    private String reason;
    private String description;
    private LocalDateTime dateTime;
    private String status;
    private ResOwnerDTO owner;
    private ResVetDTO vet;
    private ReqPetDTO pet;
    
}
