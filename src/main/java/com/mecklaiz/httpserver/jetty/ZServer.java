package com.mecklaiz.httpserver.jetty;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.*;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.StdErrLog;

public class ZServer {

    public static void startJetty() throws Exception {
        StdErrLog logs = new StdErrLog();
        logs.setLevel(StdErrLog.LEVEL_WARN);
        Log.setLog(logs);

        Server server = new Server(8085);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);


        context.setResourceBase(System.getProperty("java.io.tmpdir"));

        context.addServlet(new ServletHolder(new DumpServlet()), "/dump");
        context.addServlet(new ServletHolder(new DefaultServlet()), "/");
        context.addServlet(new ServletHolder(new HelloServlet()), "/helloserv");

        ResourceHandler resource_handler = new ResourceHandler();
        resource_handler.setDirectoriesListed(true);
        resource_handler.setWelcomeFiles(new String[]{ "index.html" });
        resource_handler.setResourceBase(".");
        ContextHandler ctx = new ContextHandler("/temp"); /* the server uri path */
        ctx.setHandler(resource_handler);

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[] { ctx, resource_handler, context,new DefaultHandler() });
        server.setHandler(handlers);


        server.start();
//        server.dumpStdErr();
//        server.join();
    }
}
