import org.junit.jupiter.api.Test;
import us.abstracta.jmeter.javadsl.core.TestPlanStats;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

import static objects.OpencartObjects.*;
import static org.assertj.core.api.Assertions.assertThat;
import static us.abstracta.jmeter.javadsl.JmeterDsl.*;

public class ModularizedCheckoutLoadTest {

    @Test
    public void loadTest() throws IOException {

        TestPlanStats stats = testPlan(
                threadGroup()
                        .rampToAndHold(15,Duration.ofMinutes(1), Duration.ofMinutes(5))
                        .rampTo(0,Duration.ofMinutes(1))
                        .children(
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
                htmlReporter("html-report-" + Instant.now().toString().replace(":", "-"))
        ).run();
        assertThat(stats.overall().sampleTimePercentile99()).isLessThan(Duration.ofSeconds(2));
    }

}