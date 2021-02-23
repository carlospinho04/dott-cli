package dott.cli.domain

import java.time.LocalDateTime

final case class Order(customerName: String, contact: String,
                       shippingAddress: String, grandTotal: BigDecimal, orderDate: LocalDateTime, items: List[ItemIdentifier])
