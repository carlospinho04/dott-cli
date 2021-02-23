package dott.cli

import cats.implicits._
import com.monovore.decline._
import com.monovore.decline.time._

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object DateTime {
  val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")

  val myDateArg: Argument[LocalDateTime] = localDateTimeWithFormatter(
    formatter
  )
}


object Commands {
  val myDateArg: Argument[LocalDateTime] = localDateTimeWithFormatter(
    DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
  )

  private val startDateOpt = Opts.option[LocalDateTime]("start-date", help = "start interval date")

  private val endDateOpt = Opts.option[LocalDateTime]("end-date", help = "end interval date")

  case class DatePeriod(startDate: LocalDateTime, endDate: LocalDateTime)

  val filterOrdersOpts: Opts[DatePeriod] = Opts.subcommand("filter", "filter orders") {
    (startDateOpt, endDateOpt).mapN(DatePeriod)
  }

}