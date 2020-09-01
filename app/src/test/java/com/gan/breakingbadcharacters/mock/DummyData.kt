package com.gan.breakingbadcharacters.mock

import com.gan.breakingbadcharacters.data.model.BreakingBadCharacter

class DummyData {
    companion object {
        val dummyCharacter1 = BreakingBadCharacter(
            id = 1,
            seasonsAppearance = listOf(1,2),
            betterCallSaulAppearance = listOf(),
            birthday = "",
            category = "",
            avatarUrl = "",
            name = "Character 1",
            nickname = "CH1",
            occupation = listOf(),
            portrayed = "",
            status = ""
        )
        val dummyCharacter2 = BreakingBadCharacter(
            id = 2,
            seasonsAppearance = listOf(1,2,3),
            betterCallSaulAppearance = listOf(),
            birthday = "",
            category = "",
            avatarUrl = "",
            name = "Character 2",
            nickname = "CH2",
            occupation = listOf(),
            portrayed = "",
            status = ""
        )
    }
}