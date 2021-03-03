package graph

import io.prophecy.libs._
import io.prophecy.libs.UDFUtils._
import io.prophecy.libs.Component._
import io.prophecy.libs.DataHelpers._
import io.prophecy.libs.SparkFunctions._
import io.prophecy.libs.FixedFileFormatImplicits._
import org.apache.spark.sql.ProphecyDataFrame._
import org.apache.spark._
import org.apache.spark.sql._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._
import config.ConfigStore._
import udfs.UDFs._
import graph._

@Visual(id = "ConnectByCustomer", label = "ConnectByCustomer", x = 405, y = 139, phase = 0)
object ConnectByCustomer {

  def apply(spark: SparkSession, left: DataFrame, right: DataFrame): Join = {
    import spark.implicits._

    val leftAlias  = left.as("left")
    val rightAlias = right.as("right")
    val dfJoin     = leftAlias.join(rightAlias, col("left_customer_id") === col("right_customer_id"), "inner")

    val out = dfJoin.select(
      col("left.first_name").as("first_name"),
      col("left.last_name").as("last_name"),
      col("right.amount").as("amount"),
      col("left.customer_id").as("customer_id")
    )

    out

  }

}