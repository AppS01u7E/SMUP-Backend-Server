package com.appsolute.soomapi.domain.cafeteria.service

import com.leeseojune.neisapi.dto.Meal
import java.time.LocalDate

interface MealService {

    fun getTodayMeal(): Meal
    fun getMeal(date: String): Meal

}