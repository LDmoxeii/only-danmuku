package edu.only4.danmuku.adapter.portal.api.payload.account

import edu.only4.danmuku.application.queries.user.GetUserCountInfoQry
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers

object GetUserCountInfo {
    data class Response(
        val fansCount: Long,
        val currentCoinCount: Int,
        val focusCount: Long
    )

    @Mapper(componentModel = "default")
    interface Converter {
        fun fromApp(response: GetUserCountInfoQry.Response): Response

        companion object {
            val INSTANCE: Converter = Mappers.getMapper(Converter::class.java)
        }
    }
}
