package br.com.chsegala.healthcheck.pfsense;

import java.util.Objects;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;

import br.com.chsegala.service.pfsense.PfSenseService;
import jakarta.inject.Inject;

@Liveness
public class PfSenseLiveness implements HealthCheck {

    @Inject
    PfSenseService pfSenseService;

    @Override
    public HealthCheckResponse call() {
        try {
            String status = pfSenseService.status();
            if (Objects.nonNull(status)) {
                return HealthCheckResponse.up("pfsense");
            }
        } catch (Exception e) {

        }
        return HealthCheckResponse.down("pfsense");
    }
}