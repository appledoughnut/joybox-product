package app.joybox.domain.product

import app.joybox.domain.image.Image
import app.joybox.domain.image.ImageRepository
import app.joybox.domain.image.ImageStorage
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.util.*

class ImageNotFoundException : Exception() {}

@Service
class ProductService(
    private val productRepository: ProductRepository,
    private val imageRepository: ImageRepository,
    private val imageStorage: ImageStorage
) {
    fun getProduct(id: Long): Product? {
        return this.productRepository.findById(id)
            .orElse(null)
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

    fun addImage(imageFile: MultipartFile): UUID {
        val id = imageStorage.save(imageFile.inputStream)
        val image = Image(id)
        image.name = imageFile.name
        return imageRepository.save(image).id!!
    }
}