package it.uniroma3.catering.authentication;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static it.uniroma3.catering.model.Credentials.ADMIN_ROLE;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class AuthConfiguration extends WebSecurityConfigurerAdapter {
	
	/**used to access the DB to get the Credentials**/
    @Autowired
    DataSource datasource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                /** authorization **/
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/", "/index", "/login", "/register", "/logout", "/css/**", "/images/**").permitAll()
                .antMatchers(HttpMethod.POST, "/login", "/registration/validate").permitAll()
                .antMatchers(HttpMethod.GET, "/admin/**").hasAnyAuthority(ADMIN_ROLE)
                .antMatchers(HttpMethod.POST, "/admin/**").hasAnyAuthority(ADMIN_ROLE)
                .anyRequest().authenticated()

                /** login **/
                .and().formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/default")
                /**TODO inserire oauth login **/
                
                /** logout **/
                .and().logout()
    			//il logout è attivato con una richiesta GET a "/logout"
    			.logoutUrl("/logout")
    			//in caso di successo, si viene reindirizzati all'index
    			.logoutSuccessUrl("/")
    			.invalidateHttpSession(true)
    			.deleteCookies("JSESSIONID")
    			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
    			.clearAuthentication(true).permitAll();
    }

    /**
     * This method provides the SQL queries to get username and password.
     */
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
        		.jdbcAuthentication()
                .dataSource(this.datasource)
                .authoritiesByUsernameQuery("SELECT username, role FROM credentials WHERE username=?")
                .usersByUsernameQuery("SELECT username, password, 1 as enabled FROM credentials WHERE username=?");
    }

    /**
     * This method defines a "passwordEncoder" Bean.
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
