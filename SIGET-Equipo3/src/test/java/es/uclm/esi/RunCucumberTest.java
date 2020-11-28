package es.uclm.esi;

import io.cucumber.junit.CucumberOptions;
import io.cucumber.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty"},features = "src/test/java/es/uclm/esi/features")
public class RunCucumberTest {
}

