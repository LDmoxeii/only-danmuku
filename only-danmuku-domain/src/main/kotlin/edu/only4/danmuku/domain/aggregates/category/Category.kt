package edu.only4.danmuku.domain.aggregates.category

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
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
     * tinyint(1)
     */
    @Column(name = "`deleted`")
    var deleted: Boolean = false,
) {

    // 【字段映射结束】本段落由[cap4k-ddd-codegen-gradle-plugin]维护，请不要手工改动

    // 【行为方法开始】

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

    fun changeCode(newCode: String): Pair<String, String> {
        val oldPath = this.nodePath
        this.code = newCode

        val parentPath = if (parentId == 0L) {
            ""
        } else {
            val parts = oldPath.trim('/').split('/')
            if (parts.size > 1) {
                "/" + parts.dropLast(1).joinToString("/") + "/"
            } else {
                ""
            }
        }
        updateNodePath(parentPath)

        return Pair(oldPath, this.nodePath)
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
        nodePath = if (parentPath.isEmpty()) {
            "/$code/"  // 根节点：/1/
        } else {
            "$parentPath$code/"  // 子节点：/1/101/
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

    // 【行为方法结束】
}
