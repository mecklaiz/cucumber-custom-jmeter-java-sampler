package com.mecklaiz.jmeter.util;

import com.mecklaiz.jmeter.resultcollector.CustomResultCollector;
import org.apache.jmeter.config.Argument;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.config.gui.ArgumentsPanel;
import org.apache.jmeter.control.LoopController;
import org.apache.jmeter.control.gui.LoopControlPanel;
import org.apache.jmeter.control.gui.TestPlanGui;
import org.apache.jmeter.engine.StandardJMeterEngine;
import org.apache.jmeter.protocol.java.sampler.JavaSampler;
import org.apache.jmeter.reporters.ResultCollector;
import org.apache.jmeter.reporters.ResultSaver;
import org.apache.jmeter.reporters.Summariser;
import org.apache.jmeter.samplers.SampleSaveConfiguration;
import org.apache.jmeter.save.SaveService;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.testelement.TestPlan;
import org.apache.jmeter.threads.ThreadGroup;
import org.apache.jmeter.threads.gui.ThreadGroupGui;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.collections.HashTree;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.function.Function;

public class ZJMeterUtils {
    public static File jmeterHome = System.getProperty("JMETER_HOME") == null ?
            new File("C:\\Apps\\apache-jmeter-2.13") :
            new File(System.getProperty("JMETER_HOME")) ;

    public static StandardJMeterEngine doStandardJMeterSetup() {

        File jmeterProperties = new File(jmeterHome.getPath() + File.separator + "bin" + File.separator + "jmeter.properties");

        StandardJMeterEngine jmeter = new StandardJMeterEngine();

        //JMeter initialization (properties, log levels, locale, etc)
        JMeterUtils.setJMeterHome(jmeterHome.getPath());
        JMeterUtils.loadJMeterProperties(jmeterProperties.getPath());
        JMeterUtils.initLogging();// you can comment this line out to see extra log messages of i.e. DEBUG level
        JMeterUtils.initLocale();

        return jmeter;
    }

    public static void runJMeter(StandardJMeterEngine jmeter,
                                 String classname,
                                 int concurrentCount ) {
        int port = 1;
        // JMeter Test Plan, basically JOrphan HashTree
        HashTree testPlanTree = new HashTree();

        // Second HTTP Sampler - open jon's machine
        //CustomJavaSampler javaSampler = new CustomJavaSampler();
        JavaSampler javaSampler = new JavaSampler();
        javaSampler.setClassname(classname);
        Arguments params = new Arguments();

        params.addArgument("zName", "edw");
        javaSampler.setArguments(params);

        // Loop Controller
        LoopController loopController = new LoopController();
        loopController.setLoops(2);
        loopController.setFirst(true);
        loopController.setProperty(TestElement.TEST_CLASS, LoopController.class.getName());
        loopController.setProperty(TestElement.GUI_CLASS, LoopControlPanel.class.getName());
        loopController.initialize();

        // Thread Group
        ThreadGroup threadGroup = new ThreadGroup();
        threadGroup.setName("Example Thread Group");
        threadGroup.setNumThreads(concurrentCount);
        threadGroup.setRampUp(1);
        threadGroup.setSamplerController(loopController);
        threadGroup.setProperty(TestElement.TEST_CLASS, ThreadGroup.class.getName());
        threadGroup.setProperty(TestElement.GUI_CLASS, ThreadGroupGui.class.getName());

        // Test Plan
        TestPlan testPlan = new TestPlan("Create JMeter Script From Java Code");
        testPlan.setProperty(TestElement.TEST_CLASS, TestPlan.class.getName());
        testPlan.setProperty(TestElement.GUI_CLASS, TestPlanGui.class.getName());
        Arguments args = (Arguments) new ArgumentsPanel().createTestElement();
        args.addArgument(new Argument("ZArg1", "ZVal1"));
        testPlan.setUserDefinedVariables(args);

        // Construct Test Plan from previously initialized elements
        testPlanTree.add(testPlan);
        HashTree threadGroupHashTree = testPlanTree.add(testPlan, threadGroup);
        threadGroupHashTree.add(javaSampler);
        // threadGroupHashTree.add(javascriptSampler);


        //add Summarizer output to get test progress in stdout like:
        // summary =      2 in   1.3s =    1.5/s Avg:   631 Min:   290 Max:   973 Err:     0 (0.00%)
        Summariser summer = null;
        String summariserName = JMeterUtils.getPropDefault("summariser.name", "summary");
        if (summariserName.length() > 0) {
            summer = new Summariser(summariserName);
        }

        // Store execution results into a .jtl file
        String tmpDir = System.getProperty("java.io.tmpdir");
        String logFile = tmpDir + File.separator + "example"+port+".jtl";
        CustomResultCollector logger = new CustomResultCollector(summer);
        logger.setFilename(logFile);
        testPlanTree.add(testPlanTree.getArray()[0], logger);

        String logFile2 = tmpDir + File.separator + "example_results_"+port+".jtl";

        ResultCollector r = new ResultCollector();
        r.setFilename(logFile2);
        SampleSaveConfiguration ssc = new SampleSaveConfiguration();
        ssc.setAsXml(true);
        ssc.setResponseData(true);
        ssc.setResponseHeaders(true);
        r.setSaveConfig(ssc);

        ResultSaver rs = new ResultSaver();
        rs.setEnabled(true);
        rs.setName("Responses");

        // javaSampler.addTestElement(rs);

        testPlanTree.add(testPlanTree.getArray()[0], r);
        testPlanTree.add(testPlanTree.getArray()[0], rs);

        // save generated test plan to JMeter's .jmx file format
        try {
            SaveService.saveTree(testPlanTree, new FileOutputStream(jmeterHome + File.separator + "example"+port+".jmx"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Run Test Plan
        jmeter.configure(testPlanTree);
        jmeter.run();
    }


}