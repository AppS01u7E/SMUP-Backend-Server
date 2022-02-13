package com.appsolute.soomapi.domain.soom.exception

import com.appsolute.soomapi.global.error.data.type.ErrorCode
import com.appsolute.soomapi.global.error.exception.GlobalException

class GeneGroupRequestBannedUserException(userId: String): GlobalException(ErrorCode.GENE_GROUP_REQUEST_BANNED, userId){

}
