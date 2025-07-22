package jc.kotlin.template.server.config

import io.ktor.server.application.Application
import jc.kotlin.template.server.session.SessionService
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import mu.two.KotlinLogging
import kotlin.time.Duration.Companion.hours

fun Application.configureSessionPruning(sessionService: SessionService) {
    val log = KotlinLogging.logger {}
    launch {
        while (true) {
            try {
                log.info("starting session pruning job...")
                sessionService.pruneExpiredSessions()
                log.info("completed session pruning job...")
                delay(1.hours) // Run daily
            } catch (e: Exception) {
                log.error("Error during data pruning", e)
                delay(1.hours) // Retry after 1 hour on error
            }
        }
    }
}
