package yl288.example.websocket.sockets;

import java.io.IOException;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Interface implementor that gets to see all the events the socket could receive.
 * @author yl288
 * 12 Feb 2017 09:02:40
 */
public class ListenerEchoWebSocket implements WebSocketListener {
	
	private static final Logger logger = LoggerFactory.getLogger(ListenerEchoWebSocket.class);
	
	private Session session;

	public ListenerEchoWebSocket() {
		super();
		logger.info("{} New socket created", socketId());
	}

	private String socketId() {
		return "Socket[" + hashCode() + "]";
	}
	
	@Override
	public void onWebSocketClose(int statusCode, String reason) {
		this.session = null;
		logger.info("{} Session closed: statusCode={}, reason=[{}]", socketId(), statusCode, reason);
	}

	@Override
	public void onWebSocketConnect(Session session) {
		this.session = session;
		logger.info("{} Session connected", socketId());
	}

	@Override
	public void onWebSocketError(Throwable cause) {
		logger.error(socketId() + " Session error", cause);
	}

	@Override
	public void onWebSocketBinary(byte[] payload, int offset, int len) {
		logger.info("{} Received binary message: len=[{}]", socketId(), len);
	}

	@Override
	public void onWebSocketText(String message) {
		logger.info("{} Received message: [{}]", socketId(), message);
		if (session != null && session.isOpen()) {
			try {
				session.getRemote().sendString(message);
			} catch (IOException e) {
				logger.error("Failed to send message", e);
			}
		}
	}
	

}
