package com.appsolute.soomapi.domain.soom.service

import com.appsolute.soomapi.domain.account.data.entity.user.User
import com.appsolute.soomapi.domain.account.exception.UserNotFoundException
import com.appsolute.soomapi.domain.account.repository.UserRepository
import com.appsolute.soomapi.domain.soom.data.entity.*
import com.appsolute.soomapi.domain.soom.data.request.PostNoticeRequest
import com.appsolute.soomapi.domain.soom.data.request.WriteReplyRequest
import com.appsolute.soomapi.domain.soom.data.response.NoticeResponse
import com.appsolute.soomapi.domain.soom.data.response.ReplyResponse
import com.appsolute.soomapi.domain.soom.data.response.ReportResponse
import com.appsolute.soomapi.domain.soom.data.type.FileType
import com.appsolute.soomapi.domain.soom.data.type.PostType
import com.appsolute.soomapi.domain.soom.data.type.ReplyType
import com.appsolute.soomapi.domain.soom.exception.GroupCannotFoundException
import com.appsolute.soomapi.domain.soom.exception.PostCannotFoundException
import com.appsolute.soomapi.domain.soom.repository.group.GroupRepository
import com.appsolute.soomapi.domain.soom.repository.post.NoticeRepository
import com.appsolute.soomapi.domain.soom.repository.post.PostRepository
import com.appsolute.soomapi.domain.soom.repository.post.ReplyRepository
import com.appsolute.soomapi.global.security.CurrentUser
import com.appsolute.soomapi.infra.service.s3.S3Util
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.util.*
import javax.transaction.Transactional


