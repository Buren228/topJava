package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.MealServlet;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Meal> repositoryOfMeals = new ConcurrentHashMap<>();
    private final AtomicInteger counterOfMeals = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(this::save);
    }

    @Override
    public Meal save(Meal meal) {
        if (meal.isNew()) {
            meal.setId(counterOfMeals.incrementAndGet());
            repositoryOfMeals.put(meal.getId(), meal);
            return meal;
        }
        return null;
    }

    @Override
    public boolean delete(int id) {
        return repositoryOfMeals.remove(id) != null;
    }

    @Override
    public Meal get(int id) {
        return repositoryOfMeals.get(id);
    }

    @Override
    public Collection<Meal> getAll() {
        List<Meal> meals = new ArrayList<>();
        repositoryOfMeals.forEach((id, meal) -> meals.add(meal));
        return meals;
    }
}

