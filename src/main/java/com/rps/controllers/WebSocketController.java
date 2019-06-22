/*

package com.rps.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.rps.model.OutputMessage;
import com.rps.model.WSMessage;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;

@Controller
public class WebSocketController {

	
    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public OutputMessage send(final WSMessage message) throws Exception {

        final String time = new SimpleDateFormat("HH:mm").format(new Date());
        return new OutputMessage(message.getFrom(), message.getText(), time);
    }
	
	
}
*/