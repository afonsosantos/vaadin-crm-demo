package com.vaadin.tutorial.crm.it;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import com.vaadin.testbench.IPAddress;
import com.vaadin.testbench.ScreenshotOnFailureRule;
import com.vaadin.testbench.parallel.ParallelTest;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.slf4j.LoggerFactory;

public abstract class AbstractTest extends ParallelTest {

    private static final String SERVER_HOST = IPAddress.findSiteLocalAddress();
    private static final int SERVER_PORT = 8080;
    private final String route;

    protected AbstractTest(String route) {
        this.route = route;
    }

    @BeforeClass
    public static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @Rule
    public ScreenshotOnFailureRule rule = new ScreenshotOnFailureRule(this, true);

    @Before
    public void setup() throws Exception {
        super.setup();
        getDriver().get(getURL(route)); // opens the given URL in the browser
    }

    private static String getURL(String route) {
        return String.format("http://%s:%d/%S", SERVER_HOST, SERVER_PORT, route);
    }

    static {
        Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        root.setLevel(Level.INFO);
    }

}
