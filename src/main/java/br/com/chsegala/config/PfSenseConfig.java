package br.com.chsegala.config;

import java.util.List;

import io.smallrye.config.ConfigMapping;

@io.quarkus.runtime.annotations.StaticInitSafe
@ConfigMapping(prefix = "pfsense") // Prefix for properties
public interface PfSenseConfig {
    String token();

    Boolean rebootEnabled();

    List<String> interfaces();
}