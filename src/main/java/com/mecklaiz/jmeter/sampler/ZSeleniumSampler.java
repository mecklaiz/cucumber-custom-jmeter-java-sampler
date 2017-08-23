package com.mecklaiz.jmeter.sampler;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.Interruptible;
import org.apache.jmeter.samplers.SampleResult;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import java.io.Serializable;
import java.util.Iterator;
import java.util.function.Function;

public class ZSeleniumSampler extends AbstractJavaSamplerClient implements Serializable, Interruptible {

    public static WebDriver driver;


    public ZSeleniumSampler () {
        super();
    }

    @Override
    public void setupTest(JavaSamplerContext context) {
        Iterator<String> iterator = context.getParameterNamesIterator();
        while (iterator.hasNext()) {
            String name = iterator.next();
            System.out.println("Parameter name: >" + name + "< value >" + context.getParameter(name) + "<");
        }

    }

    @Override
    public SampleResult runTest(JavaSamplerContext javaSamplerContext) {
        SampleResult result = new SampleResult();
        result.setSamplerData("some data");
        result.sampleStart();
        try {
            WebDriver driver = new HtmlUnitDriver();
            driver.navigate().to("http://localhost:8085/dump");
        } finally {
            result.sampleEnd();
        }
        return result;
    }

    @Override
    public boolean interrupt() {
        return false;
    }


    @Override
    public Arguments getDefaultParameters() {

        Arguments params = new Arguments();

        params.addArgument("zName", "edw");

        return params;
    }

}