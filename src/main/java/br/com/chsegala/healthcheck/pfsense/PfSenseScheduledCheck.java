package br.com.chsegala.healthcheck.pfsense;

import java.time.Duration;

import org.eclipse.microprofile.faulttolerance.Retry;
import org.jboss.logging.Logger;

import br.com.chsegala.service.pfsense.PfSenseService;
import io.quarkus.scheduler.Scheduled;
import io.quarkus.scheduler.Scheduled.ConcurrentExecution;
import jakarta.inject.Inject;

public class PfSenseScheduledCheck {
    class NotReadyException extends RuntimeException {

    }

    private static Logger log = Logger.getLogger(PfSenseScheduledCheck.class);

    @Inject
    PfSenseService service;

    @Retry
    public void readyCheck() {
        if (!service.isLive())
            return;

        if (!service.isReady()) {
            log.warn("!!! not ready !!!");
            throw new NotReadyException();
        }
        log.info("pfsense is ready :)");
    }

    @Scheduled(every = "{health.every}", concurrentExecution = ConcurrentExecution.SKIP, identity = "health-schedule")
    public void scheduled() {
        try {
            log.debug("scheduled health check...");
            readyCheck();
        } catch (NotReadyException exp) {
            service.rebootAwait().await().atMost(Duration.ofMinutes(3));
        } catch (Exception exp) {
            log.error("health check error", exp);
        }
    }
}
