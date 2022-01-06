package com.appsolulte.smupbackendserver.domain.soom.service

import com.appsolulte.smupbackendserver.domain.soom.dto.response.ReplyResponse
import com.appsolulte.smupbackendserver.domain.account.dto.response.UserResponse
import com.appsolulte.smupbackendserver.domain.account.entity.GroupInfo
import com.appsolulte.smupbackendserver.domain.account.entity.Role
import com.appsolulte.smupbackendserver.domain.account.exception.UserNotFoundException
import com.appsolulte.smupbackendserver.domain.account.repository.StudentRepository
import com.appsolulte.smupbackendserver.domain.account.repository.UserRepository
import com.appsolulte.smupbackendserver.domain.soom.dto.request.EditGroupRequest
import com.appsolulte.smupbackendserver.domain.soom.dto.request.GenerateGroupRequest
import com.appsolulte.smupbackendserver.domain.soom.dto.request.PostNoticeRequest
import com.appsolulte.smupbackendserver.domain.soom.dto.request.WriteReplyRequest
import com.appsolulte.smupbackendserver.domain.soom.dto.response.GroupAuthType
import com.appsolulte.smupbackendserver.domain.soom.dto.response.GroupResponse
import com.appsolulte.smupbackendserver.domain.soom.dto.response.GroupUserResponse
import com.appsolulte.smupbackendserver.domain.soom.dto.response.NoticeResponse
import com.appsolulte.smupbackendserver.domain.soom.entity.*
import com.appsolulte.smupbackendserver.domain.soom.exception.*
import com.appsolulte.smupbackendserver.domain.soom.repository.*
import com.appsolulte.smupbackendserver.global.facade.CurrentUser
import org.springframework.data.domain.PageRequest
import java.util.*


