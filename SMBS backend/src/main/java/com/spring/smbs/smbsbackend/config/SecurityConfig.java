import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    http
        .csrf(csrf -> csrf.disable())
        .cors(Customizer.withDefaults()) // ✅ IMPORTANT

        .authorizeHttpRequests(auth -> auth
            .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() // ✅ CRITICAL
            .requestMatchers("/login", "/registerUser").permitAll()
            .requestMatchers("/admin/**").hasRole("ADMIN")
            .requestMatchers("/cashier/**").hasRole("CASHIER")
            .anyRequest().authenticated()
        )

        .sessionManagement(session -> session
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        )

        .formLogin(form -> form.disable())
        .httpBasic(basic -> basic.disable());

    return http.build();
}
