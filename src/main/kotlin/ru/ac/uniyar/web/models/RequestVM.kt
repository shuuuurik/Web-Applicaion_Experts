package ru.ac.uniyar.web.models

import org.http4k.template.ViewModel
import ru.ac.uniyar.domain.database.entities.RequestInfo

class RequestVM(
    val requestInfo: RequestInfo,
) : ViewModel
