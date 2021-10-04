package com.example.plugins

import com.netflix.spectator.atlas.AtlasConfig
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.metrics.micrometer.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.micrometer.atlas.AtlasMeterRegistry
import io.micrometer.core.instrument.Clock
import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.core.instrument.binder.jvm.JvmGcMetrics
import io.micrometer.core.instrument.binder.jvm.JvmMemoryMetrics
import io.micrometer.core.instrument.binder.system.ProcessorMetrics
import io.micrometer.core.instrument.distribution.DistributionStatisticConfig
import io.micrometer.prometheus.*
import java.time.Duration


fun Application.configureMonitoring() {
//    val atlasConfig: AtlasConfig = object : AtlasConfig {
//        override fun step(): Duration {
//            return Duration.ofSeconds(60)
//        }
//
////        override fun uri(): String {
////            return "http://localhost:7101/api/v1/publish"
////        }
//
//        override fun enabled() = true
//
//        override fun autoStart() = true
//
//        override fun commonTags() = mapOf(Pair("metric", "metric"))
//
//        override fun get(k: String): String? {
//            return null // accept the rest of the defaults
//        }
//    }
//    val appMicrometerRegistry: MeterRegistry = AtlasMeterRegistry(atlasConfig, Clock.SYSTEM)
//    registry = appMicrometerRegistry

    val appMicrometerRegistry = PrometheusMeterRegistry(PrometheusConfig.DEFAULT)
    install(MicrometerMetrics) {
        registry = appMicrometerRegistry
        distributionStatisticConfig = DistributionStatisticConfig.Builder()
            .percentilesHistogram(true)
            .maximumExpectedValue(Duration.ofSeconds(20).toNanos().toDouble())
            .serviceLevelObjectives(
                Duration.ofMillis(100).toNanos().toDouble(),
                Duration.ofMillis(500).toNanos().toDouble()
            )
            .build()
        meterBinders = listOf(
            JvmMemoryMetrics(),
            JvmGcMetrics(),
            ProcessorMetrics()
        )
    }
    routing {
        get("/metrics") {
            call.respond(appMicrometerRegistry.scrape())
        }
    }

}
