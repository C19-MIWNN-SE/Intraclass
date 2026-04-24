package nl.miwnn.ch19.DaMaGe.IntraClass.config;

import nl.miwnn.ch19.DaMaGe.IntraClass.service.CustomUserDetailsService;
import nl.miwnn.ch19.DaMaGe.IntraClass.service.PersonService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @author My Linh Lu
 */
@Configuration
@EnableWebSecurity
public class IntraClassSecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/",
                                "/css/**",
                                "/img/**",
                                "/login",
                                "/webjars/**"
                        ).permitAll()
                        .requestMatchers(
                                "/cohort/overview",
                                "/cohort/**",
                                "/person-form",
                                "/user/**"
                        ).hasRole("TEACHER")
                        .requestMatchers(
                                "/person"
                        ).hasRole("STUDENT")
                        .anyRequest().authenticated()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/login", true)
                        .permitAll()
                );
        return http.build();
    }
}
