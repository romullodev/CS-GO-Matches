package com.fuzy.csgomatches.external

class AppEndPoints {
    companion object {

        const val BASE_URL = "https://api.pandascore.co"
        private const val API = "/csgo"

        const val ENDPOINT_CS_GO_MATCHES = "$API/matches"
        const val ENDPOINT_CS_GO_TEAMS = "$API/teams"
    }
}