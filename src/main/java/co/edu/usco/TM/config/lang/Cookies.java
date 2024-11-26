package co.edu.usco.TM.config.lang;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

@Component
public class Cookies {

    public void setLanguageCookies(String lang, HttpServletResponse response) {
        Cookie langCookie = new Cookie("lang", lang);
        langCookie.setMaxAge(60 * 60 * 24 * 30); // 30 días
        langCookie.setPath("/");
        response.addCookie(langCookie);
    }

    public String getLanguageFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("lang")) {
                    return cookie.getValue();
                }
            }
        }
        return "es";
    }

}
