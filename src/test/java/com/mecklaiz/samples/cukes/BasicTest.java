package com.mecklaiz.samples.cukes;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertTrue;

@RunWith(Cucumber.class)
@CucumberOptions(  monochrome = true,

        // TODO: Fix this path so that it is more data driven, this won't work on anyone else's machine
        // C:\Users\zmecklai\git\github\cucumber-custom-jmeter-java-sampler\src\test\java\com\mecklaiz\samples\cukes\test1.feature
        features = "src\\test\\java\\com\\mecklaiz\\samples\\cukes\\test1.feature",
        format = { "pretty",
                "html:cucumber-html-reports",
                "json:cucumber-html-reports/cucumber.json" },
        dryRun = false,
        glue = {"com.mecklaiz.samples.cukes.stepdefinitions"} )

public class BasicTest {
    @Test
    public void test() {
        assertTrue(true);
    }

}
