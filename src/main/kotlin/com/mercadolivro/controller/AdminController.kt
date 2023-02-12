package com.mercadolivro.controller

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("admin")
class AdminController() {

        @GetMapping("sales")
        fun sales(): String {
            return "list sales in soon, here!"
        }
}