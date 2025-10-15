package edu.only4.danmuku.domain.aggregates.category

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate

import jakarta.persistence.*

import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where

/**
 * 分类信息;
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：请勿手工修改该文件的字段声明，重新生成会覆盖字段声明
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
@Aggregate(aggregate = "Category", name = "Category", root = true, type = Aggregate.TYPE_ENTITY, description = "分类信息，")
@Entity
@Table(name = "`category`")
@DynamicInsert
@DynamicUpdate
@SQLDelete(sql = "update `category` set `deleted` = `id` where `id` = ?")
@Where(clause = "`deleted` = 0")
class Category (
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
     * 父级ID
     * bigint
     */
    @Column(name = "`parent_id`")
    var parentId: Long = 0L,

    /**
     * 路径
     * varchar(255)
     */
    @Column(name = "`node_path`")
    var nodePath: String = "",

    /**
     * 排序号
     * tinyint
     */
    @Column(name = "`sort`")
    var sort: Byte = 0,

    /**
     * 编码
     * varchar(30)
     */
    @Column(name = "`code`")
    var code: String = "",

    /**
     * 名称
     * varchar(30)
     */
    @Column(name = "`name`")
    var name: String = "",

    /**
     * 图标
     * varchar(50)
     */
    @Column(name = "`icon`")
    var icon: String? = null,

    /**
     * 背景图
     * varchar(50)
     */
    @Column(name = "`background`")
    var background: String? = null,

    /**
     * 创建人ID
     * bigint
     */
    @Column(name = "`create_user_id`")
    var createUserId: Long? = null,

    /**
     * 创建人名称
     * varchar(32)
     */
    @Column(name = "`create_by`")
    var createBy: String? = null,

    /**
     * 创建时间
     * bigint
     */
    @Column(name = "`create_time`")
    var createTime: Long? = null,

    /**
     * 更新人ID
     * bigint
     */
    @Column(name = "`update_user_id`")
    var updateUserId: Long? = null,

    /**
     * 更新人名称
     * varchar(32)
     */
    @Column(name = "`update_by`")
    var updateBy: String? = null,

    /**
     * 更新时间
     * bigint
     */
    @Column(name = "`update_time`")
    var updateTime: Long? = null,

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
