package edu.only4.danmuku.edu.only4.danmuku.extra

import com.only.engine.redis.misc.RedisUtils
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class RedisUtilsTest {

    data class TestObj(
        val name: String,
        val age: Int,
        val testObj: TestObj? = null
    )

    data class ComplexObj(
        val id: Long,
        val title: String,
        val tags: List<String>,
        val metadata: Map<String, Any>
    )

    @Test
    fun `test simple object serialization and deserialization`() {
        // Given: 创建测试对象
        val obj = TestObj("张三", 25)
        val key = "test:simple:${System.currentTimeMillis()}"

        // When: 保存到 Redis
        RedisUtils.setCacheObject(key, obj)

        // Then: 从 Redis 获取并验证
        val result = RedisUtils.getCacheObject<TestObj>(key)

        assertNotNull(result, "对象不应该为 null")
        assertEquals("张三", result!!.name, "name 字段应该正确反序列化")
        assertEquals(25, result.age, "age 字段应该正确反序列化")
        assertNull(result.testObj, "嵌套对象应该为 null")

        // 验证类型正确
        assertTrue(result is TestObj, "应该反序列化为 TestObj 类型，而不是 LinkedHashMap")

        println("✅ 简单对象测试通过: $result")

        // Clean up
        RedisUtils.deleteObject(key)
    }

    @Test
    fun `test nested object serialization`() {
        // Given: 创建嵌套对象
        val nestedObj = TestObj("内嵌对象", 30)
        val obj = TestObj("外层对象", 25, nestedObj)
        val key = "test:nested:${System.currentTimeMillis()}"

        // When: 保存到 Redis
        RedisUtils.setCacheObject(key, obj)

        // Then: 从 Redis 获取并验证
        val result = RedisUtils.getCacheObject<TestObj>(key)

        assertNotNull(result, "对象不应该为 null")
        assertEquals("外层对象", result!!.name)
        assertEquals(25, result.age)

        // 验证嵌套对象
        assertNotNull(result.testObj, "嵌套对象不应该为 null")
        assertEquals("内嵌对象", result.testObj!!.name)
        assertEquals(30, result.testObj!!.age)

        println("✅ 嵌套对象测试通过: $result")

        // Clean up
        RedisUtils.deleteObject(key)
    }

    @Test
    fun `test complex object with collections`() {
        // Given: 创建包含集合的复杂对象
        val obj = ComplexObj(
            id = 1001L,
            title = "测试标题",
            tags = listOf("Kotlin", "Redis", "Spring Boot"),
            metadata = mapOf(
                "channel" to "INLINE",
                "priority" to 10,
                "enabled" to true
            )
        )
        val key = "test:complex:${System.currentTimeMillis()}"

        // When: 保存到 Redis
        RedisUtils.setCacheObject(key, obj)

        // Then: 从 Redis 获取并验证
        val result = RedisUtils.getCacheObject<ComplexObj>(key)

        assertNotNull(result, "对象不应该为 null")
        assertEquals(1001L, result!!.id)
        assertEquals("测试标题", result.title)

        // 验证 List 集合
        assertEquals(3, result.tags.size, "tags 列表大小应该为 3")
        assertTrue(result.tags.contains("Redis"), "tags 应该包含 'Redis'")

        // 验证 Map 集合
        assertEquals("INLINE", result.metadata["channel"])
        assertEquals(10, result.metadata["priority"])
        assertEquals(true, result.metadata["enabled"])

        println("✅ 复杂对象测试通过: $result")

        // Clean up
        RedisUtils.deleteObject(key)
    }

    @Test
    fun `test getCacheObject with wrong type should not throw ClassCastException`() {
        // Given: 保存一个 TestObj
        val obj = TestObj("测试", 20)
        val key = "test:wrongtype:${System.currentTimeMillis()}"
        RedisUtils.setCacheObject(key, obj)

        // When & Then: 尝试用不同类型获取
        // 由于使用了 JsonJacksonCodec，Jackson 会尽力转换
        // 如果字段不匹配，应该返回 null 或部分匹配的对象，而不是抛出 ClassCastException
        assertDoesNotThrow {
            val result = RedisUtils.getCacheObject<ComplexObj>(key)
            println("⚠️ 类型不匹配测试: result = $result")
        }

        // Clean up
        RedisUtils.deleteObject(key)
    }

    @Test
    fun `test RedisUtils utility methods`() {
        val key = "test:util:${System.currentTimeMillis()}"
        val obj = TestObj("工具方法测试", 18)

        // Test: setCacheObject
        RedisUtils.setCacheObject(key, obj)
        assertTrue(RedisUtils.isExistsObject(key), "对象应该存在")

        // Test: getCacheObject
        val result = RedisUtils.getCacheObject<TestObj>(key)
        assertNotNull(result)
        assertEquals("工具方法测试", result!!.name)

        // Test: deleteObject
        assertTrue(RedisUtils.deleteObject(key), "删除应该成功")
        assertFalse(RedisUtils.isExistsObject(key), "对象应该已被删除")

        println("✅ RedisUtils 工具方法测试通过")
    }
}
