package app.joybox.domain.product

import app.joybox.domain.image.Image
import app.joybox.domain.image.ImageRepository
import app.joybox.domain.image.ImageStorage
import mu.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.util.*

class ProductNotFoundException : Exception()
class ImageNotFoundException : Exception()
class NullIdException : Exception()

val logger = KotlinLogging.logger {  }

@Service
class ProductService(
    private val productRepository: ProductRepository,
    private val imageRepository: ImageRepository,
    private val imageStorage: ImageStorage
) {
    fun getProduct(id: Long): Product {
        return if (productRepository.existsById(id)) {
            logger.info { "Get product" }
            logger.debug { "with product id = $id" }
            productRepository.findById(id).get()
        } else {
            throw ProductNotFoundException()
        }
    }

    fun getProducts(): List<Product> {
        logger.info { "Get product list" }
        return productRepository.findAll()
    }

    fun addProduct(command: AddProductCommand) {
        val imageIds = command.imageIds
        val images = if (imageIds.isEmpty()) {
            logger.debug { "Image id list is empty" }
            emptyList()
        } else {
            imageRepository.findByUuidIn(imageIds)
        }

        val product = Product()
        product.title = command.title
        product.price = command.price
        product.description = command.description
        product.images = images.toMutableList()

        logger.info { "Add product" }
        productRepository.save(product)
    }

    fun deleteProduct(id: Long) {
        if (productRepository.existsById(id)) {
            logger.info { "Delete product" }
            logger.debug { "with product id = $id" }
            productRepository.deleteById(id)
        } else {
            throw ProductNotFoundException()
        }
    }

    fun addImage(imageFile: MultipartFile): UUID {
        val id = imageStorage.save(imageFile.inputStream)
        val image = Image.create(id, imageFile.name)
        logger.info { "Add image" }
        return imageRepository.save(image).uuid!!
    }

    fun updateProduct(command: UpdateProductCommand) {
        val id = command.id
        val product = if(productRepository.existsById(id)) {
            logger.info { "Get product to be updated" }
            logger.debug { "with product id = $id" }
            productRepository.findById(id).get()
        } else {
            throw ProductNotFoundException()
        }

        val imageIds = command.imageIds
        val images = if (imageIds.isEmpty()) {
            logger.debug { "Image id list is empty" }
            emptyList()
        } else {
            imageRepository.findByUuidIn(imageIds)
        }

        product.title = command.title
        product.price = command.price
        product.description = command.description
        product.images = images.toMutableList()

        logger.info { "Update product" }
        productRepository.save(product)
    }
}













