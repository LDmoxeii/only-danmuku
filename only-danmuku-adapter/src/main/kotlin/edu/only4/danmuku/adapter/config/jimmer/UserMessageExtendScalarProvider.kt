package edu.only4.danmuku.adapter.config.jimmer

import com.only.engine.json.misc.JsonUtils
import edu.only4.danmuku.domain.aggregates.customer_message.extend.UserMessageExtend
import org.babyfish.jimmer.sql.runtime.ScalarProvider
import org.springframework.stereotype.Component

/**
 * Jimmer scalar provider for [UserMessageExtend].
 *
 * Maps JSON stored in `extend_json` column to [UserMessageExtend] and back.
 */
@Component
class UserMessageExtendScalarProvider : ScalarProvider<UserMessageExtend, String> {

    override fun toScalar(sqlValue: String): UserMessageExtend =
        JsonUtils.parseObject(sqlValue, UserMessageExtend::class.java)
            ?: UserMessageExtend()

    override fun toSql(scalarValue: UserMessageExtend): String =
        JsonUtils.toJsonString(scalarValue) ?: ""
}

