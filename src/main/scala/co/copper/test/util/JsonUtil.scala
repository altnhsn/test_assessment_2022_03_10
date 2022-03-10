package co.copper.test.util

import org.json4s.native.JsonMethods.parse
import org.json4s.{DefaultFormats, Formats}
import org.json4s.native.Serialization.write

object JsonUtil {

  implicit val formats: Formats = DefaultFormats

  def as[T](json: String)(implicit manifest: Manifest[T]): T = parse(json).extract[T]

  def toJsonString[T](obj: T): String = write(obj)
}
