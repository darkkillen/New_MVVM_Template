package com.darkkillen.archmvvm.vo.maintenance

data class MaintenanceResponse(
    val descMaintain: String,
    val isForceUpdate: Boolean,
    val isMaintain: Boolean,
    val isUpdatePlayStore: Boolean,
    val link: String,
    val versionCode: Int
)