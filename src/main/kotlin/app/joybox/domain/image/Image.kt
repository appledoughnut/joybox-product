package app.joybox.domain.image

import app.joybox.domain.product.Product
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name="product_images")
data class Image(
        @Id @GeneratedValue(strategy = GenerationType.SEQUENCE) val id: Long,
        @Column(name = "name", nullable = false) val name: String,
        @Column(name = "created_at", nullable = false) @CreatedDate val createdAt: LocalDateTime,
        @JoinColumn(name = "product_id") @ManyToOne val product: Product
)