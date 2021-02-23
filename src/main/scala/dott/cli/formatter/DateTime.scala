package dott.cli.formatter

import com.monovore.decline.Argument
import com.monovore.decline.time.localDateTimeWithFormatter

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object DateTime {
  val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")

  val myDateArg: Argument[LocalDateTime] = localDateTimeWithFormatter(
    formatter
  )
}