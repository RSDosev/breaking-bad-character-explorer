package com.gan.breakingbadcharacters.data.model

import com.google.gson.annotations.SerializedName

data class BreakingBadCharacter(
    @SerializedName("char_id")
    val id: Int,
    @SerializedName("appearance")
    val seasonsAppearance: List<Int>,
    @SerializedName("better_call_saul_appearance")
    val betterCallSaulAppearance: List<Int>,
    val birthday: String,
    val category: String,
    @SerializedName("img")
    val avatarUrl: String,
    val name: String,
    val nickname: String,
    val occupation: List<String>,
    val portrayed: String,
    val status: String
)