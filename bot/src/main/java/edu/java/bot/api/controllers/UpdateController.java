package edu.java.bot.api.controllers;

import edu.java.bot.service.bot_service.handlers.Handler;
import edu.java.bot.service.bot_service.handlers.LinkUpdateHandler;
import edu.java.exceptions.BadRequestException.BadRequestException;
import edu.java.requests.LinkUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/update")
public final class UpdateController {
    private final Handler<LinkUpdateRequest> handler;

    @Autowired
    public UpdateController(LinkUpdateHandler handler) {
        this.handler = handler;
    }

    @PostMapping(produces = "application/json")
    ResponseEntity<?> newUpdateFromClient(@RequestBody LinkUpdateRequest linkUpdateRequest) throws BadRequestException {
        handler.put(linkUpdateRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
