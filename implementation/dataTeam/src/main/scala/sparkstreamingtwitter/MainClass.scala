package sparkstreamingtwitter

import java.util.Date

import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.streaming.dstream.DStream
import org.apache.spark.streaming.twitter._
import org.apache.spark.streaming.{Seconds, StreamingContext}
import twitter4j.User

object MainClass {

  def main(args: Array[String]): Unit = {
    val consumerKey = args(0)
    val consumerSecret = args(1)
    val accessToken = args(2)
    val accessTokenSecret = args(3)

    System.setProperty("twitter4j.oauth.consumerKey", consumerKey)
    System.setProperty("twitter4j.oauth.consumerSecret", consumerSecret)
    System.setProperty("twitter4j.oauth.accessToken", accessToken)
    System.setProperty("twitter4j.oauth.accessTokenSecret", accessTokenSecret)

    val sparkConf = new SparkConf().setMaster("local[2]").setAppName("TwitterPopularTags")
    val ssc = new StreamingContext(sparkConf, Seconds(1)) // batch interval
    val stream = TwitterUtils.createStream(ssc, None)

    val tweets: DStream[(Date, User, String)] = stream.map { status => (status.getCreatedAt, status.getUser, status.getText) }

    val tic = System.currentTimeMillis()

    tweets.foreachRDD { rdd =>
      val tic_stat = System.currentTimeMillis()
      val userTweet: RDD[(String, Int)] = rdd.map {
        case (date, user, text) =>
          user.getLang -> text.length
      }
      val dataGrouped: RDD[(String, Iterable[Int])] = userTweet.groupByKey()
      val languageInfo: RDD[(String, (Int, Double))] = dataGrouped.map { case (language, textLengthList) =>
        language ->(textLengthList.size, textLengthList.sum / textLengthList.size.toDouble)
      }
      val toc = System.currentTimeMillis() - tic
      val toc_stat = System.currentTimeMillis() - tic_stat

      displayHeader()
      displayResults(languageInfo, toc, toc_stat)
    }

    ssc.start()
    ssc.awaitTermination()
  }

  def displayHeader(): Unit = {
    println("\n------------")
    println("Nationality -> (nbTweets, averageTweetLength)")
  }

  def displayResults(rdd: RDD[(String, (Int, Double))], duration: Long, stat_duration: Long): Unit = {
    rdd.foreach { case (language, (nbTweets, meanTweetSize)) =>
      println( s"""$language -> ($nbTweets, $meanTweetSize)""")
    }
    println( s"""Statistics duration: $stat_duration ms, Execution time: $duration ms""")
  }
}
