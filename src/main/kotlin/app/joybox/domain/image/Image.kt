package app.joybox.domain.image

import app.joybox.domain.product.Product
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "product_images")
@EntityListeners(AuditingEntityListener::class)
class Image {
    @Id
    var id: UUID? = null; private set

    @Column(name = "name")
    // TODO: @NotNull
    // 확인 필요
    var name: String = ""; private set

    @JoinColumn(name = "product_id")
    @ManyToOne
    var product: Product? = null; private set

    @Column(name = "created_at")
    @CreatedDate
    var createdAt: LocalDateTime = LocalDateTime.MIN; private set

    companion object {
        fun create(
            id: UUID?,
            name: String
        ): Image {
            val image = Image()
            image.id = id
            image.name = name
            return image
        }
    }
}