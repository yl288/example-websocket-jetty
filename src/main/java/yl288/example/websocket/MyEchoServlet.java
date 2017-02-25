package yl288.example.websocket;

import javax.servlet.annotation.WebServlet;

import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

import yl288.example.websocket.sockets.ListenerEchoWebSocket;

@SuppressWarnings("serial")
@WebServlet(name = "Example echo servlet", urlPatterns = "/echo")
public class MyEchoServlet extends WebSocketServlet {

	@Override
	public void configure(WebSocketServletFactory factory) {
		// 10 second timeout
		factory.getPolicy().setIdleTimeout(10_000);
		
		// Register the websocket
		factory.register(ListenerEchoWebSocket.class);
	}

}
