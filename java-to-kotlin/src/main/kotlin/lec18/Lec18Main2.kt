package lec18

import java.util.UUID

class Member(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
)

class Team(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val members: List<Member>,
)

fun main() {
    val members = listOf(
        Member(name = "mem_1"),
        Member(name = "mem_2"),
        Member(name = "mem_3"),
        Member(name = "mem_4"),
        Member(name = "mem_5"),
    )



    val teams = listOf(
        Team(name = "team_1", members = emptyList()),
        Team(name = "team_2", members = members.subList(0, 2)),
        Team(name = "team_3", members = members.subList(2, 5)),
    )


}