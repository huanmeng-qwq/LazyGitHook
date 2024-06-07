package me.huanmeng.lazygithook.model

import com.fasterxml.jackson.annotation.JsonAnySetter
import me.huanmeng.lazykook.locateValue
import kotlin.reflect.KProperty

/**
 * 2024/4/30<br>
 * LazyGitHook<br>
 * @author huanmeng_qwq
 */
data class Author(
    val name: String,
    val email: String,
    val username: String,
    val unknownField: MutableMap<String, Any?> = hashMapOf()
) {
    @JsonAnySetter
    fun setUnknownField(key: String, value: Any?) {
        unknownField[key] = value
    }

    operator fun <V, V1 : V> getValue(thisRef: Any?, property: KProperty<*>): V1 = locateValue(unknownField, property)
}