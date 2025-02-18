package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
public class InternacionalizacionController {

    @Autowired
    private MessageSource messageSource;

    @GetMapping("/saludo")
    public String obtenerSaludo(@RequestHeader(name = "Accept-Language", required = false) String acceptLanguage) {
        // Extraer el primer idioma válido del encabezado Accept-Language
        Locale locale = extractLocaleFromHeader(acceptLanguage);
        return messageSource.getMessage("welcome.message", null, locale);
    }

    private Locale extractLocaleFromHeader(String acceptLanguage) {
        if (acceptLanguage == null || acceptLanguage.isEmpty()) {
            return Locale.getDefault(); // Usar el idioma por defecto si no hay encabezado
        }

        // Dividir el encabezado en partes y tomar el primer idioma válido
        String[] languages = acceptLanguage.split(",");
        for (String lang : languages) {
            // Eliminar cualquier peso (q=0.9, etc.) y espacios
            String localeStr = lang.split(";")[0].trim();
            // Crear un Locale a partir del idioma
            return Locale.forLanguageTag(localeStr);
        }

        // Si no se encuentra un idioma válido, usar el idioma por defecto
        return Locale.getDefault();
    }
}