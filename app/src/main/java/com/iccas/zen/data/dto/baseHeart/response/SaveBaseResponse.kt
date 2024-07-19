package com.iccas.zen.data.dto.baseHeart.response

data class SaveBaseResponse(
    var status: Int,
    var message: String,
    var data: BaseData? = null
)