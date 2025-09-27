package com.edu.only4.danmuku.application.commands.statistics

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import org.springframework.stereotype.Service

object UpdateStatisticsInfoCmd{

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            Mediator.uow.save()

            return Response(
            )
        }

    }

    class Request(
    ) : RequestParam<Response>

    class Response(
    )
}
