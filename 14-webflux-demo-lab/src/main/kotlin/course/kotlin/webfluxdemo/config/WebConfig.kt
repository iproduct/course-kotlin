package course.kotlin.webfluxdemo.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.method.HandlerTypePredicate
import org.springframework.web.reactive.config.PathMatchConfigurer
import org.springframework.web.reactive.config.WebFluxConfigurer

@Configuration
//@EnableWebFlux
class WebConfig : WebFluxConfigurer{
    override fun configurePathMatching(configurer: PathMatchConfigurer) {
        configurer
            .setUseCaseSensitiveMatch(true)
            .setUseTrailingSlashMatch(true)
            .addPathPrefix("/api",
                HandlerTypePredicate.forAnnotation(RestController::class.java))
    }
}
