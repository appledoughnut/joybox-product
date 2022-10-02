package app.joybox.api

import app.joybox.api.response.AddImageResponse
import app.joybox.api.response.ProductResponse
import app.joybox.domain.image.ImageStorage
import app.joybox.domain.product.ProductService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api")
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

    @PostMapping("**/image")
    fun addImage(@RequestParam image: MultipartFile): ResponseEntity<AddImageResponse> {
        val uuid = productService.addImage(image)
        return ResponseEntity.ok(AddImageResponse.from(uuid))
    }
}