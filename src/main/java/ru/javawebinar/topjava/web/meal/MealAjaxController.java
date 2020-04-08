package ru.javawebinar.topjava.web.meal;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@RestController
@RequestMapping("/ajax/meals")
public class MealAjaxController extends AbstractMealController {

    @Override
    @GetMapping("/{id}")
    public Meal get(@PathVariable int id) {
        return super.get(id);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        super.delete(id);
    }

    @Override
    @GetMapping
    public List<MealTo> getAll() {
        return super.getAll();
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void createOrUpdate(@RequestParam Integer id,
                               @RequestParam String dateTime,
                               @RequestParam String description,
                               @RequestParam int calories) {

        Meal meal = new Meal(id, LocalDateTime.parse(dateTime), description, calories);
        if (meal.isNew()) {
            super.create(meal);
        }
    }

    @GetMapping("/filter")
    public List<MealTo> getBetween(@RequestParam @Nullable String startDate,
                                   @RequestParam @Nullable String startTime,
                                   @RequestParam @Nullable String endDate,
                                   @RequestParam @Nullable String endTime) {
        LocalDate startDate_ = parseLocalDate(startDate);
        LocalDate endDate_ = parseLocalDate(endDate);
        LocalTime startTime_ = parseLocalTime(startTime);
        LocalTime endTime_ = parseLocalTime(endTime);
        return super.getBetween(startDate_, startTime_, endDate_, endTime_);
    }
}
