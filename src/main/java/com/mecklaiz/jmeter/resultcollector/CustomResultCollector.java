package com.mecklaiz.jmeter.resultcollector;

import org.apache.jmeter.reporters.ResultCollector;
import org.apache.jmeter.reporters.Summariser;
import org.apache.jmeter.samplers.SampleEvent;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jorphan.logging.LoggingManager;
import org.apache.log.Logger;

public class CustomResultCollector extends ResultCollector {
    private static final Logger LOG = LoggingManager.getLoggerForClass();
    public CustomResultCollector(Summariser summer) {
        super(summer);
    }

    @Override
    public void sampleOccurred(SampleEvent e) {
        super.sampleOccurred(e);
        SampleResult r = e.getResult();
        if (r.isSuccessful()) {
            LOG.info("Response time in milliseconds: " + r.getTime());
        }
    }
}
