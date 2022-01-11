package com.appsolute.soomapi.domain.soom.service

import com.appsolute.soomapi.domain.account.data.dto.response.UserResponse
import com.appsolute.soomapi.domain.account.repository.UserRepository
import com.appsolute.soomapi.domain.soom.data.entity.GeneGroupRequest
import com.appsolute.soomapi.domain.soom.data.entity.Group
import com.appsolute.soomapi.domain.soom.data.request.EditGroupRequest
import com.appsolute.soomapi.domain.soom.data.request.GenerateGroupRequest
import com.appsolute.soomapi.domain.soom.data.type.GroupType
import com.appsolute.soomapi.domain.soom.exception.GeneGroupRequestNotFoundException
import com.appsolute.soomapi.domain.soom.exception.GroupCannotFoundException
import com.appsolute.soomapi.domain.soom.exception.IsNotGroupMemberException
import com.appsolute.soomapi.domain.soom.repository.GeneGroupRequestRepository
import com.appsolute.soomapi.domain.soom.repository.GroupRepository
import com.appsolute.soomapi.global.security.CurrentUser
import java.util.*

class CrudGroupServiceImpl(
    private val geneGroupRequestRepository: GeneGroupRequestRepository,
    private val current: CurrentUser,
    private val groupRepository: GroupRepository,
    private val userRepository: UserRepository
): CrudGroupService {

    override fun geneGroupRequest(r: GenerateGroupRequest){

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

    override fun getGroupGeneRequestList(): List<GeneGroupRequest>{
        return geneGroupRequestRepository.findAllBySchool(current.getUser().school)
    }

    override fun getGroupGeneRequestListByGroupType(groupType: GroupType): List<GeneGroupRequest>{
        return geneGroupRequestRepository.findAllBySchoolAndGroupType(current.getUser().school, groupType)
    }

    override fun approveGeneGroupRequest(memberId: String, name: String){
        geneGroupRequestRepository.findById(memberId+name).map {
            geneGroup(GenerateGroupRequest(name, it.des, it.groupType))
        }.orElse(null)?: throw GeneGroupRequestNotFoundException()
    }

    override fun editGroupInfo(groupId: String, r: EditGroupRequest){
        groupRepository.findById(groupId).map{
            groupRepository.save(it.editGroup(r))
        }.orElse(null)?: throw GroupCannotFoundException()

    }

    override fun changeGroupType(groupId: String, type: GroupType){
        groupRepository.findById(groupId).map{
            groupRepository.save(it.setType(type))
        }.orElse(null)?: throw GroupCannotFoundException()
    }

    override fun setGroupProfile(){

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


    override fun deleteGroupRequest(groupId: String){
        var group = checkIsGroupMember(groupId)
        if (group.deleteVote(current.getUser()).deleteVoterList.size > (group.memberList.size/2)){
            groupRepository.delete(group)
        }else {
            groupRepository.save(group)
        }
    }

    override fun getDeleteRequestUser(groupId: String): List<UserResponse>{
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



}