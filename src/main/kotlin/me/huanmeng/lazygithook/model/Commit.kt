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
data class Commit(
    val id: String,
    @JsonProperty("tree_id")
    val treeId: String,
    val distinct: String,
    val message: String,
    val timestamp: String,
    val url: String,
    val author: Author,
    val committer: Author,
    val added: Array<String>,
    val removed: Array<String>,
    val modified: Array<String>,
    val unknownField: MutableMap<String, Any?> = hashMapOf()
) {
    @JsonAnySetter
    fun setUnknownField(key: String, value: Any?) {
        unknownField[key] = value
    }

    operator fun <V, V1 : V> getValue(thisRef: Any?, property: KProperty<*>): V1 = locateValue(unknownField, property)
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Commit

        if (id != other.id) return false
        if (treeId != other.treeId) return false
        if (distinct != other.distinct) return false
        if (message != other.message) return false
        if (timestamp != other.timestamp) return false
        if (url != other.url) return false
        if (author != other.author) return false
        if (committer != other.committer) return false
        if (!added.contentEquals(other.added)) return false
        if (!removed.contentEquals(other.removed)) return false
        if (!modified.contentEquals(other.modified)) return false
        if (unknownField != other.unknownField) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + treeId.hashCode()
        result = 31 * result + distinct.hashCode()
        result = 31 * result + message.hashCode()
        result = 31 * result + timestamp.hashCode()
        result = 31 * result + url.hashCode()
        result = 31 * result + author.hashCode()
        result = 31 * result + committer.hashCode()
        result = 31 * result + added.contentHashCode()
        result = 31 * result + removed.contentHashCode()
        result = 31 * result + modified.contentHashCode()
        result = 31 * result + unknownField.hashCode()
        return result
    }
}
