package app.joybox.api

import app.joybox.api.request.AddProductRequest
import app.joybox.api.request.UpdateProductRequest
import app.joybox.api.response.AddImageResponse
import app.joybox.api.response.GetProductResponse
import app.joybox.api.response.GetSimpleProductResponse
import app.joybox.api.response.UpdateProductResponse
import app.joybox.domain.product.ProductNotFoundException
import app.joybox.domain.product.ProductService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/product")
class ProductController(
    private val productService: ProductService
) {

    @GetMapping("/{id}")
    fun getProduct(
        @PathVariable id: Long
    ): ResponseEntity<GetProductResponse> {
        return try {
            val product = productService.getProduct(id)
            ResponseEntity.ok(GetProductResponse.from(product))
        } catch (e: ProductNotFoundException) {
            ResponseEntity.badRequest().build()
        }
    }

    @GetMapping
    fun getProductList(): ResponseEntity<List<GetSimpleProductResponse>> {
        val products = productService.getProducts()
        val responses = products.map(GetSimpleProductResponse::from)
        return ResponseEntity.ok(responses)
    }

    @PostMapping
    fun addProduct(
        @RequestBody request: AddProductRequest
    ): ResponseEntity<Any> {
        val command = request.toCommand()
        productService.addProduct(command)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

    @DeleteMapping("/{id}")
    fun deleteProduct(
        @PathVariable id: Long
    ): ResponseEntity<Any> {
        return try {
            productService.deleteProduct(id)
            ResponseEntity.ok().build()
        } catch (e: ProductNotFoundException) {
            ResponseEntity.badRequest().build()
        }
    }

    @PutMapping("/{id}")
    fun updateProduct(
        @PathVariable id: Long,
        @RequestBody request: UpdateProductRequest
    ): ResponseEntity<UpdateProductResponse> {
        return try {
            val command = request.toCommand(id)
            productService.updateProduct(command)
            ResponseEntity.ok().build()
        } catch (e: ProductNotFoundException) {
            ResponseEntity.badRequest().build()
        }
    }

    @PostMapping("/{id}/thumbnail")
    fun addThumbnail(
        @PathVariable id: Long,
        @RequestParam thumbnailImage: MultipartFile
    ): ResponseEntity<Any> {
        return try {
            productService.addThumbnail(id, thumbnailImage)
            ResponseEntity.ok().build()
        } catch (e: ProductNotFoundException) {
            ResponseEntity.badRequest().build()
        }
    }

    @PostMapping("/image")
    fun addImage(@RequestParam image: MultipartFile): ResponseEntity<AddImageResponse> {
        val uuid = productService.addImage(image)
        return ResponseEntity.status(HttpStatus.CREATED).body(AddImageResponse.from(uuid))
    }
}