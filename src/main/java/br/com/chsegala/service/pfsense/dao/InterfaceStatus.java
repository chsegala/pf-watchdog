package br.com.chsegala.service.pfsense.dao;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.quarkus.runtime.util.StringUtil;
import lombok.Data;

@Data
public class InterfaceStatus {
    private String name;
    private String descr;
    private String hwif;
    private boolean enable;
    @JsonProperty("if")
    private String interf;
    private String ipaddr;
    private String status;
    private String gateway;

    public boolean isHealthy() {
        if (!enable) {
            return true;
        }
        return "up".equals(status) &&
                !StringUtil.isNullOrEmpty(ipaddr) &&
                !StringUtil.isNullOrEmpty(gateway);
    }
}
