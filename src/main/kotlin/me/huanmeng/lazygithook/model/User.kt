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
data class User(
    var name: String? = null,
    var email: String? = null,
    var login: String? = null,
    var id: Long? = null,

    @JsonProperty("node_id")
    var nodeId: String? = null,

    @JsonProperty("avatar_url")
    var avatarUrl: String? = null,

    @JsonProperty("gravatar_id")
    var gravatarId: String? = null,
    var url: String? = null,

    @JsonProperty("html_url")
    var htmlUrl: String? = null,

    @JsonProperty("followers_url")
    var followersUrl: String? = null,

    @JsonProperty("following_url")
    var followingUrl: String? = null,

    @JsonProperty("gists_url")
    var gistsUrl: String? = null,

    @JsonProperty("starred_url")
    var starredUrl: String? = null,

    @JsonProperty("subscriptions_url")
    var subscriptionsUrl: String? = null,

    @JsonProperty("organizations_url")
    var organizationsUrl: String? = null,

    @JsonProperty("repos_url")
    var reposUrl: String? = null,

    @JsonProperty("events_url")
    var eventsUrl: String? = null,

    @JsonProperty("received_events_url")
    var receivedEventsUrl: String? = null,
    var type: String? = null,

    @JsonProperty("site_admin")
    var siteAdmin: Boolean? = null,
    val unknownField: MutableMap<String, Any?> = hashMapOf()
) {
    @JsonAnySetter
    fun setUnknownField(key: String, value: Any?) {
        unknownField[key] = value
    }

    operator fun <V, V1 : V> getValue(thisRef: Any?, property: KProperty<*>): V1 = locateValue(unknownField, property)
}