package app.joybox.domain.product


import app.joybox.domain.image.Image
import javax.persistence.*

@Entity
@Table(name = "products")
class Product(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long?,
    @Column(name = "title", nullable = false) val title: String,
    @Column(name = "price", nullable = false) val price: Int,
    @Column(name = "description") @Lob val description: String,
    @Column(name = "images") @OneToMany(mappedBy = "product") val images: List<Image>
)