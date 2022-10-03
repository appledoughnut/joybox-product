package app.joybox.api

import app.joybox.api.request.AddProductRequest
import app.joybox.api.response.AddImageResponse
import app.joybox.api.response.AddProductResponse
import app.joybox.api.response.ProductResponse
import app.joybox.domain.product.ProductNotFoundException
import app.joybox.domain.product.ProductService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api")
class ProductController(
    private val productService: ProductService
) {

    @GetMapping("/{id}")
    fun getProduct(
        @PathVariable id: Long
    ): ResponseEntity<ProductResponse> {
        val product = productService.getProduct(id)
            ?: return ResponseEntity.badRequest().build()
        return ResponseEntity.ok(ProductResponse.from(product))
    }

    @PostMapping
    fun addProduct(
        @RequestBody request: AddProductRequest
    ): ResponseEntity<AddProductResponse> {
        val command = request.toCommand()
        productService.addProduct(command)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

    @DeleteMapping("/{id}")
    fun deleteProduct(
        @PathVariable id: Long
    ): ResponseEntity<Any> {
        try {
            productService.deleteProduct(id)
        } catch (e: ProductNotFoundException) {
            return ResponseEntity.badRequest().build()
        }
        return ResponseEntity.ok().build()
    }

    @PostMapping("/image")
    fun addImage(@RequestParam image: MultipartFile): ResponseEntity<AddImageResponse> {
        val uuid = productService.addImage(image)
        return ResponseEntity.status(HttpStatus.CREATED).body(AddImageResponse.from(uuid))
    }
}