package com.appsolulte.smupbackendserver.domain.soom.exception

import com.appsolulte.smupbackendserver.global.exception.base.ErrorCode
import com.appsolulte.smupbackendserver.global.exception.base.GlobalException

class GeneGroupRequestNotFoundException: GlobalException(ErrorCode.GENE_GROUP_REQUEST_NOT_FOUND) {
}