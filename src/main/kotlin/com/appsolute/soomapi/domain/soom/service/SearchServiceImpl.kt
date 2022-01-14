package com.appsolute.soomapi.domain.soom.service

import com.appsolute.soomapi.domain.soom.data.entity.Group
import com.appsolute.soomapi.domain.soom.data.response.GroupResponse
import com.appsolute.soomapi.domain.soom.exception.GroupCannotFoundException
import com.appsolute.soomapi.domain.soom.repository.group.GroupRepository
import org.springframework.data.domain.PageRequest

class SearchServiceImpl(
    private val groupRepository: GroupRepository
): SearchService {

    override fun searchGroupByTitle(query: String): List<GroupResponse>{

        return groupRepository.findAllByNameContaining(query)?.let{
            toGroupResponse(it)
        }?: throw GroupCannotFoundException()
    }

    override fun searchGroupById(id: String): GroupResponse {
        return groupRepository.findById(id).map {
            it.toGroupResponse()
        }.orElse(null)?: throw GroupCannotFoundException()

    }

    override fun getGroupList(idx: Int, size: Int): List<GroupResponse> {
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

}