
package co.edu.usco.TM.dto.response.veterinary;

import co.edu.usco.TM.dto.response.user.ResUserDTO;
import co.edu.usco.TM.dto.shared.appointment.AppointmentDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ResVetDTO extends ResUserDTO{

    private String specialty;
    private String veterinary;
    private String degreeURL;

    private List<AppointmentDTO> vetAppointments;
}
