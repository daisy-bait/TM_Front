package co.edu.usco.TM.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class JwtRequestInterceptor implements RequestInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(RequestInterceptor.class);

    @Override
    public void apply(RequestTemplate template) {
        try {
            ServletRequestAttributes attributes =
                    (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

            if (attributes != null) {
                HttpSession session = attributes.getRequest().getSession(false);

                if (session != null && session.getAttribute("jwt") != null) {
                    String token = (String) session.getAttribute("jwt");
                    template.header("Authorization", "Bearer " + token);
                    logger.debug("Token JWT agregado al header: {}", "Bearer" + token);
                } else {
                    logger.debug("No se encontró token JWT en la sesión");
                }
            } else {
                logger.warn("No se encontraron RequestAttributes");
            }
        } catch (Exception ex) {
            logger.error("Error al aplicar el token JWT en la petición", ex);
        }
    }
}
