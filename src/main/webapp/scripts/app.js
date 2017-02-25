require.config({
	baseUrl : 'scripts/modules',
	paths : {
		'jquery' : '../lib/jquery-3.1.1'
	}
});

requirejs([ 'jquery', 'logger' ], function($, logger) {

	logger.info('Connecting');

	var connection = new WebSocket('ws://localhost:20860/echo');

	// When the connection is open, send some data to the server
	connection.onopen = function() {
		connection.send('Ping'); // Send the message 'Ping' to the server
	};

	// Log errors
	connection.onerror = function(error) {
		logger.info('WebSocket Error ' + error);
	};

	// Log messages from the server
	connection.onmessage = function(e) {
		logger.info('Server: ' + e.data);
	};
	
	$('#sendButton').click(function() {
		connection.send('button clicked');
	});
});
