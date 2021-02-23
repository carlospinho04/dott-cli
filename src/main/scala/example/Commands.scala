package example

import cats.implicits._
import com.monovore.decline._
import com.monovore.decline.time._

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object Commands {


  val myDateArg: Argument[LocalDateTime] = localDateTimeWithFormatter(
    DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
  )

  private val userOpt =
    Opts.option[String]("user", help = "Person to greet.").withDefault("world")

  private val quietOpt = Opts.flag("quiet", help = "Whether to be quiet.").orFalse

  private val sadOpt = Opts.flag("sad", help = "Whether it's sad user is leaving").orFalse

  private val startDateOpt = Opts.option[LocalDateTime]("start-date", help = "start interval date")

  private val endDateOpt = Opts.option[LocalDateTime]("end-date", help = "end interval date")

  case class SayHello(
                       user: String,
                       quiet: Boolean
                     )

  case class SayGoodbye(
                         user: String,
                         sad: Boolean
                       )

  case class DatePeriod(startDate: LocalDateTime, endDate: LocalDateTime)

  val filterOrdersOpts: Opts[DatePeriod] = Opts.subcommand("filter", "filter orders") {
    (startDateOpt, endDateOpt).mapN(DatePeriod)
  }

  val helloOpts: Opts[SayHello] =
    Opts.subcommand("hello", "Say hello") {
      (userOpt, quietOpt).mapN(SayHello)
    }

  val goodbyeOpts: Opts[SayGoodbye] =
    Opts.subcommand("goodbye", "Say goodbye") {
      (userOpt, sadOpt).mapN(SayGoodbye)
    }

}