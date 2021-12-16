package course.kotlin.webfluxdemo.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity.http
import org.springframework.security.config.web.server.invoke
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.web.server.SecurityWebFilterChain

@Configuration
@EnableWebFluxSecurity
class HelloWebfluxSecurityConfig {

//    @Bean
//    fun userDetailsService(): ReactiveUserDetailsService {
//        val userDetails = User.withDefaultPasswordEncoder()
//            .username("user")
//            .password("user")
//            .roles("USER")
//            .build()
//        return MapReactiveUserDetailsService(userDetails)
//    }

    @Bean
    fun springSecurityFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        return http {
            csrf { disable() }
            authorizeExchange {
                authorize(anyExchange, permitAll)
            }
//            formLogin { }
//            httpBasic { }
        }
    }
}
//@EnableWebSecurity
//class SecurityConfig : WebSecurityConfigurerAdapter() {
//
//    override fun configure(http: HttpSecurity) {
//        http {
//            csrf { disable() }
////            securityMatcher("/")
//            authorizeRequests {
//                authorize("/**", permitAll)
////                authorize(HttpMethod.GET,"/api/blogs", hasAnyRole(Role.READER.toString(), Role.AUTHOR.toString(), Role.ADMIN.toString()))
////                authorize(HttpMethod.POST,"/api/blogs", permitAll)
////                authorize("/login", permitAll)
////                authorize("/blogs", hasAnyRole(Role.READER.toString(), Role.AUTHOR.toString(), Role.ADMIN.toString()))
////                authorize("/blogs/blog-form", hasAnyRole(Role.AUTHOR.toString(), Role.ADMIN.toString()))
//            }
//        }
////        http.authorizeRequests {
////            it.antMatchers("/").hasAnyRole(Role.READER.toString(), Role.AUTHOR.toString(), Role.ADMIN.toString())
////                .and().formLogin()
////        }
//    }
//
//    private fun csrf(function: () -> Unit) {
//
//    }
//
//
////    @Bean
////    fun userDetailsService(usersRepository: UsersService): UserDetailsService {
////        return UserDetailsService {
////            usersRepository.findByUsername(it) ?: throw UsernameNotFoundException("Invalid username or password.")
////        }
////    }
//}
