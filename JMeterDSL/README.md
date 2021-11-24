# JMeterDSL Template using Maven and the [Opencart](http://opencart.abstracta.us/) website

## Test scripts

In this template we present examples of a performance script with and without modularization, different loads and using InfluxDB for reporting and Blazemeter.

The test flow consists of the following steps:

1. Entering the [Opencart](http://opencart.abstracta.us/) website.
2. Adding a product to the cart.
3. Buying the product as a guest.

### Checkout

In this script we show an example of a simple script of the user flow described before.
It includes de parametrization of the product and the extraction of its id, which is used in following requests.
This script also features timers to mimic real user behaviour, and assertions in the responses.

### Modularized Checkout Baseline

This script is an improvement of the previous one. It only contains the names of the steps to be executed in the test.
The objects are in the file OpencartObjects. It contains the same requests but separated by user action.

### Modularized Checkout Load Test

Now we have modified the script to accomplish a higher load. It ramps up during one minute, then holds the load during five and ramps down to zero users in one minute.

### Modularized Checkout With InfluxDB Grafana And Blazemeter

In this last example we used InfluxDB and Grafana for reporting and Blazemeter for cloud running.
It's as easy as adding the line influxDbListener("http://localhost:8086/write?db=jmeter") with your influxDB url.

If you want to try it locally you can just run the command docker-compose up using the docker file in this repository, and you will be able to see the live results at http://localhost:3000.