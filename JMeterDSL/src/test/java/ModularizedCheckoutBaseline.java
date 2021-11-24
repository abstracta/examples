import org.junit.jupiter.api.Test;
import us.abstracta.jmeter.javadsl.core.TestPlanStats;

import java.io.IOException;
import java.time.Duration;

import static objects.OpencartObjects.*;
import static org.assertj.core.api.Assertions.assertThat;
import static us.abstracta.jmeter.javadsl.JmeterDsl.*;

public class ModularizedCheckoutBaseline {

    @Test
    public void baselineTest() throws IOException {

        TestPlanStats stats = testPlan(
                threadGroup(1, 1,
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
                resultsTreeVisualizer()
        ).run();
        assertThat(stats.overall().sampleTimePercentile99()).isLessThan(Duration.ofSeconds(2));
    }

}