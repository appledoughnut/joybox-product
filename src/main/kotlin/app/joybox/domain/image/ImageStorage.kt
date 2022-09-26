package app.joybox.domain.image

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.ObjectMetadata
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.io.InputStream

data class Image(
    val name: String,
    val data: InputStream
) {

}

interface ImageStorage {
    fun save(image: Image)
}

@Component
class S3ImageStorage(
    private val amazonS3: AmazonS3,
    @Value("\${cloud.aws.bucket}")
    private val bucket: String
): ImageStorage {

    override fun save(image: Image) {
        val metadata = ObjectMetadata()
        metadata.contentLength = image.data.available().toLong()
        amazonS3.putObject(this.bucket, image.name, image.data, metadata)
    }
}