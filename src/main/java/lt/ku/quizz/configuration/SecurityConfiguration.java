package lt.ku.quizz.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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
		
		
		auth
			.inMemoryAuthentication()
				.withUser("gediminas").password(bc.encode("LabasRytas")).roles("admin", "user")
				.and()
				.withUser("jonas").password(bc.encode("LabasVakaras")).roles("user");
			
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
				.antMatchers("/language*").hasAnyRole("admin")
				.antMatchers("/user/theme*").hasAnyRole("admin")
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

	/*@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}*/
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
//        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
//    }
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    

    

}