package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.stream.Collectors.toList;

@Repository
public class InMemoryUserRepository implements UserRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepository.class);

    public InMemoryUserRepository(){}
    public static AtomicInteger COUNTER = new AtomicInteger(0);
    private List<User> USERS = new ArrayList<>();

    public static final List<User> initList = Arrays.asList(
            new User(null, "nameUser", "email@yahoo.com", "passwordUser", Role.USER),
            new User(null, "nameAdmin", "email@gmail.com", "passwordAdmin", Role.ADMIN)
    );

    {
        initList.forEach(this::save);
    }

    @Override
    public boolean delete(int id) {
//        log.info("delete {}", id);
        USERS = USERS.stream().filter(user -> user.getId() != id).collect(toList());
        boolean result = USERS.stream().anyMatch(s -> s.getId() != id);
//        log.info("result {}", result);
        return result;
    }

    @Override
    public User save(User user) {
//        log.info("save {}", user);
        if (user.isNew()) user.setId(COUNTER.incrementAndGet());
        else delete(user.getId());
        USERS.add(user);
        return user;
    }

    @Override
    public User get(int id) { return USERS.stream().filter(user -> user.getId() == id).findFirst().get(); }

    @Override
    public List<User> getAll() {
        return USERS.stream().sorted(Comparator.comparing(User::getName).thenComparing(User::getEmail)).collect(toList());
    }

    @Override
    public User getByEmail(String email) {
        return USERS.stream().filter(user -> user.getEmail() == email).findFirst().get();
    }
}
