package br.com.chsegala.service.pfsense;

import org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import br.com.chsegala.service.pfsense.dao.InterfaceStatus;
import br.com.chsegala.service.pfsense.dao.ResponseWrapper;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;

@RegisterRestClient(configKey = "pfsense")
@ClientHeaderParam(name = "Authorization", value = "${pfsense.token}")
public interface PfSenseServiceClient {
    @POST
    @Path("/system/reboot")
    void reboot();

    @GET
    @Path("/status/system")
    @Consumes(MediaType.APPLICATION_JSON)
    String status();

    @GET
    @Path("/status/interface")
    @Consumes(MediaType.APPLICATION_JSON)
    ResponseWrapper<InterfaceStatus> statusInterface();
}
