package com.smart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class MyConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public UserDetailsService getUserDetailService() {
		return new UserDetailsServiceImpl();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(this.getUserDetailService());
		authenticationProvider.setPasswordEncoder(this.passwordEncoder());
		return authenticationProvider;
	}

	// configure method
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(this.authenticationProvider());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		String REDIRECT_VIEW = "/user/index";

		http.authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN").antMatchers("/user/**").hasRole("USER")
				.antMatchers("/**").permitAll().and().formLogin().loginPage("/signin").loginProcessingUrl("/do_login")
				.defaultSuccessUrl(REDIRECT_VIEW).and().csrf().disable();
	}

}
