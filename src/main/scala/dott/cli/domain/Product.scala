package dott.cli.domain

import java.time.LocalDateTime

final case class ProductIdentifier(value: Long)

final case class Product(identifier: ProductIdentifier,
                         name: String,
                         category: String,
                         weight: Float,
                         price: BigDecimal,
                         creationDate: LocalDateTime)
