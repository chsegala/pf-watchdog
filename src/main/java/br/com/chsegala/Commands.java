package br.com.chsegala;

import java.util.logging.Logger;

import br.com.chsegala.healthcheck.pfsense.PfSenseReadiness;
import br.com.chsegala.service.pfsense.PfSenseService;
import br.com.chsegala.service.pfsense.dao.InterfaceStatus;
import br.com.chsegala.service.pfsense.dao.ResponseWrapper;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;

@Path("/commands")
public class Commands {
    private static Logger log = Logger.getLogger(Commands.class.getName());

    @Inject
    PfSenseService pfSenseService;

    @Inject
    PfSenseReadiness pfSenseReadiness;

    @GET
    @Path("/reboot")
    public void reboot() {
        pfSenseService.reboot();
    }

    @GET
    @Path("/status")
    public String status(@QueryParam("isProbe") String isProbe) {
        log.info("System Status");
        return pfSenseService.status();
    }

    @GET
    @Path("/status/interface")
    public ResponseWrapper<InterfaceStatus> statusInterface() {
        log.info("Interface Status");
        return pfSenseService.statusInterfaces();
    }
}
