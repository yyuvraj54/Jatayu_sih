package com.example.jatayu_sih.websocket

import android.util.Log
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener

class webSocketListener: WebSocketListener(){
    override fun onOpen(webSocket: WebSocket, response: Response) {
        // WebSocket connection established
        // You can send messages here, e.g., webSocket.send("Hello, Server!")
        Log.d("webSocket:","WebSocket Opened")
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        // Received a text message from the server
        // Handle the message

        Log.d("webSocket:",text!!)
    }



    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        webSocket.close(NORMAL_CLOSURE_STATUS,null)
        Log.d("webSocket","closing:,$code / $reason")
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        Log.d("webSocket","Failed:"+t.message)
    }

    companion object{
        private const val NORMAL_CLOSURE_STATUS=1000
    }
}