package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.stream.Collectors.toList;

@Repository
public class InMemoryUserRepository implements UserRepository {

    public InMemoryUserRepository(){}

    public static AtomicInteger COUNTER = new AtomicInteger(0);
    public static Map<Integer, User> userMap = new ConcurrentHashMap<>();

    public static final List<User> initList = Arrays.asList(
            new User(null, "nameUser", "email@yahoo.com", "passwordUser", Role.USER),
            new User(null, "nameAdmin", "email@gmail.com", "passwordAdmin", Role.ADMIN)
    );

    {
        initList.forEach(this::save);
    }

    @Override
    public User save(User user) {
        if (user.isNew()){
            user.setId(COUNTER.incrementAndGet());
            userMap.putIfAbsent(user.getId(), user);
            return user;
        } else  return userMap.computeIfPresent(user.getId(), (key, value) -> user);
    }

    @Override
    public boolean delete(int id) {
        return userMap.remove(id) == null;
    }

    @Override
    public User get(int id) {
        return userMap.getOrDefault(id, null);
    }

    @Override
    public List<User> getAll() {
        List<User> list = userMap.values().stream()
                .sorted(Comparator.comparing(User::getName).thenComparing(User::getEmail))
                .collect(toList());
        return list;
    }

    @Override
    public User getByEmail(String email) {
        return userMap.values().stream()
                .filter(u -> u.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }
}
