package com.appsolute.soomapi.domain.cafeteria.service

import com.appsolute.soomapi.global.security.CurrentUser
import com.leeseojune.neisapi.NeisApi
import com.leeseojune.neisapi.dto.Meal
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@Service
class MealServiceImpl(
    private val current: CurrentUser

): MealService {
    private val neisApi = NeisApi.Builder().build()



    override fun getTodayMeal(): Meal {
        return getMeal(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")))
    }

    override fun getMeal(date: String): Meal {

        return neisApi.getMealsByAbsoluteDay(date
            , current.getUser().school.atpT_OFCDC_SC_CODE,
            current.getUser().school.sD_SCHUL_CODE)
    }

}