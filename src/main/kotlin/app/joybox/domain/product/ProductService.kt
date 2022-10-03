package app.joybox.domain.product

import app.joybox.api.command.AddProductCommand
import app.joybox.domain.image.Image
import app.joybox.domain.image.ImageRepository
import app.joybox.domain.image.ImageStorage
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.util.*

class ProductNotFoundException : Exception()
class ImageNotFoundException : Exception()
class NullIdException : Exception()

@Service
class ProductService(
    private val productRepository: ProductRepository,
    private val imageRepository: ImageRepository,
    private val imageStorage: ImageStorage
) {
    fun getProduct(id: Long): Product? {
        return productRepository.findById(id)
            .orElse(null)
    }

    fun getProducts(): List<Product>? {
        return productRepository.findAll()
    }

    fun addProduct(command: AddProductCommand) {
        val imagesIds = command.imagesIds
        val images: List<Image> =
            if (imagesIds.isNullOrEmpty())
                emptyList()
            else
                imageRepository.findByIdIn(imagesIds)
        val product = Product()
        product.title = command.title
        product.price = command.price
        product.description = command.description
        product.images = images.toMutableList()
        productRepository.save(product)
    }

    fun deleteProduct(id: Long) {
        if (productRepository.existsById(id))
            productRepository.deleteById(id)
        else
            throw ProductNotFoundException()
    }

    fun addImage(imageFile: MultipartFile): UUID {
        val id = imageStorage.save(imageFile.inputStream)
        val image = Image(id)
        image.name = imageFile.name
        return imageRepository.save(image).id!!
    }

}