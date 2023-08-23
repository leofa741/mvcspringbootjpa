package com.lfa.spring.jpa.demolfa;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    @Bean
    public static BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureGlobal (AuthenticationManagerBuilder build) throws Exception{

       PasswordEncoder encoder = passwordEncoder();
      User.UserBuilder users = User.builder().passwordEncoder(encoder::encode);
      build.inMemoryAuthentication()
              .withUser(users.username("admind").password("123").roles("ADMIN","USER"))
              .withUser(users.username("userd").password("123").roles("USER"));
    }

    @Bean
    public static SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/listar/**") .permitAll()
                        .requestMatchers("/form/**") .permitAll()
                        .requestMatchers("/eliminar/**").hasAnyRole("ADMIN")
                        .requestMatchers("/uploads/**").permitAll()
                        .requestMatchers("/ver/**").hasAnyRole("ADMIN")
                        .requestMatchers("/factura/**").hasAnyRole("ADMIN")
                        .requestMatchers("/cliente/**").hasAnyRole("ADMIN")
                        .requestMatchers("/index/**").permitAll()
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/css/**").permitAll()
                        .requestMatchers("/js/**").permitAll()
                        .requestMatchers("/img/**").permitAll()
                        .requestMatchers("/error/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .permitAll()
                )
                .logout(LogoutConfigurer::permitAll)
                .exceptionHandling(exceptions -> exceptions
                        .accessDeniedPage("/error/403")
                );


        return http.build();
    }



}




