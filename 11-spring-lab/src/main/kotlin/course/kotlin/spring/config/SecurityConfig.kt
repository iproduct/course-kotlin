package course.kotlin.spring.config

import course.kotlin.spring.dao.UsersRepository
import course.kotlin.spring.domain.UsersService
import course.kotlin.spring.model.Role
import org.apache.coyote.http11.Constants.a
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.web.server.ServerHttpSecurity.http
import org.springframework.security.core.userdetails.UserDetailsService

@Configuration
class SecurityConfig : WebSecurityConfigurerAdapter(){
    override fun configure(http: HttpSecurity) {
        http.authorizeRequests {
            it.antMatchers("/").hasRole(Role.ADMIN.toString())
                .and().formLogin()
        }
    }

    @Bean
    fun userDetailsService(usersRepository: UsersRepository): UserDetailsService {
        return UserDetailsService {
            usersRepository.findByUsername(it)
        }
    }
}
