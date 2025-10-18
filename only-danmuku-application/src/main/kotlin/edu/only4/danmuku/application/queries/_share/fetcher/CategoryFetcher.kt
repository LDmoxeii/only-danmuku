package edu.only4.danmuku.application.queries._share.fetcher

import edu.only4.danmuku.application.queries._share.model.JCategory
import edu.only4.danmuku.application.queries._share.model.by
import org.babyfish.jimmer.sql.kt.fetcher.newFetcher

/**
 * 分类查询 Fetcher 预定义
 * 用于优化查询性能，避免 N+1 问题
 */
object CategoryFetcher {

    /**
     * 基础分类信息（不包含子分类）
     */
    val BASIC = newFetcher(JCategory::class).by {
        allScalarFields()
    }

    /**
     * 分类树形结构（递归加载所有子分类）
     * 注意：这会递归加载整个分类树，适用于分类数量不多的场景
     */
    val TREE = newFetcher(JCategory::class).by {
        allScalarFields()
        // 递归加载子分类（* 表示递归，自动使用相同的字段定义）
        `children*`()
    }

    /**
     * 单层分类树（只加载一级子分类）
     */
    val WITH_CHILDREN = newFetcher(JCategory::class).by {
        allScalarFields()
        children {
            allScalarFields()
        }
    }
}
