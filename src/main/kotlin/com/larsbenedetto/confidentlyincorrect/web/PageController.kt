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
    fun landing(
        model: Model
    ): String? {
        return "Landing"
    }

    @GetMapping("/host")
    fun host(
        model: Model
    ): String? {
        return "HostLobby"
    }

    @GetMapping("/join")
    fun join(
        model: Model
    ): String? {
        return "JoinLobby"
    }

    @GetMapping("/lobby")
    fun lobby(
        model: Model
    ): String? {
        return "PreGameLobby"
    }

    @GetMapping("/question")
    fun question(
        model: Model
    ): String? {
        return "Question"
    }

    @GetMapping("/results")
    fun results(
        model: Model
    ): String? {
        return "QuestionResults"
    }

    @GetMapping("/gameOver")
    fun gameOver(
        model: Model
    ): String? {
        return "GameResults"
    }
}