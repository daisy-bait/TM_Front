
package co.edu.usco.TM.dto.request.veterinary;

import co.edu.usco.TM.dto.appointment.PetSpecie;
import co.edu.usco.TM.dto.response.veterinary.ResOwnerDTO;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class ReqPetDTO {

    @NotEmpty
    private String name;
    @NotEmpty
    private PetSpecie specie;
    @Positive
    private Double weight;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    private ResOwnerDTO owner;

}
