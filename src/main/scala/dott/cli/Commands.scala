package dott.cli

import cats.data.{NonEmptyList, Validated}
import cats.implicits._
import com.monovore.decline._
import com.monovore.decline.time._
import dott.cli.domain.Interval

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object Commands {
  val myDateArg: Argument[LocalDateTime] = localDateTimeWithFormatter(
    DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
  )

  private val startDateOpt = Opts.argument[LocalDateTime]("start-date")

  private val endDateOpt = Opts.argument[LocalDateTime]("end-date")

  private val intervals = Opts.arguments[String]("intervals")
    .mapValidated { list =>
      list.traverse { s =>
        s.split("-", 2) match {
          case Array(key, value) => Validated.valid(Interval(key.toInt, value.toInt))
          case _ => Validated.invalidNel(s"Invalid key:value pair: $list")
        }
      }

    }

  case class FilterArguments(startDate: LocalDateTime, endDate: LocalDateTime, intervals: NonEmptyList[Interval])

  val filterOrdersOpts: Opts[FilterArguments] = Opts.subcommand("filter", "filter orders") {
    (startDateOpt, endDateOpt, intervals).mapN(FilterArguments)
  }

}