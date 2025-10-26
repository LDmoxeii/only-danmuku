package edu.only4.danmuku.application.queries._share.model

import org.babyfish.jimmer.sql.Column
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.ForeignKeyType
import org.babyfish.jimmer.sql.JoinColumn
import org.babyfish.jimmer.sql.ManyToOne
import org.babyfish.jimmer.sql.OneToMany
import org.babyfish.jimmer.sql.Table

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

    @Column(name = "node_path")
    val nodePath: String

    @Column(name = "sort")
    val sort: Int

    @Column(name = "code")
    val code: String

    @Column(name = "name")
    val name: String

    @Column(name = "icon")
    val icon: String?

    @Column(name = "background")
    val background: String?
}
