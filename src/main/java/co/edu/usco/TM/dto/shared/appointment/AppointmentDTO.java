
package co.edu.usco.TM.dto.shared.appointment;

import co.edu.usco.TM.dto.response.user.ResUserDTO;
import co.edu.usco.TM.dto.response.veterinary.ResPetDTO;
import co.edu.usco.TM.dto.response.veterinary.ResVetDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class AppointmentDTO {
    
    private Long id;
    private String reason;
    private String description;
    private LocalDateTime dateTime;
    private Integer duration;
    private String status;
    @JsonIgnoreProperties({"pets", "userAppointments"})
    private ResUserDTO owner;
    @JsonIgnoreProperties({"vetAppointments", "userAppointments", "pets"})
    private ResVetDTO vet;
    private ResPetDTO pet;
    
}
