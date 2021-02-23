package example

import munit.CatsEffectSuite

class HelloSpec extends CatsEffectSuite {
  test("Should say hello") {
    assertEquals("hello", "hello")
  }
}
