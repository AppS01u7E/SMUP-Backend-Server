package com.appsolute.soomapi.domain.soom.service

import com.appsolute.soomapi.domain.soom.data.entity.Notice
import com.appsolute.soomapi.domain.soom.data.entity.Post
import com.appsolute.soomapi.domain.soom.data.entity.Reply
import com.appsolute.soomapi.domain.soom.data.request.PostNoticeRequest
import com.appsolute.soomapi.domain.soom.data.request.WriteReplyRequest
import com.appsolute.soomapi.domain.soom.data.response.NoticeResponse
import com.appsolute.soomapi.domain.soom.data.response.ReplyResponse
import com.appsolute.soomapi.domain.soom.data.type.PostType
import com.appsolute.soomapi.domain.soom.exception.GroupCannotFoundException
import com.appsolute.soomapi.domain.soom.exception.PostCannotFoundException
import org.springframework.data.domain.PageRequest
import org.springframework.web.multipart.MultipartFile
import java.util.*

interface NoticeService {

    fun getNoticeList(idx: Int, size: Int): List<NoticeResponse>

    fun getNoticeWithId(groupId: String, postId: String): NoticeResponse

    fun getGroupNoticeList(groupId: String, idx: Int, size: Int): List<NoticeResponse>

    fun postNotice(groupId: String, noticeRequest: PostNoticeRequest)

    fun patchNotice(noticeId: String, noticeRequest: PostNoticeRequest)

    fun deleteNotice(noticeId: String)

    fun attachFileToPost(file: MultipartFile, postId: String)

    fun patchNoticeFile(idx: Int, file: MultipartFile, postId: String)

    fun likeToNotice(postId: String)

    fun getReplyWithId(replyId: String): ReplyResponse

    fun getRepliesWithNotice(noticeId: String): List<ReplyResponse>

    fun writeReply(noticeId: String, writeReplyRequest: WriteReplyRequest)

    fun editReply(replyId: String, content: String)

    fun deleteReply(replyId: String)

    fun getReportById(replyId: String): ReplyResponse

    fun getReportListWithNotice(noticeId: String): List<Reply>

    fun getReportListWithMemberIdAndGroupId(memberId: String, groupId: String): List<ReplyResponse>

    fun submitReportToNotice(file: MultipartFile, reportId: String)

    fun changeReportFile(fileIdx: Int, file: MultipartFile, reportId: String)

    fun deleteReport(fileIdx: Int, reportId: String)


}