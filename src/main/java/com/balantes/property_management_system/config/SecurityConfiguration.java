package com.balantes.property_management_system.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        // Configuring which pages needs that the user is logged in and which pages
        // are open to the public (user is not logged in)
        httpSecurity.authorizeHttpRequests(
                authorize -> {
                    authorize.requestMatchers(
                            "/images/**",
                            "/css/**",
                            "/js/**"
                    ).permitAll();
                    // Allows all users, including unauthenticated users, to access the home, login, and registration pages.
                    authorize.requestMatchers("/", "/authenticate", "/register").permitAll();
                    // Restricts the user pages and rest endpoints to ADMIN users
                    //            authorize.requestMatchers("/users/**",
                    // "/api/users/**").hasRole(USER_TYPE.ADMIN.getType());
                    authorize.requestMatchers("/users/**").hasRole(USER_TYPE.ADMIN.getType());
                    // Any other pages, requires that user is currently logged in
                    authorize.anyRequest().authenticated();
                });

        // Configuring our login page
        httpSecurity.formLogin(
                form ->
                        form
                                // configure that our home page is also our login page
                                .loginPage("/")
                                // configure that the url that will process the login will be `/authenticate`
                                .loginProcessingUrl("/authenticate")
                                // if the user successfully logs in, redirect to `logbook list page`
//                .defaultSuccessUrl("/homepage/")
                                .successHandler(new CustomAuthenticationSuccessHandler())
                                .failureUrl("/?error=true")
                                .permitAll());

        /*
         * Handle API requests with Basic Authentication
         *
         * Note:
         * This is not the standard authentication, and
         *  this is not the authentication na ginagamit sa industry.
         * This is just a basic authentication for learning purposes.
         * */
        httpSecurity.httpBasic(
                (httpBasic) -> httpBasic.authenticationEntryPoint(new RestAuthenticationEntryPoint()));

        // Configures how we manually handle access denied exceptions
        httpSecurity.exceptionHandling(
                exception -> exception.accessDeniedHandler(new CustomAccessDeniedHandler()));

        // Additional config
        // You don't need this for now
        httpSecurity.csrf(AbstractHttpConfigurer::disable).cors(AbstractHttpConfigurer::disable);

        // Pass all custom configs that we made to Spring security
        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}

