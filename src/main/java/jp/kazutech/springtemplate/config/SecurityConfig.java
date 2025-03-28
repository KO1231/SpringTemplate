package jp.kazutech.springtemplate.config;

import jakarta.servlet.DispatcherType;
import jp.kazutech.springtemplate.controller.login.LoginController;
import jp.kazutech.springtemplate.security.JwtAuthenticationFilter;
import jp.kazutech.springtemplate.service.MyUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

    private final MyUserDetailsService userDetailsService;
    private final PasswordEncoder encoder;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomConfig config;

    private final static String[] SWAGGER_PATHS = { //
            "/v3/api-docs/**", //
            "/swagger-ui/**", //
            "/swagger-ui.html" //
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // CORSの設定を適用
                // .cors(customizer -> customizer.configurationSource(corsConfigurationSource())) //

                // CSRF保護を無効
                .csrf(CsrfConfigurer::disable) //

                .headers(header -> header //
                        .frameOptions(FrameOptionsConfig::deny) //
                        .contentSecurityPolicy(csp -> csp.policyDirectives("default-src 'self';"))) //

                .authorizeHttpRequests(authorizeRequests -> {

                    /* 無条件 permitAll (全許可) */
                    authorizeRequests
                            // エラー関係のページは全許可
                            .dispatcherTypeMatchers(DispatcherType.ERROR).permitAll()
                            // public dirは全許可
                            .requestMatchers("/public/**").permitAll()
                            // その他publicファイルを指定 (全許可)
                            .requestMatchers("/robots.txt").permitAll();

                    // 個別ページの権限設定 (基本check関数で処理)
                    authorizeRequests.requestMatchers("/login").access(LoginController::check);


                    /* Config依存ページ */
                    // Swaggerの表示設定 (configでshowSwaggerがtrueの場合(原則devかlocal)のみ表示)
                    if (config.isShowSwagger()) {
                        authorizeRequests.requestMatchers(SWAGGER_PATHS).permitAll();
                    }

                    // その他のリクエストは全拒否
                    authorizeRequests.anyRequest().denyAll();

                }).sessionManagement(sessionManagement -> sessionManagement //
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //
                .userDetailsService(userDetailsService) //
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Autowired
    public void configureAuth(AuthenticationManagerBuilder auth) throws Exception {
        auth.eraseCredentials(true) //
                .userDetailsService(userDetailsService) //
                .passwordEncoder(encoder);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}
