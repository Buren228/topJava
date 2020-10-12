package ru.javawebinar.topjava.repository;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;

import java.util.Collection;
@Repository
public interface MealRepository {
    // null if not found, when updated
    Meal save(Meal meal);

    // false if not found
    boolean delete(int id);

    // null if not found
    Meal get(int id);

    Collection<Meal> getAll();
}