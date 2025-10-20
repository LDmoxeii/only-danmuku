package edu.only4.danmuku.edu.only4.danmuku.extra

import com.only.engine.redis.misc.RedisUtils
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

/**
 * Redis 数据清理工具
 * 用于清理使用旧配置（带 DefaultTyping）保存的数据
 */
@SpringBootTest
class RedisCleanupTest {

    @Test
    fun `cleanup all test keys`() {
        // 清理所有测试 key
        val patterns = listOf(
            "test:*",
            "global:captcha_codes:*"
        )

        patterns.forEach { pattern ->
            val keys = RedisUtils.keys(pattern)
            println("🔍 找到 $pattern 匹配的 key 数量: ${keys.size}")

            if (keys.isNotEmpty()) {
                keys.forEach { key ->
                    println("   - 删除: $key")
                }
                RedisUtils.deleteObject(keys)
                println("✅ 已删除 ${keys.size} 个 key")
            }
        }

        println("\n🎉 清理完成！现在可以运行 RedisUtilsTest 了")
    }

    @Test
    fun `cleanup specific key pattern`() {
        // 自定义要清理的 key 模式
        val pattern = "test:*"  // 修改这里来清理特定的 key

        val keys = RedisUtils.keys(pattern)
        println("🔍 找到匹配的 key: ${keys.size} 个")

        keys.forEach { key ->
            println("   - $key")
        }

        if (keys.isNotEmpty()) {
            print("\n确认要删除这些 key 吗? (测试会自动执行，这里仅供查看)")
            RedisUtils.deleteObject(keys)
            println("✅ 已删除 ${keys.size} 个 key")
        } else {
            println("ℹ️  没有找到匹配的 key")
        }
    }

    @Test
    fun `list all keys in Redis`() {
        // 列出所有 key（谨慎使用，生产环境可能有大量 key）
        val allKeys = RedisUtils.keys("*")

        println("📋 Redis 中的所有 key (共 ${allKeys.size} 个):")
        allKeys.sorted().forEach { key ->
            val ttl = RedisUtils.getTimeToLive<Any>(key)
            val ttlStr = when {
                ttl == -1L -> "永久"
                ttl > 0 -> "${ttl / 1000}s"
                else -> "已过期"
            }
            println("   - $key (TTL: $ttlStr)")
        }
    }

    @Test
    fun `cleanup and verify captcha keys`() {
        // 专门清理验证码相关的 key
        val pattern = "global:captcha_codes:*"

        println("🔍 清理验证码缓存...")
        val keys = RedisUtils.keys(pattern)

        if (keys.isNotEmpty()) {
            println("   找到 ${keys.size} 个验证码 key")
            RedisUtils.deleteObject(keys)
            println("✅ 已清理")
        } else {
            println("ℹ️  没有找到验证码缓存")
        }

        // 验证是否清理干净
        val remaining = RedisUtils.keys(pattern)
        if (remaining.isEmpty()) {
            println("✅ 验证码缓存已全部清理")
        } else {
            println("⚠️  仍有 ${remaining.size} 个 key 未清理")
        }
    }
}
