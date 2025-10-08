package com.edu.only4.danmuku.domain.aggregates.user

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate

import jakarta.persistence.*
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where

/**
 * 帐号;
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：请勿手工修改该文件的字段声明，重新生成会覆盖字段声明
 * @author cap4k-ddd-codegen
 * @date 2025/10/08
 */
@Aggregate(aggregate = "User", name = "User", root = true, type = Aggregate.TYPE_ENTITY, description = "帐号;")
@Entity
@Table(name = "`user`")
@DynamicInsert
@DynamicUpdate
@SQLDelete(sql = "update `user` set `deleted` = `id` where `id` = ?")
@Where(clause = "`deleted` = 0")
class User (
    // 【字段映射开始】本段落由[cap4k-ddd-codegen-gradle-plugin]维护，请不要手工改动

    /**
     * ID
     * bigint
     */
    @Id
    @GeneratedValue(generator = "com.only4.cap4k.ddd.domain.distributed.SnowflakeIdentifierGenerator")
    @GenericGenerator(name = "com.only4.cap4k.ddd.domain.distributed.SnowflakeIdentifierGenerator", strategy = "com.only4.cap4k.ddd.domain.distributed.SnowflakeIdentifierGenerator")
    @Column(name = "`id`", insertable = false, updatable = false)
    var id: Long = 0L,

    /**
     * 帐号类型
     * 0:UNKNOW:未知类型
     * 1:SYS_USER:系统管理员
     * tinyint(1)
     */
    @Convert(converter = com.edu.only4.danmuku.domain.aggregates.user.enums.UserType.Converter::class)
    @Column(name = "`type`")
    var type: com.edu.only4.danmuku.domain.aggregates.user.enums.UserType = com.edu.only4.danmuku.domain.aggregates.user.enums.UserType.valueOf(0),

    /**
     * 邮箱
     * varchar(150)
     */
    @Column(name = "`email`")
    var email: String = "",

    /**
     * 密码
     * varchar(50)
     */
    @Column(name = "`password`")
    var password: String = "",

    /**
     * 加入时间
     * bigint
     */
    @Column(name = "`join_time`")
    var joinTime: Long = 0L,

    /**
     * 最后登录时间
     * bigint
     */
    @Column(name = "`last_login_time`")
    var lastLoginTime: Long? = null,

    /**
     * 最后登录IP
     * varchar(15)
     */
    @Column(name = "`last_login_ip`")
    var lastLoginIp: String? = null,

    /**
     * 0:禁用 1:正常
     * tinyint(1)
     */
    @Column(name = "`status`")
    var status: Boolean = true,

    /**
     * 关联ID; 用户、管理员 = 0
     * int
     */
    @Column(name = "`related_id`")
    var relatedId: Int? = null,

    /**
     * 删除标识 0：未删除 id：已删除
     * tinyint(1)
     */
    @Column(name = "`deleted`")
    var deleted: Boolean = false,
) {

    // 【字段映射结束】本段落由[cap4k-ddd-codegen-gradle-plugin]维护，请不要手工改动

    // 【行为方法开始】



    // 【行为方法结束】

}
