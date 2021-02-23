import sbt._

object Dependencies {
  private val catsVersion = "2.3.0"
  private val declineVersion = "1.3.0"
  private val circeVersion = "0.13.0"
  private val munitVersion = "0.7.20"
  private val munitCatsEffectVersion = "0.13.0"

  lazy val cliDependencies = List(
    "org.typelevel"                %% "cats-core"                      % catsVersion,
    "com.monovore"                 %% "decline"                        % declineVersion,
    "com.monovore"                 %% "decline-effect"                 % declineVersion,
    "io.circe"                     %% "circe-generic"                  % circeVersion,
    "org.scalameta"                %% "munit"                          % munitVersion % Test,
    "org.typelevel"                %% "munit-cats-effect-2"            % munitCatsEffectVersion % Test)
}
