package jp.co.sample.emp_management.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		/*セキュリティー設定を無視するリクエスト設定
		 * img, css, js に対するアクセスはセキュリティー無視。
		*/
		web.ignoring().antMatchers("/css/**" ,"/img/**", "/js/**");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.formLogin()  // 作成したログインフォームをspring securityに適用。
					.loginPage("/") // ログインページを指定
					.permitAll(); //すべてのアクセスを許可。
		http.authorizeRequests()
					.antMatchers("/toInsert").permitAll()
					.antMatchers("/employee/showList").permitAll()
					.antMatchers("/employee/showDetail").permitAll()
					.antMatchers("/login").permitAll()
					.antMatchers("/insert").permitAll();
		
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
