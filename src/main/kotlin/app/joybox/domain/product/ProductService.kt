package app.joybox.domain.product

import app.joybox.domain.image.Image
import app.joybox.domain.image.ImageRepository
import app.joybox.domain.image.ImageStorage
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.util.*

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

    fun addImage(imageFile: MultipartFile): UUID {
        val uuid = imageStorage.save(imageFile.inputStream)
        val image = Image(
            uuid = uuid,
            name = imageFile.name,
            createdAt = null,
            product = null
        )
        return imageRepository.save(image).uuid
    }
}