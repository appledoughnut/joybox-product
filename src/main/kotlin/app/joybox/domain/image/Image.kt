package app.joybox.domain.image

import app.joybox.domain.product.Product
import org.springframework.data.annotation.CreatedDate
import java.io.InputStream
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name="product_images")
data class Image(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long?,
        val name: String,
        @CreatedDate
        val createdAt: LocalDateTime,
        @ManyToOne
)