package app.joybox.domain.product


import app.joybox.domain.image.Image
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "products")
class Product(id: Long? = null){
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    var id: Long? = null; private set

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
    val createdAt: LocalDateTime? = null
}