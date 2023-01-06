package lt.ku.quizz.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import lt.ku.quizz.services.UserService;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{    

	@Autowired
	UserService userService;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		BCryptPasswordEncoder bc=new BCryptPasswordEncoder();
		
		auth
			.userDetailsService(this.userService)
			.passwordEncoder(bc);
			
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.antMatchers("/user/").permitAll()
				.antMatchers("/user/new").permitAll()
				.antMatchers("/").permitAll()	
				.antMatchers("/quizz/").permitAll()
				.antMatchers("/quizz/play/").permitAll()
				.antMatchers("/login*").permitAll()
				.antMatchers("/user/theme").hasAnyRole("admin")
				.antMatchers("/user/language").hasAnyRole("admin")
				.anyRequest().authenticated()
		
		.and()
			.formLogin()
				.loginPage("/login")
				.defaultSuccessUrl("/")
				.failureUrl("/login-error")
		.and()
			.logout()
				.logoutUrl("/logout")
				.logoutSuccessUrl("/");
	}
	
	@Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/images/**", "/css/**");
    }
	
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}