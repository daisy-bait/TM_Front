package co.edu.usco.TM.dto.shared.appointment;

import co.edu.usco.TM.dto.response.user.ResUserDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResContactDTO {

    private ResUserDTO user;
    private Long originID;
    private String status;

}
