package course.kotlin.spring.config

import course.kotlin.spring.dao.UsersRepository
import course.kotlin.spring.model.Role
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.web.server.ServerHttpSecurity.http
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.config.web.servlet.invoke
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.web.util.matcher.AntPathRequestMatcher

@Configuration
class SecurityConfig : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
//        http.authorizeRequests {
//            it.antMatchers("/").hasRole(Role.ADMIN.toString())
//                .and().formLogin()
//        }
        http {
            securityMatcher("/")
            authorizeRequests{
                authorize("/blogs", permitAll)
                authorize("/login", permitAll)
                authorize(HttpMethod.GET,"/blogs/blog-form", permitAll)
                authorize(HttpMethod.POST, "/blogs/blog-form", permitAll) //AntPathRequestMatcher("/blogs/blog-form", HttpMethod.POST.name)
            }
            formLogin {}
        }
    }
//


    @Bean
    fun userDetailsService(usersRepository: UsersRepository): UserDetailsService {
        return UserDetailsService {
            usersRepository.findByUsername(it)?: throw UsernameNotFoundException("Invalid username or password.")
        }
    }
}