@Service
class NoticeServiceIml(
    private val postRepository: PostRepository,
    private val noticeRepository: NoticeRepository,
    private val groupRepository: GroupRepository,
    private val current: CurrentUser,
    private val replyRepository: ReplyRepository,
    private val s3util: S3Util,
    private val userRepository: UserRepository

): NoticeService {

    @Transactional
    override fun getNoticeList(idx: Int, size: Int): List<NoticeResponse>{
        return postRepository.findAllByReceiverListContainsAndPostType(
            current.getUser(),
            PageRequest.of(idx, size),
            PostType.NOTICE
        ).stream().map {
            it.toNoticeResponse(current.getUser())
        }.toList()
    }

    @Transactional
    override fun getNoticeWithId(groupId: String, postId: String): NoticeResponse {
        val group = groupRepository.findById(groupId).orElse(null)?: throw GroupCannotFoundException(groupId)
        val notice = noticeRepository.findById(postId).orElse(null)?: throw PostCannotFoundException(postId)
        if (group.postList.contains(notice)) throw PostCannotFoundException(postId)
        return (notice).toNoticeResponse(current.getUser())
    }

    @Transactional
    override fun getGroupNoticeList(groupId: String, idx: Int, size: Int): List<NoticeResponse>{
        return noticeRepository.findAll(PageRequest.of(idx, size)).stream().map {
            it.toNoticeResponse(current.getUser())
        }.toList()
    }

    @Transactional
    override fun postNotice(groupId: String, noticeRequest: PostNoticeRequest){
        noticeRepository.save(
            Notice(
                UUID.randomUUID().toString(),
                noticeRequest.title,
                noticeRequest.content,
                current.getUser(),
                groupRepository.findById(groupId).orElse(null)?: throw GroupCannotFoundException(groupId)
            )
        )
    }

    @Transactional
    override fun patchNotice(noticeId: String, noticeRequest: PostNoticeRequest){
        noticeRepository.save(
            noticeRepository.findById(noticeId).map {
                it.editNotice(noticeRequest)
            }.orElse(null)?: throw PostCannotFoundException(noticeId)
        )
    }

    @Transactional
    override fun deleteNotice(noticeId: String){
        val notice = noticeRepository.findById(noticeId).orElse(null)?: throw PostCannotFoundException(noticeId)
        if (notice.getWriter().equals(current.getUser())) noticeRepository.delete(notice)
    }

    @Transactional
    override fun attachFileToPost(file: MultipartFile, postId: String){
        val notice: Notice = noticeRepository.findByIdAndWriter(postId, current.getUser()).orElse(null)?: throw PostCannotFoundException(postId)
        val fileKey: String = s3util.upload(file, "${notice.getGroup().id}/notice/${notice.id}")
        notice.attachFile(File(
            fileKey,
            FileType.DOCS,
            file.originalFilename!!.substring(file.originalFilename!!.indexOf(".") + 1)
        ))

        noticeRepository.save(notice)
    }

    @Transactional
    override fun patchNoticeFile(idx: Int, file: MultipartFile, postId: String){
        val notice: Notice = noticeRepository.findByIdAndWriter(postId, current.getUser()).orElse(null)?: throw PostCannotFoundException(postId)
        val fileKey: String = s3util.upload(file, "${notice.getGroup().id}/notice/${notice.id}")
        notice.changeFile(File(
            fileKey,
            FileType.DOCS,
            file.originalFilename!!.substring(file.originalFilename!!.indexOf(".") + 1)
        ), idx)
        noticeRepository.save(notice)
    }

    @Transactional
    override fun likeToNotice(postId: String){
        postRepository.save(
            postRepository.findById(postId).map {
                it.like(current.getUser())
            }.orElse(null)?: throw PostCannotFoundException(postId)
        )
    }

    @Transactional
    override fun getReplyWithId(replyId: String): ReplyResponse {
        return (postRepository.findById(replyId).orElse(null)
            ?: throw PostCannotFoundException(replyId))
            .toReplyResponse()
    }

    @Transactional
    override fun getRepliesWithNotice(noticeId: String): List<ReplyResponse>{
        return noticeRepository.findById(noticeId).map {
            it.getAimingAtThisPostList().filter {
                reply -> reply.getReplyType().equals(ReplyType.COMMENT)
            }.map {
                reply -> reply.toReplyResponse()
            }.toList()
        }.orElse(null)?: throw PostCannotFoundException(noticeId)
    }

    @Transactional
    override fun writeReply(noticeId: String, writeReplyRequest: WriteReplyRequest){
        val notice: Post = noticeRepository.findById(noticeId).orElse(null)?: throw PostCannotFoundException(noticeId)
        replyRepository.save(
            Reply(
                UUID.randomUUID().toString(),
                writeReplyRequest.content,
                current.getUser(),
                notice,
                notice.getGroup(),
                ReplyType.COMMENT
            )
        )
    }

    @Transactional
    override fun editReply(replyId: String, content: String){
        replyRepository.findByIdAndWriter(replyId, current.getUser()).map {
            it.setTitle(content)
        }.orElse(null)?: throw PostCannotFoundException(replyId)
    }

    @Transactional
    override fun deleteReply(replyId: String){
        replyRepository.findByIdAndWriter(replyId, current.getUser()).map {
            replyRepository.delete(it)
        }.orElse(null)?: throw PostCannotFoundException(replyId)
    }

    @Transactional
    override fun getReportById(replyId: String): ReportResponse{
        return (replyRepository.findByIdAndWriter(replyId, current.getUser()).orElse(null)?: throw PostCannotFoundException(replyId)).toReportResponse()
    }

    @Transactional
    override fun getReportListWithNotice(noticeId: String): List<ReportResponse>{
        val notice: Notice = noticeRepository.findById(noticeId).orElse(null)?: throw PostCannotFoundException(noticeId)
        return notice.getAimingAtThisPostList().filter { reply -> reply.getReplyType().equals(ReplyType.REPORT) }.map { reply -> reply.toReportResponse() }
    }

    @Transactional
    override fun getReportListWithMemberIdAndGroupId(memberId: String, groupId: String): List<ReportResponse> {
        val targetUser: User = userRepository.findById(memberId).orElse(null) ?: throw UserNotFoundException(memberId)
        val targetSoom: Soom = groupRepository.findByIdAndHeader(groupId, current.getUser()).orElse(null)
            ?: throw GroupCannotFoundException(groupId)
        return replyRepository.findAllByReplyTypeAndWriterAndGroup(ReplyType.REPORT, targetUser, targetSoom).map {
            it.toReportResponse()
        }
    }

    @Transactional
    override fun submitReportToNotice(file: MultipartFile, reportId: String){
        val report: Reply = replyRepository.findByIdAndWriter(reportId, current.getUser()).orElse(null)?: throw PostCannotFoundException(reportId)
        val fileKey: String = s3util.upload(file, "${report.getGroup().id}/report/${reportId}")
        report.attachFile(File(
            fileKey,
            FileType.DOCS,
            file.originalFilename!!.substring(file.originalFilename!!.indexOf(".") + 1)
        ))
        replyRepository.save(report)
    }

    @Transactional
    override fun changeReportFile(fileIdx: Int, file: MultipartFile, reportId: String) {
        val report: Reply = replyRepository.findByIdAndWriter(reportId, current.getUser()).orElse(null)?: throw PostCannotFoundException(reportId)
        val fileKey: String = s3util.upload(file, "${report.getGroup().id}/report/${reportId}")
        report.changeFile(File(
            fileKey,
            FileType.DOCS,
            file.originalFilename!!.substring(file.originalFilename!!.indexOf(".") + 1)
        ), fileIdx)
        replyRepository.save(report)
    }

    @Transactional
    override fun deleteReport(fileIdx: Int, reportId: String){
        val report: Reply = replyRepository.findByIdAndWriter(reportId, current.getUser()).orElse(null)?: throw PostCannotFoundException(reportId)
        report.deleteFile(fileIdx)
        replyRepository.save(report)
    }


}