package br.com.chsegala.service.pfsense;

import java.util.Objects;

import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

import br.com.chsegala.config.PfSenseConfig;
import br.com.chsegala.service.pfsense.dao.InterfaceStatus;
import br.com.chsegala.service.pfsense.dao.ResponseWrapper;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class PfSenseService {
    private static final Logger log = Logger.getLogger(PfSenseService.class);

    @Inject
    @RestClient
    PfSenseServiceClient client;

    @Inject
    PfSenseConfig config;

    public void reboot() {
        if (config.rebootEnabled()) {
            log.warn("rebooting...");
            client.reboot();
        } else {
            log.info("rebooting is disabled...");
        }
    }

    public Uni<Boolean> rebootAwait() {
        reboot();

        return Uni.createFrom().deferred(
                () -> {
                    int count = 0;
                    while (!isLive() && count++ < 30) {
                        try {
                            log.debug("awaiting reboot...");
                            Thread.sleep(10000);
                        } catch (InterruptedException e) {
                            log.warn(e);
                        }
                    }
                    boolean islive = isLive();
                    log.infov("awaiting reboot finished with status \"{0}\"", islive);
                    return Uni.createFrom().item(islive);
                });
    }

    public String status() {
        return client.status();
    }

    public ResponseWrapper<InterfaceStatus> statusInterfaces() {
        return client.statusInterface();
    }

    @Timeout(value = 1000)
    public boolean isLive() {
        try {
            return Objects.nonNull(status());
        } catch (Exception e) {
            return false;
        }
    }

    @Timeout(value = 5000)
    public boolean isReady() {
        return statusInterfaces()
                .getData()
                .stream()
                .filter(i -> config.interfaces().contains(i.getName()))
                .allMatch(i -> i.isHealthy());
    }
}
