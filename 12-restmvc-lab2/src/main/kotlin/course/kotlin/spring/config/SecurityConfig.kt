package course.kotlin.spring.config

import course.kotlin.spring.dao.UsersRepository
import course.kotlin.spring.domain.UsersService
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

@Configuration
class SecurityConfig : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        http {
            csrf { disable() }
//            securityMatcher("/")
            authorizeRequests {
//                authorize("/**", permitAll)
                authorize(HttpMethod.GET,"/api/blogs",
                    hasAnyRole(Role.READER.toString(), Role.AUTHOR.toString(), Role.ADMIN.toString()))
                authorize(HttpMethod.POST,"/api/blogs", permitAll)
//                authorize("/login", permitAll)
//                authorize("/blogs", hasAnyRole(Role.READER.toString(), Role.AUTHOR.toString(), Role.ADMIN.toString()))
//                authorize("/blogs/blog-form", hasAnyRole(Role.AUTHOR.toString(), Role.ADMIN.toString()))
            }
        }
//        http.authorizeRequests {
//            it.antMatchers("/").hasAnyRole(Role.READER.toString(), Role.AUTHOR.toString(), Role.ADMIN.toString())
//                .and().formLogin()
//        }
    }


    @Bean
    fun userDetailsService(usersRepository: UsersService): UserDetailsService {
        return UserDetailsService {
            usersRepository.findByUsername(it) ?: throw UsernameNotFoundException("Invalid username or password.")
        }
    }
}
