package co.copper.test

import org.asynchttpclient.{AsyncHttpClient, Response}

import scala.compat.java8.FutureConverters.CompletionStageOps
import scala.concurrent.Future

package object util {
  implicit class AsyncHttpClientOps(httpClient: AsyncHttpClient) {
    def executeGet(url: String): Future[Response] = {
      httpClient.prepareGet(url).execute().toCompletableFuture.toScala
    }
  }
}
