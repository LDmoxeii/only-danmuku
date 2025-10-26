package edu.only4.danmuku.application.queries._share.model

import org.babyfish.jimmer.sql.Column
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.ForeignKeyType
import org.babyfish.jimmer.sql.JoinColumn
import org.babyfish.jimmer.sql.ManyToOne
import org.babyfish.jimmer.sql.OneToMany
import org.babyfish.jimmer.sql.Table

/**
 * 分类信息;@Spe;@Fac;
 */
@Entity
@Table(name = "category")
interface Category : BaseEntity {

    @ManyToOne
    @JoinColumn(
        name = "parent_id",
        foreignKeyType = ForeignKeyType.FAKE
    )
    val parent: Category?

    @OneToMany(mappedBy = "parent")
    val children: List<Category>

    /**
     * 路径
     */
    @Column(name = "node_path")
    val nodePath: String

    /**
     * 排序号
     */
    @Column(name = "sort")
    val sort: Int

    /**
     * 编码
     */
    @Column(name = "code")
    val code: String

    /**
     * 名称
     */
    @Column(name = "name")
    val name: String

    /**
     * 图标
     */
    @Column(name = "icon")
    val icon: String?

    /**
     * 背景图
     */
    @Column(name = "background")
    val background: String?
}
