package ru.javawebinar.topjava.repository.inmemory;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Meal> repositoryOfMeals = new ConcurrentHashMap<>();
    private final AtomicInteger counterOfMeals = new AtomicInteger(0);
    private final Map<Integer, User> repositoryOfUsers = new ConcurrentHashMap<>();
    private final AtomicInteger counterOfUsers = new AtomicInteger(0);
    public static InMemoryUserRepository userRepository;

    {
        MealsUtil.meals.forEach(this::save);
    }

    @Override
    public Meal save(Meal meal) {
        if (meal.isNew()) {
            meal.setId(counterOfMeals.incrementAndGet());
            meal.setUserId(counterOfUsers.incrementAndGet());
            repositoryOfMeals.put(meal.getId(), meal);
            return meal;
        }
        // handle case: update, but not present in storage
        if(meal.getUserId().equals(repositoryOfMeals.get(meal.getUserId()).getId())){
            return repositoryOfMeals.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
        }
        return null;
    }

    @Override
    public boolean delete(int id) {
        if(repositoryOfMeals.get(id).getUserId().equals(repositoryOfUsers.get(repositoryOfMeals.get(id).getUserId()).getId()))
        {
            return repositoryOfMeals.remove(id) != null;
        }
        return false;
    }

    @Override
    public Meal get(int id) {

        if(repositoryOfMeals.get(id).getUserId().equals(repositoryOfUsers.get(repositoryOfMeals.get(id).getUserId()).getId()))
        {
            return repositoryOfMeals.get(id);
        }
        else return null;

    }

    @Override
    public Collection<Meal> getAll() {
        return repositoryOfMeals.values();
    }
}

