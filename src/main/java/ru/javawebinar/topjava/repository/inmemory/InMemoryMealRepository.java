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
        // handle case: update, but not present in storage
        if(meal.getId().equals(SecurityUtil.authUserId())){
            return repositoryOfMeals.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
        }
        return null;
    }

    @Override
    public boolean delete(int id) {
        if(repositoryOfMeals.get(id).getId().equals(SecurityUtil.authUserId()))
        {
            return repositoryOfMeals.remove(id) != null;
        }
        return false;
    }

    @Override
    public Meal get(int id) {

        if(repositoryOfMeals.get(id).getId().equals(SecurityUtil.authUserId()))
        {
            return repositoryOfMeals.get(id);
        }
        else return null;

    }

    @Override
    public Collection<Meal> getAll() {
        List<Meal> meals=new ArrayList<>();
        repositoryOfMeals.forEach((id,meal)->meals.add(meal));
        meals.removeIf(meal -> !meal.getUserId().equals(SecurityUtil.authUserId()));
        return meals;
    }
}

