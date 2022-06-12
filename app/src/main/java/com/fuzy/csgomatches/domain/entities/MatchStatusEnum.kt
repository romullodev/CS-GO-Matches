package com.fuzy.csgomatches.domain.entities

enum class MatchStatusEnum (val value: String) {
    RUNNING("RUNNING"),
    CANCELED("CANCELED"),
    NOT_STARTED("NOT_STARTED"),
    FINISHED("FINISHED"),
    NO_STATUS("no_status"),
}