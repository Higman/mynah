package jp.ac.kagawalab.mynah.web.security;

import jp.ac.kagawalab.mynah.core.oauth2.service.FormUserDetailService;
import jp.ac.kagawalab.mynah.core.oauth2.service.MynahOAuth2UserService;
import jp.ac.kagawalab.mynah.core.oauth2.service.MynahOidcUserService;
import jp.ac.kagawalab.mynah.core.oauth2.service.OAuth2UserUtil;
import jp.ac.kagawalab.mynah.core.repository.UserRepository;
import jp.ac.kagawalab.mynah.web.mapper.UserAuthoritiesMapper;
import lombok.Data;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.List;

@Configuration
@EnableWebSecurity
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {

    private final UserRepository userRepository;
    private final OAuth2UserUtil oAuth2UserUtil;
    private final FormUserDetailService userDetailsService;

    public WebSecurityConfigurer(UserRepository userRepository, OAuth2UserUtil oAuth2UserUtil, FormUserDetailService userDetailsService) {
        this.userRepository = userRepository;
        this.oAuth2UserUtil = oAuth2UserUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/css/**", "/js/**", "/favicon*", "/resources/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // ログイン処理の認証ルールを設定
        http.authorizeRequests()
                /** Auth **/
                .antMatchers("/", "/login", "/admin/login", "/admin/sign_up", "/error").permitAll() // 認証なしでアクセス可能なパス
                .anyRequest().authenticated()// それ以外は認証が必要
                .and()
                /**  OAuthLogin **/
                .oauth2Login()
                .userInfoEndpoint()
                .userAuthoritiesMapper(new UserAuthoritiesMapper())
                .oidcUserService(oidcUserService())
                .userService(oAuth2UserService())
                .and()
                .defaultSuccessUrl("/") // ログイン成功時に遷移するURL
                .failureUrl("/login?error") // ログイン失敗時に遷移するURL
                .permitAll()
                .and()
                /** FormLogin for Admin user **/
                .formLogin()
                .loginPage("/admin/login")
                .loginProcessingUrl("/admin/sign_up")
                .defaultSuccessUrl("/")
                .failureUrl("/admin/login?error")
                .usernameParameter("admin_user_id")
                .passwordParameter("admin_password")
                .permitAll()
                .and()
                /** Logout **/
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .permitAll();
    }

    @Bean
    public OidcUserService oidcUserService() {
        return new MynahOidcUserService(userRepository, oAuth2UserUtil);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService() {
        return new MynahOAuth2UserService(userRepository, oAuth2UserUtil);
    }
}
