package edu.java.domain;

import edu.java.exceptions.BadRequestException.BadRequestException;
import edu.java.exceptions.BadRequestException.ChatAlreadyRegisteredException;
import edu.java.exceptions.BadRequestException.IncorrectDataException;
import edu.java.exceptions.NotFoundException.ChatNotRegisteredException;
import edu.java.exceptions.NotFoundException.ChatNotTrackedUriException;
import edu.java.exceptions.NotFoundException.NotFoundException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class InMemoryDataBase implements DataBase<Long, String> {
    private static final Map<Long, Set<String>> DATA_BASE = new ConcurrentHashMap<>();

    @Autowired
    public InMemoryDataBase() {

    }

    @Override
    public Set<String> allDataByKey(Long key) throws BadRequestException {
        if (notCorrectId(key) || !DATA_BASE.containsKey(key)) {
            throw new IncorrectDataException(key);
        }

        return DATA_BASE.get(key);
    }

    @Override
    public void addValue(Long key, String value) throws BadRequestException {
        if (notCorrectId(key) || !DATA_BASE.containsKey(key)) {
            throw new IncorrectDataException(key);
        }

        DATA_BASE.get(key).add(value);
    }

    @Override
    public void deleteValue(Long key, String value) throws BadRequestException, NotFoundException {
        if (notCorrectId(key) || !DATA_BASE.containsKey(key)) {
            throw new IncorrectDataException(key);
        }

        if (!DATA_BASE.get(key).contains(value)) {
            throw new ChatNotTrackedUriException(key, value);
        }

        DATA_BASE.get(key).remove(value);
    }

    @Override
    public void addNewUserByKey(Long key) throws BadRequestException {
        if (notCorrectId(key)) {
            throw new IncorrectDataException(key);
        }

        if (DATA_BASE.containsKey(key)) {
            throw new ChatAlreadyRegisteredException(key);
        }

        DATA_BASE.put(key, ConcurrentHashMap.newKeySet());
    }

    @Override
    public void deleteUserByKey(Long key) throws BadRequestException, NotFoundException {
        if (notCorrectId(key)) {
            throw new IncorrectDataException(key);
        }

        if (!DATA_BASE.containsKey(key)) {
            throw new ChatNotRegisteredException(key);
        }

        DATA_BASE.remove(key);
    }

    private boolean notCorrectId(long key) {
        return key <= 0;
    }
}
