package com.otakuma.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.otakuma.utils.Parse;
import com.otakuma.utils.Utils;
import com.otakuma.utils.Validateur;

@Configuration
public class UtilsConfig {
    
    @Bean
    public Validateur createValidateur() { return new Validateur(); }
    
    @Bean
    public Parse createParse() { return new Parse(); }

    @Bean
    public Utils createUtils() { return new Utils(); }
    
}

