import org.junit.jupiter.api.Test;
import us.abstracta.jmeter.javadsl.blazemeter.BlazeMeterEngine;
import us.abstracta.jmeter.javadsl.core.TestPlanStats;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.TimeoutException;

import static objects.OpencartObjects.*;
import static org.assertj.core.api.Assertions.assertThat;
import static us.abstracta.jmeter.javadsl.JmeterDsl.*;

public class ModularizedCheckoutWithInfluxGrafanaAndBlazemeter {

    @Test
    public void influxAndBlazemeterTest() throws IOException, InterruptedException, TimeoutException {

        TestPlanStats stats = testPlan(
                threadGroup(1, 15,
                        goToOpencart,
                        clickProduct,
                        addToCart,
                        viewCart,
                        goToCheckout,
                        selectGuestCheckout,
                        selectCountry,
                        sendBillingDetails,
                        sendShippingMethod,
                        sendPaymentMethod,
                        confirmOrder
                ),
                influxDbListener("http://localhost:8086/write?db=jmeter")
        ).runIn(new BlazeMeterEngine("KeyId:KeySecret")
                .testName("DSL test")
                .totalUsers(10)
                .rampUpFor(Duration.ofMinutes(1))
                .holdFor(Duration.ofMinutes(5))
                .threadsPerEngine(100)
                .testTimeout(Duration.ofMinutes(10)));
        assertThat(stats.overall().sampleTimePercentile99()).isLessThan(Duration.ofSeconds(2));
    }

}