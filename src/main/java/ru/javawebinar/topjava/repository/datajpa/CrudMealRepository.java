package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudMealRepository extends JpaRepository<Meal, Integer> {

    @Transactional
    @Modifying
//    @Query(name = User.DELETE)
    @Query("DELETE FROM Meal u WHERE u.id=:id AND u.user.id=:userId")
    int delete(@Param("id") int id, @Param("userId") int userId);

    List<Meal> findByUserIdOrderByDateTimeDesc(int userId);

// 1-variant getBetweenHalfOpen()
    List<Meal> findByUserIdAndDateTimeGreaterThanEqualAndDateTimeLessThanOrderByDateTimeDesc(
            int userId, LocalDateTime startTime, LocalDateTime endTime);

//  2-variant getBetweenHalfOpen()
    @Modifying
    @Query("SELECT m FROM Meal m WHERE m.user.id=:userId " +
            "AND m.dateTime >= :startTime AND m.dateTime < :endTime ORDER BY m.dateTime DESC")
    List<Meal> getBetweenHalfOpen(@Param("userId") int userId,
                           @Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);
}
