package dott.cli

import cats.data.{NonEmptyList, Validated}
import cats.implicits._
import com.monovore.decline._
import com.monovore.decline.time._
import dott.cli.domain.Interval

import java.time.LocalDateTime

object Commands {

  private val startDateOpt = Opts.argument[LocalDateTime]("start-date")

  private val endDateOpt = Opts.argument[LocalDateTime]("end-date")

  private val intervals = Opts
    .arguments[String]("intervals")
    .mapValidated { intervals =>
      intervals.traverse { interval =>
        interval.split("-", 2) match {
          case Array(minMonth, maxMonth) if checkIfIsNumber(minMonth) && checkIfIsNumber(maxMonth) =>
            Validated.valid(Interval(minMonth.toIntOption, maxMonth.toIntOption))
          case Array(s">$minMonth") if checkIfIsNumber(minMonth) =>
            Validated.valid(Interval(Option(minMonth.toInt), None))
          case Array(s"<$maxMonth") if checkIfIsNumber(maxMonth) =>
            Validated.valid(Interval(None, Option(maxMonth.toInt)))
          case _ => Validated.invalidNel(s"Invalid min and max month: $intervals")
        }
      }
    }

  private def checkIfIsNumber(str: String): Boolean = {
    str.forall(Character.isDigit)
  }

  case class FilterArguments(startDate: LocalDateTime, endDate: LocalDateTime, intervals: NonEmptyList[Interval])

  val filterOrdersOpts: Opts[FilterArguments] =
    Opts.subcommand("filter", "filter orders in a specific period of time") {
      (startDateOpt, endDateOpt, intervals).mapN(FilterArguments)
    }

}
