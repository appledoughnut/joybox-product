package app.joybox.domain.model

import javax.persistence.*

@Entity
@Table(name = "products")
class Product(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long?,
    @Column(name = "title") val title: String,
    @Column(name = "price") val price: Int,
    @Column(name = "description") val description: String,
)