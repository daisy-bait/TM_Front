package co.edu.usco.TM.controller.error;

import co.edu.usco.TM.dto.auth.AuthLoginRequest;
import co.edu.usco.TM.dto.request.veterinary.ReqOwnerDTO;
import feign.FeignException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    private HttpServletRequest request;

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleValidationExceptions(MethodArgumentNotValidException ex,
                                             Model model,
                                             RedirectAttributes redirectAttributes) {

        BindingResult bindingResult = ex.getBindingResult();
        Object target = bindingResult.getTarget();

        // Logging más estructurado
        logger.error("Error de validación para el objeto {}: {}",
                target.getClass().getSimpleName(),
                ex.getMessage());

        if (target instanceof AuthLoginRequest) {
            redirectAttributes.addFlashAttribute("loginRequest", target);
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.loginRequest",
                    bindingResult);
            return "redirect:/login?error";  // Vista de login
        } else if (target instanceof ReqOwnerDTO) {

            redirectAttributes.addFlashAttribute("newOwner", target);
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.newOwner",
                    bindingResult);

            String originalUrl = request.getRequestURI();

            logger.info("Url de la que viene: ", originalUrl);

            if (originalUrl.contains("/update")) {
                redirectAttributes.addFlashAttribute("action", "modify");
                return "redirect:/owner/modify?error";
            } else {
                redirectAttributes.addFlashAttribute("action", "register");
                return "redirect:/owner/register?error";
            }
        }

        return "views/error";

    }

}
