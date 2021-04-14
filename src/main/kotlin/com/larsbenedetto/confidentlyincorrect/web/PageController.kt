package com.larsbenedetto.confidentlyincorrect.web

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("")
class PageController(
) {
    @GetMapping("/")
    fun greeting(
        model: Model
    ): String? {
        return "Landing"
    }
}