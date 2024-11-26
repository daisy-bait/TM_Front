package co.edu.usco.TM.aspect;

import feign.FeignException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.Set;


@Aspect
@Component
public class CheckRole {

    /**
     * Este aspecto, en su definición, se encarga de interceptar los métodos anotados con @RequireRole
     * y verificar si el usuario tiene el rol requerido para ejecutar el metodo.
     * En dado caso de que el usuario no lo tenga, tirara una excepción de seguridad.
     * usado para el rol de administrador.
     * @param joinPoint Representa el punto de ejecución del metodo anotado con @RequireRole
     * @param requireRole Contiene el rol requerido para ejecutar el metodo
     * @return devuelve el resultado de la ejecución del metodo.
     * @throws Throwable Recoje las excepciones que se puedan generar en la ejecución del metodo.
     */
    @Around("@annotation(requireRole)")
    public Object checkRole(ProceedingJoinPoint joinPoint, RequireRole requireRole) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpSession session = attributes.getRequest().getSession(false);

        List<String> roles = (List<String>) session.getAttribute("roles");

        if (requireRole.value().equals("AUTH") && roles == null) {
            throw new UnauthorizedException();
        }

        if (roles == null || (!roles.contains(requireRole.value()) && !requireRole.value().equals("AUTH"))) {
            throw new SecurityException();
        }

        return joinPoint.proceed();}

}