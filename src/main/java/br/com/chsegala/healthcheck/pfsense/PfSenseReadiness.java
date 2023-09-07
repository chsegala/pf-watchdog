package br.com.chsegala.healthcheck.pfsense;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;

import br.com.chsegala.service.pfsense.PfSenseService;
import jakarta.enterprise.inject.Default;
import jakarta.inject.Inject;

@Readiness
@Default
public class PfSenseReadiness implements HealthCheck {

    @Inject
    PfSenseService pfSenseService;

    @Override
    public HealthCheckResponse call() {
        if (pfSenseService.isLive() && pfSenseService.isReady()) {
            return HealthCheckResponse.up("pfsense");
        }
        return HealthCheckResponse.down("pfsense");
    }
}
