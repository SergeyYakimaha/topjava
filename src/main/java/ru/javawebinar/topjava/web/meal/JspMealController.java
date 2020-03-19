package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import javax.servlet.http.HttpServletRequest;
import java.net.http.HttpRequest;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class JspMealController {
    @Autowired
    private MealService mealService;

    @GetMapping("/meals")
    public String getMeals(Model model) {
        int userId = SecurityUtil.authUserId();
        List<MealTo> mealToList = MealsUtil.getTos(mealService.getAll(userId), SecurityUtil.authUserCaloriesPerDay());
        model.addAttribute("meals", mealToList);
        return "meals";
    }

    @GetMapping("/meals/add")
    public String showAddUserForm(Model model) {
        Meal meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000);
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @GetMapping("/meals/{id}/update")
    public String showUpdateUserForm(@PathVariable int id, Model model) {
        Meal meal = mealService.get(id, SecurityUtil.authUserId());
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @GetMapping("/meals/{id}/delete")
    public String deleteMeal(@PathVariable int id) {
        mealService.delete(id, SecurityUtil.authUserId());
        return "redirect:/meals";
    }

    @PostMapping("/meals/**")
    public String saveOrUpdateMeal(HttpServletRequest request) {
        Meal meal = new Meal(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));

        if (StringUtils.isEmpty(request.getParameter("id"))) {
            checkNew(meal);
            mealService.create(meal, SecurityUtil.authUserId());
        } else {
            assureIdConsistent(meal, getId(request));
            mealService.update(meal, SecurityUtil.authUserId());
        }
        return "redirect:/meals";
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }

}
