package me.huanmeng.lazygithook.model

import com.fasterxml.jackson.annotation.JsonAnySetter
import com.fasterxml.jackson.annotation.JsonProperty
import me.huanmeng.lazykook.locateValue
import kotlin.reflect.KProperty

/**
 * 2024/4/30<br>
 * LazyGitHook<br>
 * @author huanmeng_qwq
 */
data class Organization(
    var login: String? = null,
    var id: String? = null,

    @JsonProperty("node_id")
    var nodeId: String? = null,
    var url: String? = null,

    @JsonProperty("repos_url")
    var reposUrl: String? = null,

    @JsonProperty("events_url")
    var eventsUrl: String? = null,

    @JsonProperty("hooks_url")
    var hooksUrl: String? = null,

    @JsonProperty("issues_url")
    var issuesUrl: String? = null,

    @JsonProperty("members_url")
    var membersUrl: String? = null,

    @JsonProperty("public_members_url")
    var publicMembersUrl: String? = null,

    @JsonProperty("avatar_url")
    var avatarUrl: String? = null,
    var description: String? = null,
    val unknownField: MutableMap<String, Any?> = hashMapOf()
) {
    @JsonAnySetter
    fun setUnknownField(key: String, value: Any?) {
        unknownField[key] = value
    }

    operator fun <V, V1 : V> getValue(thisRef: Any?, property: KProperty<*>): V1 = locateValue(unknownField, property)
}