package com.appsolute.soomapi.domain.chat.controller

import com.appsolute.soomapi.domain.chat.data.request.ApplyInterviewRequest
import com.appsolute.soomapi.domain.chat.data.request.ConcludeInterviewRequest
import com.appsolute.soomapi.domain.chat.data.response.ChatRoomListResponse
import com.appsolute.soomapi.domain.chat.data.response.MessageResponse
import com.appsolute.soomapi.domain.chat.data.response.OneChatRoomResponse
import com.appsolute.soomapi.domain.chat.service.ChatService
import com.appsolute.soomapi.domain.chat.service.InterviewService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/v1/chat")
class ChatController(
    private val chatService: ChatService
) {


    @GetMapping("/{chatRoomId}")
    fun getMessageList(@PathVariable chatRoomId: String, @RequestParam idx: Int, @RequestParam size: Int): OneChatRoomResponse{
        return chatService.getMessage(chatRoomId, idx, size)
    }

    @GetMapping("/rooms")
    fun getChatRoom(): ChatRoomListResponse {
        return chatService.getChatRoomList()
    }

    @DeleteMapping
    fun deleteMessage(@RequestParam messageId: String): ResponseEntity.BodyBuilder{
        chatService.deleteMessage(messageId)
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
    }

    @PatchMapping
    fun editMessage(@RequestParam messageId: String, @RequestBody content: String) {
        return chatService.editMessage(messageId, content)
    }

}