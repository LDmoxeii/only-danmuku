package edu.only4.danmuku.application.queries._share.model

import org.babyfish.jimmer.sql.*

/**
 * Jimmer 分类实体
 * 用于 CQRS 读模型查询
 */
@Table(name = "category")
@Entity
interface JCategory {

    /**
     * 分类ID
     */
    @Id
    val id: Long

    /**
     * 路径
     */
    val nodePath: String

    /**
     * 排序号
     */
    val sort: Byte

    /**
     * 编码
     */
    val code: String

    /**
     * 名称
     */
    val name: String

    /**
     * 图标
     */
    val icon: String?

    /**
     * 背景图
     */
    val background: String?

    /**
     * 逻辑删除标识
     */
    @LogicalDeleted("0")
    val deleted: Boolean

    /**
     * 子分类列表（自关联）
     * 注意：这是递归关联，用于构建树形结构
     */
    @OneToMany(mappedBy = "parent")
    val children: List<JCategory>

    /**
     * 父分类（自关联）
     */
    @ManyToOne
    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    val parent: JCategory?

    /**
     * 父级ID（IdView方式访问父分类ID）
     */
    @IdView
    val parentId: Long?
}
