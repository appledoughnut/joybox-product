package app.joybox.domain.image

import app.joybox.domain.product.Product
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Entity
@Table(name="product_images")
@EntityListeners( AuditingEntityListener::class )
class Image(id: UUID? = null) {
    @Id
    var id: UUID? = id; private set

    @Column(name = "name")
    // TODO: @NotNull
    // 확인 필요
    var name: String = ""

    @JoinColumn(name = "product_id")
    @ManyToOne
    var product: Product? = null

    @Column(name = "created_at")
    @CreatedDate
    var createdAt: LocalDateTime = LocalDateTime.MIN
}