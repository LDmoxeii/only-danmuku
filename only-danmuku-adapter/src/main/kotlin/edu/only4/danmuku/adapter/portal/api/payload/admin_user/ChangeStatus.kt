package edu.only4.danmuku.adapter.portal.api.payload.admin_user

import edu.only4.danmuku.application.commands.user.ChangeUserStatusCmd
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.factory.Mappers

object ChangeStatus {

    data class Request(
        val userId: Long,
        val status: Int
    )

    @Mapper(componentModel = "default")
    interface Converter {

        @Mapping(target = "status", expression = "java(request.getStatus() == 1)")
        fun toCmd(request: Request): ChangeUserStatusCmd.Request

        companion object {
            val INSTANCE: Converter = Mappers.getMapper(Converter::class.java)
        }
    }
}
