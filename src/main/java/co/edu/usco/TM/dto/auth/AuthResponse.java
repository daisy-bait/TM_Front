package co.edu.usco.TM.dto.auth;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({"username", "message", "jwt", "status"})
public class AuthResponse {

    private String username;
    private String message;
    private String jwt;
    private boolean status;
    
}
