package app.joybox.domain.image

import app.joybox.domain.product.Product
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(name="product_images")
@EntityListeners( AuditingEntityListener::class )
data class Image(
    @Id val uuid: UUID,
    @Column(name = "name") @NotNull val name: String,
    @Column(name = "created_at") @CreatedDate val createdAt: LocalDateTime?,
    @JoinColumn(name = "product_id") @ManyToOne val product: Product?
)