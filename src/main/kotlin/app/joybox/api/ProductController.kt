package app.joybox.api

import app.joybox.api.request.AddProductRequest
import app.joybox.api.request.UpdateProductRequest
import app.joybox.api.response.AddImageResponse
import app.joybox.api.response.GetProductResponse
import app.joybox.api.response.GetSimpleProductResponse
import app.joybox.api.response.UpdateProductResponse
import app.joybox.domain.product.ProductNotFoundException
import app.joybox.domain.product.ProductService
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

val logger = KotlinLogging.logger {}

@RestController
@RequestMapping("/api")
class ProductController(
    private val productService: ProductService
) {

    @GetMapping("/{id}")
    fun getProduct(
        @PathVariable id: Long
    ): ResponseEntity<GetProductResponse> {
        logger.info { "Get product request" }
        return try {
            val product = productService.getProduct(id)
            ResponseEntity.ok(GetProductResponse.from(product))
        } catch (e: ProductNotFoundException) {
            logger.debug { "$e" }
            ResponseEntity.badRequest().build()
        }
    }

    @GetMapping
    fun getProductList(): ResponseEntity<List<GetSimpleProductResponse>> {
        logger.info { "Get product list request" }
        val products = productService.getProducts()
        val responses = products.map(GetSimpleProductResponse::from)
        return ResponseEntity.ok(responses)
    }

    @PostMapping
    fun addProduct(
        @RequestBody request: AddProductRequest
    ): ResponseEntity<Any> {
        logger.info { "Add product request" }
        val command = request.toCommand()
        productService.addProduct(command)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

    @DeleteMapping("/{id}")
    fun deleteProduct(
        @PathVariable id: Long
    ): ResponseEntity<Any> {
        logger.info { "Delete product request" }
        return try {
            productService.deleteProduct(id)
            ResponseEntity.ok().build()
        } catch (e: ProductNotFoundException) {
            logger.debug { "$e" }
            ResponseEntity.badRequest().build()
        }
    }

    @PutMapping("/{id}")
    fun updateProduct(
        @PathVariable id: Long,
        @RequestBody request: UpdateProductRequest
    ): ResponseEntity<UpdateProductResponse> {
        logger.info { "Update product request" }
        return try {
            val command = request.toCommand(id)
            productService.updateProduct(command)
            ResponseEntity.ok().build()
        } catch (e: ProductNotFoundException) {
            logger.debug { "$e" }
            ResponseEntity.badRequest().build()
        }
    }

    @PostMapping("/image")
    fun addImage(@RequestParam image: MultipartFile): ResponseEntity<AddImageResponse> {
        logger.info { "Add image request" }
        val uuid = productService.addImage(image)
        return ResponseEntity.status(HttpStatus.CREATED).body(AddImageResponse.from(uuid))
    }
}