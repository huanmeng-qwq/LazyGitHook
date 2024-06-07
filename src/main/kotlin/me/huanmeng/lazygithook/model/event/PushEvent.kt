package me.huanmeng.lazygithook.model.event

import com.fasterxml.jackson.annotation.JsonAnySetter
import com.fasterxml.jackson.annotation.JsonProperty
import me.huanmeng.lazygithook.model.*
import me.huanmeng.lazykook.locateValue
import kotlin.reflect.KProperty

/**
 * 2024/4/30<br>
 * LazyGitHook<br>
 * @author huanmeng_qwq
 */
data class PushEvent(
    val ref: String,
    val before: String,
    val after: String,
    val repository: Repository,
    val sender: User,
    val pusher: Pusher,
    val organization: Organization? = null,
    val created: Boolean,
    val deleted: Boolean,
    val forced: Boolean,

    @JsonProperty("base_ref")
    val baseRef: String? = null,
    val compare: String,
    val commits: Array<Commit>? = null,
    @JsonProperty("head_commit")
    val headCommit: Commit? = null,
    val unknownField: MutableMap<String, Any?> = hashMapOf()
) {
    @JsonAnySetter
    fun setUnknownField(key: String, value: Any?) {
        unknownField[key] = value
    }

    operator fun <V, V1 : V> getValue(thisRef: Any?, property: KProperty<*>): V1 = locateValue(unknownField, property)
}