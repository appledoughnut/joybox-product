package app.joybox

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@SpringBootApplication
class ProductApplication

fun main(args: Array<String>) {
    runApplication<ProductApplication>(*args)
}
