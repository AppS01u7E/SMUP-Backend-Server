package com.appsolute.soomapi.domain.soom.controller

import com.appsolute.soomapi.domain.soom.data.request.PostNoticeRequest
import com.appsolute.soomapi.domain.soom.data.request.WriteReplyRequest
import com.appsolute.soomapi.domain.soom.data.response.NoticeResponse
import com.appsolute.soomapi.domain.soom.data.response.ReplyResponse
import com.appsolute.soomapi.domain.soom.data.response.ReportResponse
import com.appsolute.soomapi.domain.soom.service.NoticeService
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile


@RestController
@RequestMapping("/api/v1/group")
class NoticeController(
    private val noticeService: NoticeService

) {
    //공지 리스트 가져오기
    @GetMapping("/notice/list")
    fun getNoticeList(@RequestParam idx: Int, @RequestParam size: Int): List<NoticeResponse> {
        return noticeService.getNoticeList(idx, size)
    }
    //공지 Id로 가져오기
    @GetMapping("/notice/{noticeId}")
    fun getNoticeWithId(@PathVariable noticeId: String, @RequestParam groupId: String): NoticeResponse{
        return noticeService.getNoticeWithId(groupId, noticeId)
    }
    //해당 그룹의 공지 리스트 가져오기
    @GetMapping("/{groupId}/notice/list")
    fun getGroupNoticeList(@PathVariable groupId: String, @RequestParam idx: Int, @RequestParam size: Int): List<NoticeResponse>{
        return noticeService.getGroupNoticeList(groupId, idx, size)
    }
    //공지 등록하기
    @PostMapping("/{groupId}/notice")
    fun postNotice(@PathVariable groupId: String, @RequestBody request: PostNoticeRequest) {
        return noticeService.postNotice(groupId, request)
    }
    //공지 수정하기
    @PatchMapping("/notice/{noticeId}")
    fun editNotice(@PathVariable noticeId: String, @RequestBody request: PostNoticeRequest){
        return noticeService.patchNotice(noticeId, request)
    }
    //공지 삭제하기
    @DeleteMapping("/notice/{noticeId}")
    fun deleteNotice(@PathVariable noticeId: String){
        return noticeService.deleteNotice(noticeId)
    }
    //공지에 파일 첨부하기
    @PostMapping("/notice/{noticeId}/file")
    fun attachFileToPost(@PathVariable postId: String, @ModelAttribute file: MultipartFile) {
        return noticeService.attachFileToPost(file, postId)
    }
    //공지에 첨부된 index에 해당하는 파일 변경
    @PatchMapping("/notice/{noticeId}/file/{idx}")
    fun patchNoticeFile(@PathVariable idx: Int, @PathVariable noticeId: String, @ModelAttribute file: MultipartFile) {
        return noticeService.patchNoticeFile(idx, file, noticeId)
    }
    //notice에 좋아요 누르기
    @PostMapping("/notice/{noticeId}/like")
    fun likeToNotice(@PathVariable noticeId: String) {
        return noticeService.likeToNotice(noticeId)
    }
    //replyId에 해당하는 댓글 확인하기
    @GetMapping("/notice/reply/{replyId}")
    fun getReplyWithId(@PathVariable replyId: String): ReplyResponse{
        return noticeService.getReplyWithId(replyId)
    }
    //공지 Id에 해당하는 게시물에 달린 댓글 리스트 가져오기
    @GetMapping("/notice/{noticeId}/reply")
    fun getReplyWithNotice(@PathVariable noticeId: String): List<ReplyResponse> {
        return noticeService.getRepliesWithNotice(noticeId)
    }
    //댓글 작성하기
    @PostMapping("/notice/{noticeId}")
    fun writeReply(@PathVariable noticeId: String, @RequestBody request: WriteReplyRequest) {
        return noticeService.writeReply(noticeId, request)
    }
    //댓글 수정
    @PatchMapping("/notice/reply/{replyId}")
    fun editReply(@PathVariable replyId: String, @RequestBody content: String) {
        return noticeService.editReply(replyId, content)
    }
    //댓글 삭제
    @DeleteMapping("/notice/reply/{replyId}")
    fun deleteReply(@PathVariable replyId: String) {
        return noticeService.deleteReply(replyId)
    }
    //과제 Id로 가져오기
    @GetMapping("/notice/report/{reportId}")
    fun getReportWithId(@PathVariable reportId: String): ReportResponse {
        return noticeService.getReportById(reportId)
    }
    //공지에 제출한 과제 가져오기
    @GetMapping("/notice/{noticeId}/report")
    fun getReportWithNotice(@PathVariable noticeId: String): List<ReportResponse> {
        return noticeService.getReportListWithNotice(noticeId)
    }
    //해당 그룹의 멤버가 제출한 모든 과제 확인하기
    @GetMapping("/{groupId}/notice/report")
    fun getReportListWithMemberIdAndGroupId(@PathVariable groupId: String, @RequestParam memberId: String): List<ReportResponse> {
        return noticeService.getReportListWithMemberIdAndGroupId(memberId, groupId)
    }
    //공지에 과제 제출하기
    @PostMapping("/group/report/{reportId}")
    fun submitReportToNotice(@PathVariable reportId: String, @ModelAttribute file: MultipartFile){
        return noticeService.submitReportToNotice(file, reportId)
    }
    //과제 파일 변경하기
    @PostMapping("/group/report/{reportId}/{idx}")
    fun changeReportFile(@PathVariable reportId: String, @PathVariable idx: Int, @ModelAttribute file: MultipartFile){
        return noticeService.changeReportFile(idx, file, reportId)
    }
    //파일 삭제하기
    @DeleteMapping("/group/report/{reportId}/{idx}")
    fun deleteReport(@PathVariable reportId: String, @PathVariable idx: Int) {
        return noticeService.deleteReport(idx, reportId)
    }

}