package app.joybox.domain.product


import app.joybox.domain.image.Image
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(name = "products")
class Product(
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE) val id: Long?,
    @Column(name = "title") val title: String,
    @Column(name = "price") val price: Int,
    @Column(name = "description") @Lob val description: String?,
    @Column(name = "images") @OneToMany(mappedBy = "product") val images: List<Image>
)