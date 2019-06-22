<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<html>
<head>
<title>

<s:message code="welcome.message"></s:message> 

- Home</title>
<script>
function connect() {
	ws = new WebSocket('ws://localhost:9091/name');
	ws.onmessage = function(data){
		showGreeting(data.data);
	}
	 //setConnected(true);
}

function disconnect() {
    if (ws != null) {
        ws.close();
    }
    //setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    ws.send(document.getElementById('input').innerHTML);
}

function showGreeting(message) {
    document.getElementById('output').innerHTML = message;
}

</script>
</head>
<body>
<input type="text" id="input"/>
<input type="button" onclick="" value="Click"/>
<input type="text" id="output"/>


</body>
</html>