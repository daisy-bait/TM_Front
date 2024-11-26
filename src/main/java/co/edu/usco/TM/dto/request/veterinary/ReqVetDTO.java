
package co.edu.usco.TM.dto.request.veterinary;

import co.edu.usco.TM.dto.request.user.ReqUserDTO;
import co.edu.usco.TM.dto.shared.PetSpecie;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReqVetDTO extends ReqUserDTO{

    @NotNull(message="ownerformregister.error.notblank")
    private PetSpecie specialty;
    @NotEmpty(message="ownerformregister.error.notblank")
    @NotNull(message="ownerformregister.error.notblank")
    private String veterinary;
}
