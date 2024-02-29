package edu.java.api.controllers;

import edu.java.database.DataBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tg-chat/{id}")
public final class TgChatController {
    private final DataBase<Integer, String> dataBase;

    @Autowired
    public TgChatController(DataBase<Integer, String> dataBase) {
        this.dataBase = dataBase;
    }

    @PostMapping(produces = "application/json")
    public ResponseEntity<?> registerChat(@PathVariable int id) {
        dataBase.addNewUserByKey(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(produces = "application/json")
    public ResponseEntity<?> deleteChat(@PathVariable int id) {
        dataBase.deleteUserByKey(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

