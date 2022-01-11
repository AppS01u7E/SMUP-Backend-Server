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
import com.appsolute.soomapi.domain.soom.repository.GroupRepository
import com.appsolute.soomapi.domain.soom.repository.NoticeRepository
import com.appsolute.soomapi.domain.soom.repository.PostRepository
import com.appsolute.soomapi.domain.soom.repository.ReplyRepository
import com.appsolute.soomapi.global.security.CurrentUser
import org.springframework.data.domain.PageRequest
import java.util.*

class NoticeServiceIml(
    private val postRepository: PostRepository,
    private val noticeRepository: NoticeRepository,
    private val groupRepository: GroupRepository,
    private val current: CurrentUser,
    private val replyRepository: ReplyRepository

): NoticeService {

    override fun getNoticeList(idx: Int, size: Int): List<NoticeResponse>{
        return postRepository.findAllByReceiverListContainsAndPostType(
            current.getUser(),
            PageRequest.of(idx, size),
            PostType.NOTICE
        ).stream().map {
            it.toNoticeResponse()
        }.toList()
    }

    override fun getNoticeWithId(groupId: String, postId: String): NoticeResponse {
        var group = groupRepository.findById(groupId).orElse(null)?: throw GroupCannotFoundException()
        var notice = noticeRepository.findById(postId).orElse(null)?: throw PostCannotFoundException()
        if (group.postList.contains(notice)) throw PostCannotFoundException()
        return (notice).toNoticeResponse()
    }

    override fun getGroupNoticeList(groupId: String, idx: Int, size: Int): List<NoticeResponse>{
        return noticeRepository.findAll(PageRequest.of(idx, size)).stream().map {
            it.toNoticeResponse()
        }.toList()
    }

    override fun postNotice(groupId: String, noticeRequest: PostNoticeRequest){
        noticeRepository.save(
            Notice(
                UUID.randomUUID().toString(),
                noticeRequest.title,
                noticeRequest.content,
                current.getUser(),
                groupRepository.findById(groupId).orElse(null)?: throw GroupCannotFoundException()
            )
        )
    }

    override fun patchNotice(noticeId: String, noticeRequest: PostNoticeRequest){
        noticeRepository.save(
            noticeRepository.findById(noticeId).map {
                it.editNotice(noticeRequest)
            }.orElse(null)?: throw PostCannotFoundException()
        )
    }

    override fun deleteNotice(noticeId: String){
        val notice = noticeRepository.findById(noticeId).orElse(null)?: throw PostCannotFoundException()
        if (notice.getWriter().equals(current.getUser())) noticeRepository.delete(notice)
    }

    override fun attachFileWithNotice(){

    }

    override fun patchNoticeFile(){

    }

    override fun likeToNotice(postId: String){
        postRepository.save(
            postRepository.findById(postId).map {
                it.like(current.getUser())
            }.orElse(null)?: throw PostCannotFoundException()
        )
    }

    override fun getReplyWithId(replyId: String): ReplyResponse {
        return (postRepository.findById(replyId).orElse(null)
            ?: throw PostCannotFoundException())
            .toReplyResponse()
    }

    override fun getRepliesWithNotice(noticeId: String): List<ReplyResponse>{
        return noticeRepository.findById(noticeId).map {
            it.getAimingAtThisPostList().stream().map {
                it.toReplyResponse()
            }.toList()
        }.orElse(null)?: throw PostCannotFoundException()
    }

    override fun writeReply(noticeId: String, writeReplyRequest: WriteReplyRequest){
        val notice: Post = noticeRepository.findById(noticeId).orElse(null)?: throw PostCannotFoundException()
        replyRepository.save(
            Reply(
                UUID.randomUUID().toString(),
                writeReplyRequest.content,
                current.getUser(),
                notice,
                notice.getGroup()
            )
        )
    }

    override fun editReply(replyId: String, content: String){
        replyRepository.findByIdAndWriter(replyId, current.getUser()).map {
            it.setTitle(content)
        }.orElse(null)?: throw PostCannotFoundException()
    }

    override fun deleteReply(replyId: String){
        replyRepository.findByIdAndWriter(replyId, current.getUser()).map {
            replyRepository.delete(it)
        }.orElse(null)?: throw PostCannotFoundException()
    }

    override fun getReportListWithNotice(){

    }

    override fun getReportListWithMember(){

    }

    override fun submitReportToNotice(){

    }

    override fun editReport(){

    }

    override fun deleteReport(){

    }


}