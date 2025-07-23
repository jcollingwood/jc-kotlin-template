package jc.kotlin.template.server.config

import io.github.oshai.kotlinlogging.KotlinLogging
import io.ktor.server.application.Application
import jc.kotlin.template.server.session.SessionService
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.hours

fun Application.configureSessionPruning(sessionService: SessionService) {
    val log = KotlinLogging.logger {}
    launch {
        while (true) {
            try {
                log.info { "starting session pruning job..." }
                sessionService.pruneExpiredSessions()
                log.info { "completed session pruning job..." }
                delay(PRUNE_DURATION_HOURS.hours) // Run daily
            } catch (e: Exception) {
                log.error(e) { "Error during data pruning" }
            }
        }
    }
}
