import org.junit.runner.RunWith;
import io.cucumber.junit.*;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/", glue = "com.architecture.microserviciobdd.testbdd",plugin = {"pretty", "html:target/site/index.html","json:target/cucumber.json"})
public class Runner {
}
