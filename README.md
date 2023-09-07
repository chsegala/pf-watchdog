# pf-watchdog
This project will try to connect to `pfsense` from time to time to validate the internet
connection, restarting it if no connection is present.

This was motivated by multiple connections drop from my ISP locking me out of my VPN.

## requirements
- pfsense
  - [jaredhendrickson13/pfsense-api](https://github.com/jaredhendrickson13/pfsense-api)
- something to run docker on
  - k8s
  - docker compose

## configuration
See the [quarkus configuration guide](https://quarkus.io/guides/config-reference) to check all
available methods of setting the configuration for the application.

The main configuration parameters are:
- QUARKUS_REST-CLIENT_PFSENSE_URL
  - pfsense's API utl (http://192.168.1.1/api/v1)
- PFSENSE_TOKEN
  - your pfsense API token
- PFSENSE_REBOOT-ENABLED=true
  - if should reboot the system, useful for debugging
- HEALTH_EVERY=60s
  - interval in wich the watchdog will test pfsense's connection