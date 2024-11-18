
package co.edu.usco.TM.dto.response.veterinary;

import co.edu.usco.TM.dto.appointment.PetSpecie;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class ResPetDTO {

    private String name;
    private PetSpecie specie;
    private Double weight;
    private int years;
    private int months;
    private LocalDate birthDate;

    @JsonBackReference
    private ResOwnerDTO owner;

}
