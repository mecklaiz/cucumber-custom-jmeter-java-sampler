package com.mecklaiz.samples.cukes.stepdefinitions;

import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StepDefs {
    @Given("^The following url:$")
    public void the_following_url(DataTable arg1) throws Throwable {
//        System.out.println(arg1.asList(String.class));
        List<Map<String, String>> args = arg1.asMaps(String.class, String.class);
        for (Map m : args) {
            System.out.println("List item: " + m);
            for (Object key : m.keySet()) {
                System.out.println("KEY: " + key);
                System.out.println("VALS: " + m.get(key));
            }
        }

        // Write code here that turns the phrase above into concrete actions
        // For automatic transformation, change DataTable to one of
        // List<YourType>, List<List<E>>, List<Map<K,V>> or Map<K,V>.
        // E,K,V must be a scalar (String, Integer, Date, enum etc)

        WebDriver driver = new HtmlUnitDriver();
        driver.navigate().to("https://www.google.com");
        driver.findElement(By.cssSelector("input[title=\"Google Search\"]")).sendKeys("cucumber");
        driver.findElement(By.cssSelector("input[value=\"Google Search\"]")).click();
        System.out.println(driver.getTitle() + " " + driver.findElement(By.id("resultStats")).getText());



    }

    @Given("^There are (\\d+) concurrent users using node$")
    public void there_are_concurrent_users_using_node(int arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        System.out.println("I am here");
    }

    @Then("^The response time is less than (\\d+)ms$")
    public void the_response_time_is_less_than_ms(int arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        System.out.println("I am groot");
    }

}
