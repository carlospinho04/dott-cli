package dott.cli

import munit.CatsEffectSuite

class CliSpec extends CatsEffectSuite {
  test("Should group correctly orders") {
    val stream = new java.io.ByteArrayOutputStream()
    Console.withOut(stream) {
      //all printlns in this block will be redirected
      Cli.main(Array("filter", "2021-01-01T00:00:00", "2022-01-01T00:00:00", "1-3", "4-7", "8-12", ">4", "<8"))
    }
    val expected = """1-3 months: 0 orders
                     |4-7 months: 1 orders
                     |8-12 months: 2 orders
                     |> 4 months: 3 orders
                     |< 8 months: 1 orders
                     |""".stripMargin
    assertEquals(stream.toString, expected)
  }
}
