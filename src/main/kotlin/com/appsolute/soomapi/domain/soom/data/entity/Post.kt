package com.appsolute.soomapi.domain.soom.data.entity

import com.appsolute.soomapi.domain.account.data.entity.user.User
import com.appsolute.soomapi.domain.soom.data.response.ReplyResponse
import com.appsolute.soomapi.domain.soom.data.type.PostType
import java.time.LocalDateTime
import javax.persistence.*


@Entity
@DiscriminatorColumn(name = "PTYPE")
abstract class Post(
    id: String,
    title: String,
    postType: PostType,
    writer: User,
    sendTo: Post?,
    group: Group
) {

    @Id
    private var id = id

    private var title = title

    private var postType = postType

    private var writeAt = LocalDateTime.now()

    @ManyToOne(fetch = FetchType.LAZY)
    private var writer: User = writer

    @ElementCollection
    private var files: MutableList<File> = ArrayList<File>()

    @OneToOne(fetch = FetchType.LAZY)
    private var sendTo: Post? = sendTo

    @ManyToOne(fetch = FetchType.LAZY)
    private var group = group

    @OneToMany(fetch = FetchType.LAZY)
    private var receiverList: MutableList<User> = ArrayList<User>()

    private var numOfLike: Int = 0

    @OneToMany(fetch = FetchType.LAZY)
    private var likedMemberList: MutableList<User> = ArrayList<User>()

    @OneToMany(fetch = FetchType.LAZY)
    private var aimingThisPostList: MutableList<Post> = ArrayList<Post>()



    fun getAimingAtThisPostList(): List<Post> {
        return this.aimingThisPostList
    }

    fun toReplyResponse(): ReplyResponse {
        return ReplyResponse(
            this.id,
            this.title,
            this.writer.toUserResponse(),
            this.sendTo?.id!!
        )
    }

    fun like(user: User): Post{
        this.numOfLike.and(1)
        this.likedMemberList.add(user)
        return this
    }

    fun getLike(): Int{
        return this.numOfLike
    }

    fun getLikedMemberList(): List<User>{
        return this.likedMemberList
    }

    fun setReceiverList(memberList: List<User>) {
        this.receiverList.addAll(memberList)
    }

    fun attachFile(file: File): Post{
        this.files.add(file)
        return this
    }

    fun getGroup(): Group{
        return this.group
    }

    fun getId(): String{
        return this.id
    }

    fun getTitle(): String{
        return this.title
    }

    fun setTitle(title: String): Post{
        this.title = title
        return this
    }

    fun getWriter(): User {
        return this.writer
    }

    fun getFileList(): List<File>{
        return this.files
    }


}