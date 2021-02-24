package dott.cli.domain

final case class ItemIdentifier(value: Long)

final case class Item(identifier: ItemIdentifier,
                      productIdentifier: ProductIdentifier,
                      cost: BigDecimal,
                      shippingFee: BigDecimal,
                      taxAmount: BigDecimal)
