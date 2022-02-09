package com.appsolute.soomapi.domain.soom.service

import com.appsolute.soomapi.domain.soom.data.entity.Soom
import com.appsolute.soomapi.domain.soom.data.response.GroupResponse
import com.appsolute.soomapi.domain.soom.exception.GroupCannotFoundException
import com.appsolute.soomapi.domain.soom.repository.group.GroupRepository
import com.appsolute.soomapi.global.security.CurrentUser
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service


@Service
class SearchServiceImpl(
    private val groupRepository: GroupRepository,
    private val current: CurrentUser
): SearchService {

    override fun searchGroupByTitle(query: String): List<GroupResponse>{

        return groupRepository.findAllByNameContaining(query)?.let{
            toGroupResponse(it)
        }?: throw GroupCannotFoundException(query)
    }

    override fun searchGroupById(id: String): GroupResponse {
        return groupRepository.findById(id).map {
            it.toGroupResponse(current.getUser())
        }.orElse(null)?: throw GroupCannotFoundException(id)

    }

    override fun getGroupList(idx: Int, size: Int): List<GroupResponse> {
        return groupRepository.findAll(PageRequest.of(idx, size)).let {
            toGroupResponse(it.toList())
        }

    }

    private fun toGroupResponse(soom: List<Soom>): List<GroupResponse> {
        return soom.stream().map {
                it -> GroupResponse(
            it.id,
            it.name,
            it.description,
            it.type,
            it.header,
            it.subHeaderList.stream().map { it.toUserResponse() }.toList(),
            it.profile,
            it.profileBanner,
            it.memberList.size,
            it.memberList.stream().map { it.toUserResponse() }.toList(),
            it.teacher?.toTeacherResponse(),
            it.memberList.contains(current.getUser()),
            it.joinRequestMemberList.contains(current.getUser())
        )
        }.toList()
    }

}