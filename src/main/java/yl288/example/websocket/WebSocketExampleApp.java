package yl288.example.websocket;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebSocketExampleApp {
	
	private static final Logger logger = LoggerFactory.getLogger(WebSocketExampleApp.class);
	
	public static void main(String[] args) {
		logger.info("Hello World!");
		
		Server server = new Server(20860);
		
		ResourceHandler resourceHandler = new ResourceHandler();
		resourceHandler.setDirectoriesListed(true);
		resourceHandler.setWelcomeFiles(new String[] { "index.html" });
		resourceHandler.setResourceBase("src/main/webapp");
		
		Handler handlers = servletContextHandlerList(resourceHandler);
		
		server.setHandler(handlers);
		
		try {
			server.start();
			server.join();
		} catch (Exception e) {
			logger.error("Failed to start server", e);
		}
	}

	/**
	 * This seems to work.
	 * @param resourceHandler
	 * @return
	 */
	private static HandlerList servletContextHandlerList(ResourceHandler resourceHandler) {
		ServletContextHandler websocketHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
		websocketHandler.setContextPath("/");
		websocketHandler.setResourceBase(System.getProperty("java.io.tmpdir"));
		websocketHandler.addServlet(MyEchoServlet.class, "/*");
		
		HandlerList handlers = new HandlerList();
		handlers.setHandlers(new Handler[] {
				resourceHandler,
				websocketHandler,
				new DefaultHandler()
		});
		return handlers;
	}

	/**
	 * Attempt 1 - throws an error about annotation not found.
	 * @param resourceHandler
	 * @return
	 */
	@SuppressWarnings("unused")
	private static HandlerList servletHandlerList(ResourceHandler resourceHandler) {
		ServletHandler websocketHandler = new ServletHandler();
		websocketHandler.addServletWithMapping(MyEchoServlet.class, "/*");
		
		HandlerList handlers = new HandlerList();
		handlers.setHandlers(new Handler[] {
				resourceHandler,
				websocketHandler,
				new DefaultHandler()
		});
		return handlers;
	}
}
