package com.example.menuactions.classes

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableEmitter
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString

class WeatherWebClientSocket {
    private val client = OkHttpClient()

    fun connect(url: String): Observable<String> {
        return Observable.create { emitter: ObservableEmitter<String> ->

            val request = Request.Builder()
                .url(url)
                .build()

            val listener = object : WebSocketListener() {
                override fun onOpen(webSocket: WebSocket, response: okhttp3.Response) {
                    emitter.onNext("Conectado al servidor WebSocket:Detalles:Clima:0")
                }

                override fun onMessage(webSocket: WebSocket, text: String) {
                    emitter.onNext("Mensaje recibido: $text")
                }

                override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
                    emitter.onNext("Mensaje binario: ${bytes.hex()}")
                }

                override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                    emitter.onNext("Cerrando conexión: $reason")
                    emitter.onComplete()
                }

                override fun onFailure(webSocket: WebSocket, t: Throwable, response: okhttp3.Response?) {
                    emitter.onError(t)
                }
            }

            client.newWebSocket(request, listener)

            emitter.setCancellable {
                client.dispatcher.executorService.shutdown()
            }
        }
    }
}