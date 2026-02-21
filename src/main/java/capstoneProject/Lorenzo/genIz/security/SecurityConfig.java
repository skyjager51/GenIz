package capstoneProject.Lorenzo.genIz.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

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
        .oauth2Login(Customizer.withDefaults())
        .formLogin(Customizer.withDefaults())
        //logout config, by hitting baseUrl/logout the user gets redirected to a default logout page.
        .logout(logout -> {
            logout.logoutSuccessUrl("/")
            .permitAll();
        })
        .build();
    }
}
