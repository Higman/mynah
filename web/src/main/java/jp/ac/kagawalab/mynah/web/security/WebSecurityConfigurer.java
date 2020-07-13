package jp.ac.kagawalab.mynah.web.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/css/**", "/resources/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // ログイン処理の認証ルールを設定
        http.authorizeRequests()
                .antMatchers("/", "/login", "/error").permitAll() // 認証なしでアクセス可能なパス
                .anyRequest().authenticated() // それ以外は認証が必要
                .and()
                .oauth2Login()
                .defaultSuccessUrl("/") // ログイン成功時に遷移するURL
                .failureUrl("/login?error") // ログイン失敗時に遷移するURL
                .permitAll()
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutUrl("/logout")
                .permitAll();
    }
}
