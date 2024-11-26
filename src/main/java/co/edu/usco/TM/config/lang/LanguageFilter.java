package co.edu.usco.TM.config.lang;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Locale;

@Component
public class LanguageFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Obtén las cookies de la solicitud
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("lang".equals(cookie.getName())) {
                    String language = cookie.getValue();
                    // Cambiar el idioma globalmente usando LocaleContextHolder
                    Locale locale = new Locale(language);
                    LocaleContextHolder.setLocale(locale);
                    break;
                }
            }
        }

        // Continua con el ciclo de la solicitud
        filterChain.doFilter(request, response);
    }

}
