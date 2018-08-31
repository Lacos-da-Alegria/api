package com.lacosdaalegria.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /*
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private AuthService authService;
    */

    private static final String[] PUBLIC_MATCHERS = {
    };

    private static final String[] PUBLIC_MATCHERS_GET = {
            // Swagger endpoints
            "/",
            "/swagger-resources/**",
            "/swagger-ui**",
            "/webjars/springfox-swagger-ui/**",
            "/v2/api-docs/**"
    };

    private static final String[] PUBLIC_MATCHERS_POST = {
    };

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        /**
         * Esse método ativa a configuração do corsConfigurationSource() caso seja implementado.
         */
        httpSecurity.cors().and()
            /*
             * Desabilitando proteção CSRF pois não iremos manter seção no serviço então torna-se desnecessário.
             */
            .csrf().disable();

        httpSecurity.authorizeRequests()
            .antMatchers(PUBLIC_MATCHERS).permitAll()
            .antMatchers(HttpMethod.GET, PUBLIC_MATCHERS_GET).permitAll()
            .antMatchers(HttpMethod.POST, PUBLIC_MATCHERS_POST).permitAll()
            .anyRequest().authenticated();

        /**
         * Adicionando o filtro de autenticacao e autorizacao
         */
        /*
        httpSecurity.addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtUtil, authService));
        httpSecurity.addFilter(new JWTAuthorizationFilter(authenticationManager(), jwtUtil, userDetailsService));
        */
        /*
         * Garantir que o serviço não irá guardar seção.
         */
        httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }


    /**
     * Informando quem é meu UserDetailsService e PasswordEnconder para autenticacao
     *
     * @param authenticationManagerBuilder
     * @throws Exception
     */
    /*
    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }
    */

    /**
     * Libera cross-origin requests
     *
     * @return CorsConfigurationSource
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();

        // Liberando PUT e DELETE bloqueado por padrão
        corsConfiguration.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE", "OPTIONS"));

        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return urlBasedCorsConfigurationSource;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*
    @Bean
    public EmailService emailService() {
        return new SmtpEmailService();
    }
    */
}
