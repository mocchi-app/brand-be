package org.mocchi.brand

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class BrandApplication

fun main(args: Array<String>) {
    SpringApplication.run(BrandApplication::class.java, *args)
}
