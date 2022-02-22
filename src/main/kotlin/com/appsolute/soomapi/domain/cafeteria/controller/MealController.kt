package com.appsolute.soomapi.domain.cafeteria.controller

import com.appsolute.soomapi.domain.cafeteria.service.MealService
import com.leeseojune.neisapi.dto.Meal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate
import javax.validation.Valid
import javax.validation.constraints.Max
import javax.validation.constraints.Pattern


@RestController
@RequestMapping("/api/v1/meal")
class MealController(
    private val mealService: MealService
) {

    @GetMapping("/today")
    fun getTodayMeal(): Meal{
        return mealService.getTodayMeal()
    }

    @GetMapping
    fun getMeal(@RequestParam date: String): Meal{
        return mealService.getMeal(date)
    }


}