class SoomService(
    private val groupRepository: GroupRepository,
    private val current: CurrentUser,
    private val geneGroupRequestRepository: GeneGroupRequestRepository,
    private val studentRepository: StudentRepository,
    private val rejectedUserRepository: RejectedUserRepository,
    private val userRepository: UserRepository,
    private val groupInfoRepository: GroupInfoRepository,
    private val postRepository: PostRepository,
    private val noticeRepository: NoticeRepository,
    private val replyRepository: ReplyRepository

) {


    fun searchGroupByTitle(query: String): List<GroupResponse>{

        return groupRepository.findAllByNameContaining(query)?.let{
            toGroupResponse(it)
        }?: throw GroupCannotFoundException()
    }

    fun searchGroupById(id: String): GroupResponse{
        return groupRepository.findById(id).map {
            it.toGroupResponse()
        }.orElse(null)?: throw GroupCannotFoundException()

    }

    fun getGroupList(idx: Int, size: Int): List<GroupResponse> {
        return groupRepository.findAll(PageRequest.of(idx, size)).let {
            toGroupResponse(it.toList())
        }

    }

    private fun toGroupResponse(group: List<Group>): List<GroupResponse> {
        return group.stream().map {
            it -> GroupResponse(
                it.id,
                it.name,
                it.description,
                it.type,
                it.header,
                it.subHeaderList.stream().map { it.toUserResponse() }.toList(),
                it.profile,
                it.memberList.size,
                it.memberList.stream().map { it.toUserResponse() }.toList(),
                it.teacher?.toTeacherResponse()
            )
        }.toList()
    }




    fun geneGroupRequest(r: GenerateGroupRequest){

        if (r.type.equals(GroupType.TEAM)) {
            geneGroup(r)
        } else {
            geneGroupRequestRepository.save(
                GeneGroupRequest(
                    r.name,
                    current.getPk(),
                    r.type,
                    current.getUser().school,
                    r.description
                )
            )
        }
    }

    fun getGroupGeneRequestList(): List<GeneGroupRequest>{
        return geneGroupRequestRepository.findAllBySchool(current.getUser().school)
    }

    fun getGroupGeneRequestListByGroupType(groupType: GroupType): List<GeneGroupRequest>{
        return geneGroupRequestRepository.findAllBySchoolAndGroupType(current.getUser().school, groupType)
    }

    fun approveGeneGroupRequest(memberId: String, name: String){
        geneGroupRequestRepository.findById(memberId+name).map {
            geneGroup(GenerateGroupRequest(name, it.des, it.groupType))
        }.orElse(null)?: throw GeneGroupRequestNotFoundException()
    }

    fun editGroupInfo(groupId: String, r: EditGroupRequest){
        groupRepository.findById(groupId).map{
            groupRepository.save(it.editGroup(r))
        }.orElse(null)?: throw GroupCannotFoundException()

    }

    fun changeGroupType(groupId: String, type: GroupType){
        groupRepository.findById(groupId).map{
            groupRepository.save(it.setType(type))
        }.orElse(null)?: throw GroupCannotFoundException()
    }

    fun setGroupProfile(){

    }

    private fun geneGroup(r: GenerateGroupRequest){
        var id: String = UUID.randomUUID().toString()
        groupRepository.findById(id).ifPresent { id = UUID.randomUUID().toString() }

        groupRepository.save(
            Group(
            id,
            r.name,
            r.description,
            r.type,
            current.getUser()
            )
        )
    }




    fun deleteGroupRequest(groupId: String){
        var group = checkIsGroupMember(groupId)
        if (group.deleteVote(current.getUser()).deleteVoterList.size > (group.memberList.size/2)){
            groupRepository.delete(group)
        }else {
            groupRepository.save(group)
        }
    }

    fun getDeleteRequestUser(groupId: String): List<UserResponse>{
        return checkIsGroupMember(groupId).deleteVoterList.stream().map {
            it.toUserResponse()
        }.toList()
    }

    private fun checkIsGroupMember(groupId: String): Group{
        return groupRepository.findById(groupId).map {
            if (!it.memberList.contains(current.getUser())) {
                throw IsNotGroupMemberException()
            } else it
        }.orElse(null)?: throw GroupCannotFoundException()
    }

    private fun checkIsGroupMember(groupId: String, userId: String): Group{
        return groupRepository.findById(groupId).map {
            if (!it.memberList.contains(userRepository.findById(userId).orElse(null)
                    ?: throw UserNotFoundException())) {
                throw IsNotGroupMemberException()
            } else it
        }.orElse(null)?: throw GroupCannotFoundException()
    }




    fun getJoinRequest(groupId: String): List<UserResponse>{
        return checkIsGroupMember(groupId).memberList.stream().map {
            it.toUserResponse()
        }.toList()
    }

    fun isJoinRequestSent(groupId: String): Boolean{
        return groupRepository.findById(groupId).map {
            it.memberList.contains(current.getUser())
        }.orElse(null)?: return false

    }

    fun sendJoinRequest(groupId: String){
        val group = checkIsNotGroupMember(groupId)
        if (current.getUser().getRole().equals(Role.TEACHER)) throw TeacherAlreadyExistsException()
        else if (rejectedUserRepository.findById(groupId + current.getUser().id).isPresent) throw RejectedUserException()
        else {
            group.addJoinRequestMemberList(current.getStudent())
        }
        groupRepository.save(group)
    }

    fun receiveJoinRequest(groupId: String, receiverId: String){
        val accepted = studentRepository.findById(receiverId).map{
            it
        }.orElse(null)?: throw UserNotFoundException()

        (groupRepository.findById(groupId).map{
            {
                if (!it.joinRequestMemberList.contains(accepted)) throw HasNotJoinRequestException()
                else {
                    it.approveJoinRequest(accepted)
                    groupRepository.save(it)
                    userRepository.save(accepted)
                }
            }
        }.orElse(null)?: throw GroupCannotFoundException())

    }

    fun receiveEveryJoinRequest(groupId: String, receiverIds: List<String>){
        receiverIds.forEach { receiveJoinRequest(groupId, it) }
    }

    fun rejectJoinRequest(groupId: String, studentId: String){
        rejectedUserRepository.save(
            RejectedUser(
                groupId,
                studentId
            )
        )
    }

    fun rejectAllJoinRequest(groupId: String, studentIds: List<String>){
        studentIds.forEach {
            rejectJoinRequest(groupId, it)
        }
    }

    private fun checkIsNotGroupMember(groupId: String): Group{
        return groupRepository.findById(groupId).map{
            if (it.memberList.contains(current.getUser())){
                throw IsAlreadyGroupMmeberException()
            }
            else it
        }.orElse(null)?: throw GroupCannotFoundException()
    }




    fun getGroupMemberList(groupId: String): List<UserResponse>{
        return groupRepository.findById(groupId).map {
            it.memberList.stream().map {
                it.toUserResponse()
            }.toList()
        }.orElse(null)?: throw GroupCannotFoundException()
    }

    fun getGroupMember(groupId: String, userId: String): GroupUserResponse{
        var group = groupRepository.findById(groupId).map {
            it
        }.orElse(null)?: throw GroupCannotFoundException()

        var user = userRepository.findById(userId).orElse(null)?: throw UserNotFoundException()
        var groupInfo = groupInfoRepository.findByGroupAndUser(group, user).orElse(null)?: throw GroupCannotFoundException()

        return GroupUserResponse(
            user.toUserResponse(),
            groupInfo.joinedAt,
            postRepository.findAllByGroupAndPostType(group, PostType.NOTICE, PageRequest.of(1, 10)).toList(),
            postRepository.findAllByGroupAndPostType(group, PostType.REPLY, PageRequest.of(1, 10)).toList(),
            groupInfo.auth
        )
    }

    fun transferAuthority(groupId: String, userId: String){
        var user = userRepository.findById(userId)
            .orElse(null)?: throw UserNotFoundException()
        var group = checkIsGroupHeader(groupId)

        var targetGroupInfo = groupInfoRepository.findByGroupAndUser(group, user)
            .orElse(null)?: throw GroupCannotFoundException()
        var actorGroupInfo = groupInfoRepository.findByGroupAndUser(group, current.getUser())
            .orElse(null)?: throw GroupCannotFoundException()

        groupInfoRepository.save(targetGroupInfo.changeAuth(actorGroupInfo.auth))
        groupInfoRepository.save(actorGroupInfo.removeAuth())
    }


    fun kickGroupMember(groupId: String, userId: String){
        var member = userRepository.findById(userId).orElse(null)?: throw UserNotFoundException()
        var group = checkIsGroupMember(groupId, userId)
        groupRepository.save(
            group.kickMember(
                member,
                groupInfoRepository.findByGroupAndUser(group, member).orElse(null)
                    ?: throw GroupCannotFoundException()
            )
        )

    }

    private fun checkIsGroupHeader(groupId: String): Group{
        return groupRepository.findById(groupId).map {
            if (!it.header.equals(current.getUser())) {
                throw LowerAuthException()
            } else it
        }.orElse(null)?: throw GroupCannotFoundException()
    }





    fun getNoticeList(idx: Int, size: Int): List<NoticeResponse>{
        return postRepository.findAllByReceiverListContainsAndPostType(
            current.getUser(),
            PageRequest.of(idx, size),
            PostType.NOTICE
            ).stream().map {
                it.toNoticeResponse()
        }.toList()
    }

    fun getNoticeWithId(groupId: String, postId: String): NoticeResponse{
        var group = groupRepository.findById(groupId).orElse(null)?: throw GroupCannotFoundException()
        var notice = noticeRepository.findById(postId).orElse(null)?: throw PostCannotFoundException()
        if (group.postList.contains(notice)) throw PostCannotFoundException()
        return (notice).toNoticeResponse()
    }

    fun getGroupNoticeList(groupId: String, idx: Int, size: Int): List<NoticeResponse>{
        return noticeRepository.findAll(PageRequest.of(idx, size)).stream().map {
            it.toNoticeResponse()
        }.toList()
    }

    fun postNotice(groupId: String, noticeRequest: PostNoticeRequest){
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

    fun patchNotice(noticeId: String, noticeRequest: PostNoticeRequest){
        noticeRepository.save(
            noticeRepository.findById(noticeId).map {
                it.editNotice(noticeRequest)
            }.orElse(null)?: throw PostCannotFoundException()
        )
    }

    fun deleteNotice(noticeId: String){
        val notice = noticeRepository.findById(noticeId).orElse(null)?: throw PostCannotFoundException()
        if (notice.getWriter().equals(current.getUser())) noticeRepository.delete(notice)
    }

    fun attachFileWithNotice(){

    }

    fun patchNoticeFile(){

    }

    fun likeToNotice(postId: String){
        postRepository.save(
        postRepository.findById(postId).map {
                it.like(current.getUser())
            }.orElse(null)?: throw PostCannotFoundException()
        )
    }




    fun getReplyWithId(replyId: String): ReplyResponse {
        return (postRepository.findById(replyId).orElse(null)
            ?: throw PostCannotFoundException())
            .toReplyResponse()
    }

    fun getRepliesWithNotice(noticeId: String): List<ReplyResponse>{
        return noticeRepository.findById(noticeId).map {
            it.getAimingAtThisPostList().stream().map {
                it.toReplyResponse()
            }.toList()
        }.orElse(null)?: throw PostCannotFoundException()
    }

    fun writeReply(noticeId: String, writeReplyRequest: WriteReplyRequest){
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

    fun editReply(replyId: String, content: String){
        replyRepository.findByIdAndWriter(replyId, current.getUser()).map {
            it.setTitle(content)
        }.orElse(null)?: throw PostCannotFoundException()
    }

    fun deleteReply(replyId: String){
        replyRepository.findByIdAndWriter(replyId, current.getUser()).map {
            replyRepository.delete(it)
        }.orElse(null)?: throw PostCannotFoundException()
    }




    fun getReportListWithNotice(){

    }

    fun getReportListWithMember(){

    }

    fun submitReportToNotice(){

    }

    fun editReport(){

    }

    fun deleteReport(){

    }




    fun setSubHeader(groupId: String, memberId: String) {
        var member = userRepository.findById(memberId).orElse(null) ?: throw UserNotFoundException()
        var group = groupRepository.findByIdAndMemberListContains(groupId, member).orElse(null)
            ?: throw GroupCannotFoundException()
        if (!group.header.equals(current.getUser())) group.setSubHeader(member)

        groupRepository.save(group)
    }

    fun removeSubHeader(groupId: String, memberId: String){
        var member = userRepository.findById(memberId).orElse(null) ?: throw UserNotFoundException()
        var group = groupRepository.findByIdAndMemberListContains(groupId, member).orElse(null)
            ?: throw GroupCannotFoundException()
        if (!group.header.equals(current.getUser())) group.dismissalSubHeader(member)

        groupRepository.save(group)
    }


}