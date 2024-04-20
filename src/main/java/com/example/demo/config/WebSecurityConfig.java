
package com.example.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;

@Configuration
@EnableWebMvc
@ComponentScan("com.spring.boot.rocks.*")
public class WebSecurityConfig implements WebMvcConfigurer {
    /**
     * Конфигурира местоположенията на ресурсите за статично съдържание.
     * Този метод добавя обработчици за ресурси, които насочват към статични файлове
     * лежащи в класпат-а под директорията /static.
     *
     * @param registry Регистърът, към който се добавят обработчиците на ресурси.
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");
    }
    /**
     * Конфигурира веригата от филтри за сигурност, определяща правила за достъп.
     * Дефинира правила за авторизация за различни пътища в приложението, както и
     * конфигурация за формата за вход.
     *
     * @param http Обект HttpSecurity за настройка на сигурността.
     * @return Изградената верига от филтри за сигурност.
     * @throws Exception при грешка в конфигурацията на сигурността.
     */
    @Bean
    SecurityFilterChain web(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/users","/addProducts","/edit")
                        .hasAuthority("Admin")
                        .requestMatchers("/login","/index","/register","/register/save").permitAll()
                        .requestMatchers("/images/**").permitAll()
                        .anyRequest().authenticated()
                ).formLogin((form) -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/frontPage",true)
                );

        return http.build();
    }
   /**
    * Бийн, който предоставя кодер за пароли използвайки BCrypt алгоритъм.
    * Използва се за хеширане на пароли по безопасен начин.
    *
    * @return BCryptPasswordEncoder инстанция.
    */
   @Bean
   public PasswordEncoder passwordEncoder() {
       return new BCryptPasswordEncoder();
   }
   /**
    * Конфигурира и създава източник на данни базиран на свойства зададени
    * с префикс 'spring.datasource'.
    *
    * @return Инстанция на DataSource според зададените конфигурации.
    */
   @Bean
   @ConfigurationProperties(prefix = "spring.datasource")
   public DataSource dataSource() {
       return DataSourceBuilder.create().build();
   }
    /**
     * Създава служба за детайли на потребителите, която използва JDBC за извличане
     * на данни за потребителите от базата данни.
     *
     * @param dataSource Източникът на данни, от който JdbcDaoImpl ще извлича информация за потребителите.
     * @return JdbcDaoImpl инстанция конфигурирана за извличане на потребителска информация.
     */
    @Bean
    public UserDetailsService users(DataSource dataSource) {
        JdbcDaoImpl service = new JdbcDaoImpl();
        service.setDataSource(dataSource);
        service.setUsersByUsernameQuery("select email, password, true from users where email = ? ");
        service.setAuthoritiesByUsernameQuery("select users.email , users.role from users"
                + " where users.email = ?");
        return service;
    }
}