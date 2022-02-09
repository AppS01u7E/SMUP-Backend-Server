package com.appsolute.soomapi.domain.soom.service

import com.appsolute.soomapi.domain.soom.data.entity.Notice
import com.appsolute.soomapi.domain.soom.data.entity.Post
import com.appsolute.soomapi.domain.soom.data.entity.Reply
import com.appsolute.soomapi.domain.soom.data.request.PostNoticeRequest
import com.appsolute.soomapi.domain.soom.data.request.WriteReplyRequest
import com.appsolute.soomapi.domain.soom.data.response.NoticeResponse
import com.appsolute.soomapi.domain.soom.data.response.ReplyResponse
import com.appsolute.soomapi.domain.soom.data.response.ReportResponse
import com.appsolute.soomapi.domain.soom.data.type.PostType
import com.appsolute.soomapi.domain.soom.exception.GroupCannotFoundException
import com.appsolute.soomapi.domain.soom.exception.PostCannotFoundException
import org.springframework.data.domain.PageRequest
import org.springframework.web.multipart.MultipartFile
import java.util.*

interface NoticeService {

    //공지 리스트 가져오기
    fun getNoticeList(idx: Int, size: Int): List<NoticeResponse>
    //공지Id로 가져오기
    fun getNoticeWithId(groupId: String, postId: String): NoticeResponse
    //공지 리스트 가져오기
    fun getGroupNoticeList(groupId: String, idx: Int, size: Int): List<NoticeResponse>
    //공지 게시하기
    fun postNotice(groupId: String, noticeRequest: PostNoticeRequest)
    //공지 수정하기
    fun patchNotice(noticeId: String, noticeRequest: PostNoticeRequest)
    //공지 삭제하기
    fun deleteNotice(noticeId: String)
    //게시물에 파일 첨부하기
    fun attachFileToPost(file: MultipartFile, postId: String)
    //공지 파일 수정하기
    fun patchNoticeFile(idx: Int, file: MultipartFile, postId: String)
    //공지에 좋아요 누르기
    fun likeToNotice(postId: String)
    //id로 댓글 가져오기
    fun getReplyWithId(replyId: String): ReplyResponse
    //공지Id에 달린 댓글 가져오기
    fun getRepliesWithNotice(noticeId: String): List<ReplyResponse>
    //댓글 달기
    fun writeReply(noticeId: String, writeReplyRequest: WriteReplyRequest)
    //댓글 수정하기
    fun editReply(replyId: String, content: String)
    //댓글 삭제하기
    fun deleteReply(replyId: String)
    //과제Id로 가져오기
    fun getReportById(reportId: String): ReportResponse
    //공지에 제출한 과제 가져오기
    fun getReportListWithNotice(noticeId: String): List<ReportResponse>
    //해당 그룹의 멤버가 제출한 모든 과제 가져오기
    fun getReportListWithMemberIdAndGroupId(memberId: String, groupId: String): List<ReportResponse>
    //공지에 과제 제출하기
    fun submitReportToNotice(file: MultipartFile, reportId: String)
    //과제 파일 변경하기
    fun changeReportFile(fileIdx: Int, file: MultipartFile, reportId: String)
    //과제 삭제하기기
    fun deleteReport(fileIdx: Int, reportId: String)


}