package com.appsolute.soomapi.global.error.data.type

import org.springframework.http.HttpStatus

enum class ErrorCode(
    val message: String,
    val status: HttpStatus
) {
    USER_NOT_FOUND("해당 user를 조회하지 못하였습니다.", HttpStatus.NOT_FOUND),
    DEVICE_TOKEN_NOT_FOUND("user의 deviceToken을 찾지 못했습니다.", HttpStatus.NOT_FOUND),
    EXPIRED_TOKEN("만료된 토큰입니다를", HttpStatus.BAD_REQUEST),
    INVALID_TOKEN("유효하지 않은 토큰입니다.", HttpStatus.BAD_REQUEST),
    GROUP_NOT_FOUND("해당하는 숨을 조회하지 못하였습니다.", HttpStatus.NOT_FOUND),
    GENE_GROUP_REQUEST_NOT_FOUND("숨 생성 요청을 찾지 못했습니다.", HttpStatus.NOT_FOUND),
    IS_NOT_GROUP_MEMBER("해당 해당 숨의 멤버가 아닙니다.", HttpStatus.BAD_REQUEST),
    ALREADY_GROUP_MEMBER("이미 가입된 숨입니다.", HttpStatus.BAD_REQUEST),
    HAS_NOT_JOIN_REQUEST("가입 요청을 한 적이 없습니다.", HttpStatus.BAD_REQUEST),
    REJECTED_USER_EXCEPTION("가입 거절 후 10일 지나지 않은 사용자입니다.", HttpStatus.BAD_REQUEST),
    TEACHER_ALREADY_EXISTS("담당 교사가 이미 존재합니다.", HttpStatus.BAD_REQUEST),
    LOW_AUTH("작업을 수행하기에 너무 낮은 권한입니다.", HttpStatus.FORBIDDEN),
    POST_CANNOT_FOUND("게시물을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    SERIOUS_SECURITY_THREAT_EXISTS("보안 위협이 감지되었습니다.", HttpStatus.BAD_REQUEST),
    FILE_CONVERT_ERROR("파일 변환 작업 중 오류가 발생했습니다.", HttpStatus.BAD_REQUEST),
    INCORRECT_PASSWORD("잘못된 비밀번호입니다.", HttpStatus.BAD_REQUEST),
    ALREADY_USED_EMAIL("해당 email로 가입된 계정이 존재합니다.", HttpStatus.BAD_REQUEST),
    INVALID_TEACHER_CODE("유효하지 않은 코드입니다.", HttpStatus.BAD_REQUEST),
    CHAT_ROOM_NOT_FOUND("채팅방을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    ALARM_NOT_FOUND("해당 알림을 찾지 못하였습니다.", HttpStatus.NOT_FOUND),
    MESSAGE_NOT_FOUND("해당 메시지를 찾지 못하였습니다.", HttpStatus.NOT_FOUND),
    ALREADY_ACCEPTED_REQUEST("이미 처리된 요청입니다.", HttpStatus.BAD_REQUEST)



}