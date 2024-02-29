package edu.java.database;

import edu.java.exceptions.ChatAlreadyRegisteredException;
import edu.java.exceptions.ChatNotRegisteredException;
import edu.java.exceptions.IncorrectDataException;
import edu.java.exceptions.UriNotTrackedException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class InMemoryDataBase implements DataBase<Integer, String> {
    private static final Map<Integer, Set<String>> DATA_BASE = new ConcurrentHashMap<>();

    @Autowired
    public InMemoryDataBase() {

    }

    @Override
    public Set<String> allDataByKey(Integer key) {
        if (notCorrectId(key) || !DATA_BASE.containsKey(key)) {
            throw new IncorrectDataException(key);
        }

        return DATA_BASE.get(key);
    }

    @Override
    public void addValue(Integer key, String value) {
        if (notCorrectId(key) || !DATA_BASE.containsKey(key)) {
            throw new IncorrectDataException(key);
        }

        DATA_BASE.get(key).add(value);
    }

    @Override
    public void deleteValue(Integer key, String value) {
        if (notCorrectId(key) || !DATA_BASE.containsKey(key)) {
            throw new IncorrectDataException(key);
        }

        if (!DATA_BASE.get(key).contains(value)) {
            throw new UriNotTrackedException(key, value);
        }

        DATA_BASE.get(key).remove(value);
    }

    @Override
    public void addNewUserByKey(Integer key) {
        if (notCorrectId(key)) {
            throw new IncorrectDataException(key);
        }

        if (DATA_BASE.containsKey(key)) {
            throw new ChatAlreadyRegisteredException(key);
        }

        DATA_BASE.put(key, ConcurrentHashMap.newKeySet());
    }

    @Override
    public void deleteUserByKey(Integer key) {
        if (notCorrectId(key)) {
            throw new IncorrectDataException(key);
        }

        if (!DATA_BASE.containsKey(key)) {
            throw new ChatNotRegisteredException(key);
        }

        DATA_BASE.remove(key);
    }

    private boolean notCorrectId(int key) {
        return key <= 0;
    }
}
