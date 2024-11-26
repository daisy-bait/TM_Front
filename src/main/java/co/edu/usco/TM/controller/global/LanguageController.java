package co.edu.usco.TM.controller.global;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Locale;

@Configuration
public class LanguageController {

    @RequestMapping("/change-language")
    public String changeLanguage(String language, HttpServletRequest request, HttpServletResponse response) {

        Cookie languageCookie = new Cookie("lang", language);
        languageCookie.setMaxAge(60 * 60 * 24 * 30);
        languageCookie.setPath("/");
        response.addCookie(languageCookie);

        Locale locale = new Locale(language);
        LocaleContextHolder.setLocale(locale);

        return "redirect:/" + request.getRequestURI();
    }

}
