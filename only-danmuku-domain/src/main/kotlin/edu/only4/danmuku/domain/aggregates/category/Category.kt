package edu.only4.danmuku.domain.aggregates.category

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
import com.only4.cap4k.ddd.core.domain.event.DomainEventSupervisorSupport.events
import edu.only4.danmuku.domain.aggregates.category.events.CategoryCreatedDomainEvent
import edu.only4.danmuku.domain.aggregates.category.events.CategoryDeletedDomainEvent
import jakarta.persistence.*
import jakarta.persistence.Table
import org.hibernate.annotations.*

/**
 * 分类信息;
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：请勿手工修改该文件的字段声明，重新生成会覆盖字段声明
 * @author cap4k-ddd-codegen
 * @date 2025/10/21
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
     * bigint
     */
    @Column(name = "`deleted`")
    var deleted: Long = 0L,
) {

    // 【字段映射结束】本段落由[cap4k-ddd-codegen-gradle-plugin]维护，请不要手工改动

    // 【行为方法开始】

    fun onCreate() {
        events().attach(this) { CategoryCreatedDomainEvent(id) }
    }

    fun onUpdate() {
        // TODO: 触发缓存删除
//        events().attach(this) { CategoryUpdatedDomainEvent(this) }
    }

    fun onDeleted() {
        events().attach(this) { CategoryDeletedDomainEvent(this) }
    }

    fun updateBasicInfo(
        newName: String,
        newIcon: String?,
        newBackground: String?
    ) {
        this.name = newName
        this.icon = newIcon
        this.background = newBackground
    }

    fun changeParent(newParentId: Long, parentCategory: Category?): Pair<String, String> {
        val oldPath = this.nodePath
        this.parentId = newParentId

        val newParentPath = parentCategory?.nodePath ?: ""
        updateNodePath(newParentPath)

        return Pair(oldPath, this.nodePath)
    }

    fun changeCode(newCode: String) {
        // 修改编码不再影响路径（路径改为基于ID）
        this.code = newCode
    }

    fun isParentChanged(newParentId: Long): Boolean {
        return this.parentId != newParentId
    }

    fun isCodeChanged(newCode: String): Boolean {
        return this.code != newCode
    }

    fun isMovingToSelf(newParentId: Long): Boolean {
        return newParentId == this.id
    }

    fun isMovingToDescendant(parentCategory: Category): Boolean {
        return parentCategory.nodePath.startsWith(this.nodePath)
    }

    /**
     * 更新节点路径（在创建或移动节点时调用）
     * @param parentPath 父节点的路径，根节点传空字符串
     */
    fun updateNodePath(parentPath: String = "") {
        nodePath = if (parentPath.isBlank()) {
            "/$id/"  // 根节点：/1/
        } else {
            "$parentPath$id/"  // 子节点：/1/101/
        }
    }

    /**
     * 增加排序号
     * @param increment 增加的值
     */
    fun addSort(increment: Int) {
        sort = (sort + increment).toByte()
    }

    /**
     * 是否为根节点
     */
    fun isRoot(): Boolean = parentId == 0L

    /**
     * 获取层级深度（1=根节点, 2=一级子节点...）
     */
    fun getLevel(): Int = nodePath.trim('/').split('/').size

    /**
     * 获取所有祖先 ID（从根到父节点）
     */
    fun getAncestorIds(): List<Long> {
        return nodePath.trim('/').split('/').dropLast(1).mapNotNull { it.toLongOrNull() }
    }

    /**
     * 检查是否为指定节点的后代
     */
    fun isDescendantOf(ancestorId: Long): Boolean {
        return nodePath.contains("/$ancestorId/")
    }

    /**
     * 是否为指定父分类的直接子级
     */
    fun isDirectChildOf(parentId: Long): Boolean {
        return this.parentId == parentId
    }

    /**
     * 依据父路径变更重定位当前节点路径
     * @param oldPrefix 旧父路径前缀，例如 "/1/101/"
     * @param newPrefix 新父路径前缀，例如 "/1/102/"
     */
    fun rebaseNodePath(oldPrefix: String, newPrefix: String) {
        if (nodePath.startsWith(oldPrefix)) {
            nodePath = nodePath.replaceFirst(oldPrefix, newPrefix)
        }
    }

    // 【行为方法结束】
}
