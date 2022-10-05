package app.joybox.domain.product


import app.joybox.domain.image.Image
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.*

@SequenceGenerator(
    name = "PRODUCTS_SEQ_GEN",
    sequenceName = "PRODUCTS_SEQ",
    initialValue = 1,
    allocationSize = 1
)

@Entity
@Table(name = "products")
@EntityListeners(AuditingEntityListener::class)

class Product(id: Long? = null) {
    @Id
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "PRODUCTS_SEQ_GEN"
    )
    var id: Long? = id; private set

    @Column(name = "title")
    var title: String = ""

    @Column(name = "price")
    var price: Int = 0

    @Column(name = "description")
    @Lob
    var description: String = ""

    @Column(name = "images")
    @OneToMany(mappedBy = "product")
    var images: MutableList<Image> = mutableListOf()

    @Column(name = "created_at")
    @CreatedDate
    var createdAt: LocalDateTime = LocalDateTime.MIN
}