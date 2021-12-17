package course.kotlin.webfluxdemo.config

import course.kotlin.spring.exception.UnauthorisedException
import course.kotlin.webfluxdemo.domain.UsersService
import io.micrometer.core.instrument.config.validate.Validated
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.config.web.server.invoke
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Mono


@Configuration
@EnableWebFluxSecurity
class SecurityConfig {
    @Bean
    fun springSecurityFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        return http {
            csrf{ disable()}
            authorizeExchange {
                authorize(anyExchange, permitAll)
            }
            formLogin { }
            httpBasic { }
        }
    }

    @Bean
    fun userDetailsRepository(usersService: UsersService): ReactiveUserDetailsService {
        return ReactiveUserDetailsService { username: String ->
            usersService.findByUsername(username).cast(UserDetails::class.java)
                .switchIfEmpty(Mono.error(UsernameNotFoundException("Invalid username or password.")))
        } // Flux.fromIterable(users.findByUsername(username)).next().cast(UserDetails.class);
    }


//    @Bean
//    fun userDetailsService(): ReactiveUserDetailsService {
//        val userDetails = User.withDefaultPasswordEncoder()
//            .username("user")
//            .password("user")
//            .roles("ADMIN")
//            .build()
//        return MapReactiveUserDetailsService(userDetails)
//    }
}
