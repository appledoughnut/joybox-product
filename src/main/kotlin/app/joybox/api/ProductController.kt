package app.joybox.api

import app.joybox.api.response.ProductResponse
import app.joybox.domain.service.ProductService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class ProductController(
    private val productService: ProductService
) {

    @GetMapping("{id}")
    fun getProduct(
        @PathVariable id: Long
    ): ResponseEntity<ProductResponse> {
        val product = productService.getProduct(id)
            ?: return ResponseEntity.badRequest().build()
        return ResponseEntity.ok(ProductResponse.from(product))
    }
}