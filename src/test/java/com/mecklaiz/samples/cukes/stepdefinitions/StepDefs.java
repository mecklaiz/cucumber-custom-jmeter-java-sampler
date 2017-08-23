package com.mecklaiz.samples.cukes.stepdefinitions;

import com.mecklaiz.httpserver.jetty.ZServer;
import com.mecklaiz.jmeter.sampler.ZSeleniumSampler;
import com.mecklaiz.jmeter.util.ZJMeterUtils;
import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.jmeter.engine.StandardJMeterEngine;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StepDefs {

    static WebDriver driver;

    static Map<String, URL> dataMap = new HashMap<>();
    static URL fullurl;
    static boolean runOnce = false;

    @Given("^The following url:$")
    public void the_following_url(DataTable arg1) throws Throwable {

        if (!runOnce) {
            ZServer.startJetty();

            Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF);

            List<Map<String, String>> args = arg1.asMaps(String.class, String.class);

            for (Map m : args) {
                String url = (String) m.get("URL");
                String id = (String) m.get("ID");
                int port = Integer.parseInt((String) m.get("PORT"));
                String path = (String) m.get("PATH");

                fullurl = new URL("http", url, port, path);
                dataMap.put(id, fullurl);
                System.out.println("Putting: " + id + " " + fullurl.toString());

            }
            driver = new HtmlUnitDriver();
            runOnce = true;
        }
    }

    @Given("^The ID (\\S+)$")
    public void test_case_id(String arg1) throws Throwable {

        fullurl = dataMap.get(arg1);
        System.out.println("I am here " + arg1 + " " + fullurl);

    }

    @When("^I load the page$")
    public void i_load_the_page() throws Throwable {
        String classname = "com.mecklaiz.jmeter.sampler.ZSeleniumSampler";

        StandardJMeterEngine jmeter = ZJMeterUtils.doStandardJMeterSetup();
        ZJMeterUtils.runJMeter(jmeter,classname, 2);

        driver.navigate().to(fullurl.toString());
        System.out.println("CURRENT URL: " + driver.getCurrentUrl());

    }

    @Then("^The response time is less than (\\d+)ms$")
    public void the_response_time_is_less_than_ms(int arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
//        System.out.println("CURRENT URL: " + driver.getCurrentUrl());
    }

}
