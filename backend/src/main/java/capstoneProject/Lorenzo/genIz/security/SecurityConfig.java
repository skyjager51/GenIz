package capstoneProject.Lorenzo.genIz.security;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http.authorizeHttpRequests(
            securityConfig -> {
                securityConfig.requestMatchers("/").permitAll();
                securityConfig.anyRequest().authenticated();
            }
        )
        //return 401 error code if user is not authenticated
        .exceptionHandling(exception -> exception
            .authenticationEntryPoint((request, response, authException) -> {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
            }))

        //when user successfully loging, redirect to frontend home
        .oauth2Login(oauth2 -> oauth2.defaultSuccessUrl("http://localhost:5173/", true))

        .formLogin(Customizer.withDefaults())

        //logout config, by hitting baseUrl/logout the user gets redirected to a default logout page.
        .logout(logout -> {
            logout.logoutSuccessUrl("http://localhost:5173/")
            .permitAll()
            .logoutSuccessHandler((request, response, authentication) -> {
                response.setStatus(HttpServletResponse.SC_OK);
            });
        })

        .csrf(csrf -> csrf.disable())

        //allow home page of GenIz to perform api calls 
        .cors(cors -> cors.configurationSource(request -> {
            CorsConfiguration config = new CorsConfiguration();
            config.setAllowedOrigins(List.of("http://localhost:5173/"));
            config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH"));
            config.setAllowedHeaders(List.of("*"));
            config.setAllowCredentials(true);
            return config;
        }))

        .build();
    }
}
