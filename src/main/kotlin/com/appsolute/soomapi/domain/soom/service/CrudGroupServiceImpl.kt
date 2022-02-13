package com.appsolute.soomapi.domain.soom.service

import com.appsolute.soomapi.domain.account.data.dto.response.UserResponse
import com.appsolute.soomapi.domain.account.repository.UserRepository
import com.appsolute.soomapi.domain.chat.service.ChatService
import com.appsolute.soomapi.domain.soom.data.request.type.ChangeProfileType
import com.appsolute.soomapi.domain.soom.data.entity.GeneGroupRequest
import com.appsolute.soomapi.domain.soom.data.entity.Soom
import com.appsolute.soomapi.domain.soom.data.request.EditGroupRequest
import com.appsolute.soomapi.domain.soom.data.request.GenerateGroupRequest
import com.appsolute.soomapi.domain.soom.data.type.GroupType
import com.appsolute.soomapi.domain.soom.exception.GeneGroupRequestNotFoundException
import com.appsolute.soomapi.domain.soom.exception.GroupCannotFoundException
import com.appsolute.soomapi.domain.soom.exception.IsNotGroupMemberException
import com.appsolute.soomapi.domain.soom.repository.group.GeneGroupRequestRepository
import com.appsolute.soomapi.domain.soom.repository.group.GroupRepository
import com.appsolute.soomapi.domain.soom.util.CheckGroupUtil
import com.appsolute.soomapi.global.security.CurrentUser
import com.appsolute.soomapi.infra.service.fcm.FcmService
import com.appsolute.soomapi.infra.service.s3.S3Util
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.util.*
import javax.transaction.Transactional


@Service
class CrudGroupServiceImpl(
    private val geneGroupRequestRepository: GeneGroupRequestRepository,
    private val current: CurrentUser,
    private val groupRepository: GroupRepository,
    private val s3Util: S3Util,
    private val check: CheckGroupUtil,
    private val fcmService: FcmService,
    private val chatService: ChatService
): CrudGroupService {

    @Transactional
    override fun geneGroupRequest(r: GenerateGroupRequest){

        if (r.type == GroupType.TEAM) {
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

    @Transactional
    override fun getGroupGeneRequestListByGroupType(groupType: GroupType?): List<GeneGroupRequest>{
        groupType?.let{
            return geneGroupRequestRepository.findAllBySchoolAndGroupType(current.getUser().school, groupType)
        } ?: return geneGroupRequestRepository.findAllBySchool(current.getUser().school)

    }

    @Transactional
    override fun approveGeneGroupRequest(memberId: String, name: String){
        geneGroupRequestRepository.findById(memberId+name).map {
            geneGroup(GenerateGroupRequest(name, it.des, it.groupType))
            geneGroupRequestRepository.delete(it)
        }.orElse(null)?: throw GeneGroupRequestNotFoundException(name)
    }

    @Transactional
    override fun editGroupInfo(groupId: String, description: String){
        val dto = check.checkIsGroupHeader(groupId)
        dto.soom.description = description
    }

    @Transactional
    override fun changeGroupType(groupId: String, type: GroupType) {
        current.getTeacher()?.let {
            (groupRepository.findById(groupId).orElse(null)?: throw GroupCannotFoundException(groupId))
                .type = type
        } ?: let {
            (check.checkIsGroupHeader(groupId).soom)
                .type = type
        }
    }

    @Transactional
    override fun setGroupProfile(profile: MultipartFile, groupId: String, type: ChangeProfileType){
        groupRepository.findByIdAndHeader(groupId, current.getUser()).orElse(null)?.let {
            if (type == ChangeProfileType.BANNER) it.settingProfile(s3Util.upload(profile, "${it.id}/profile"))
            else it.settingBannerProfile(s3Util.upload(profile, "${it.id}/banner"))
        }

    }

    private fun geneGroup(r: GenerateGroupRequest){
        var id: String = UUID.randomUUID().toString()
        groupRepository.findById(id).ifPresent { id = UUID.randomUUID().toString() }

        groupRepository.save(
            Soom(
                id,
                r.name,
                r.description,
                r.type,
                current.getUser()
            )
        )
    }

    @Transactional
    override fun deleteGroupRequest(groupId: String){
        val group = check.checkIsGroupMember(groupId).soom
        val user = current.getUser()
        user.deleteGroupRequest(group)

        if (group.type.equals(GroupType.TEAM) && group.deleteVoterList.size > (group.memberList.size/2)){
            fcmService.sendChatRoomAlarm(
                group.chattingRoom,
                null,
                "[GROUP] ${group.name} 삭제됨.",
                "[GROUP] ${group.name} 삭제 투표가 과반수를 넘어 삭제되었습니다."
            )
            chatService.deleteChatRoom(group.chattingRoom)
            groupRepository.delete(group)
        } else if (group.deleteVoterList.size.equals(group.memberList.size)) {
            fcmService.sendChatRoomAlarm(
                group.chattingRoom,
                null,
                "[GROUP] ${group.name} 삭제됨.",
                "[GROUP] ${group.name} 삭제 투표에서 전원이 찬성하여 삭제되었습니다."
            )
            groupRepository.delete(group)
        } else {
            groupRepository.save(group)
        }
    }

    @Transactional
    override fun cancelDeleteGroupRequest(groupId: String) {
        val group = check.checkIsGroupMember(groupId).soom
        val user = current.getUser()
        user.cancelDeleteGroupRequest(group)
    }


}