package me.huanmeng.lazygithook

import cn.hutool.core.date.DatePattern
import cn.hutool.core.date.DateTime
import cn.hutool.core.date.DateUtil
import com.fasterxml.jackson.databind.JsonNode
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import me.huanmeng.lazygithook.model.event.PushEvent
import me.huanmeng.lazykook.LazyKook
import me.huanmeng.lazykook.config.BotConfig
import me.huanmeng.lazykook.http.Requests
import me.huanmeng.lazykook.http.request.MessageCreateRequest
import me.huanmeng.lazykook.mapper
import me.huanmeng.lazykook.message.*
import me.huanmeng.lazykook.signal.event.SignalEventType
import org.bspfsystems.yamlconfiguration.configuration.ConfigurationSection
import org.bspfsystems.yamlconfiguration.file.YamlConfiguration
import java.io.File

/**
 * 2024/4/30<br>
 * LazyGitHook<br>
 * @author huanmeng_qwq
 */
class Runtime {
    lateinit var bot: LazyKook
    lateinit var config: ConfigurationSection
}

fun runtime(accept: (suspend Runtime.() -> Unit)) {
    runBlocking {
        accept(Runtime())
    }
}

fun main(): Unit = runtime {
    val file = File("config.yml").also {
        if (!it.exists()) {
            it.writeText(
                """
                token: 'please input your token'
                server: 
                  port: 8080
                channels: 
                  - 'channelId'
            """.trimIndent()
            )
        }
    }
    config = YamlConfiguration.loadConfiguration(file)
    val tk = config.getString("token")!!
    bot = LazyKook(BotConfig(tk))
    http(config.getConfigurationSection("server")!!)
    bot.start()
}

fun Runtime.http(config: ConfigurationSection) {
    embeddedServer(Netty, port = config.getInt("port", 8080)) {
        routing {
            post("/github") {
                val jsonStr = call.receiveText()
                val eventType = call.request.header("X-GitHub-Event")
                if (eventType == null) {
                    call.respond(
                        HttpStatusCode.InternalServerError, "This service only applies to the GitHub webhook feature"
                    )
                    return@post
                }
                async {
                    val node = mapper.readTree(jsonStr)
                    handle(eventType, node)
                }
                call.respond("fine.")
            }
        }
    }.start()
}

suspend fun Runtime.handle(event: String, node: JsonNode) {
    when (event) {
        "push" -> {
            val e = mapper.convertValue(node, PushEvent::class.java)

            val card1 = Card(buildList {
                add(SectionModule(Text(TextType.KMARKDOWN, content = "**Github提交推送**")))
                add(DividerModule())
                add(
                    ContextModule(
                        listOf(
                            Text(
                                TextType.KMARKDOWN, content = "${e.sender.name ?: e.sender.login} ${formatTime()}"
                            )
                        )
                    )
                )
                add(
                    ContextModule(
                        listOf(
                            Text(
                                TextType.KMARKDOWN,
                                content = "${e.repository.fullName} [${e.repository.name}](${e.repository.htmlUrl})"
                            )
                        )
                    )
                )
                add(
                    ContextModule(
                        listOf(
                            Text(
                                TextType.KMARKDOWN,
                                content = "分支: ${e.ref} (${e.before.shortId()}->${e.after.shortId()})"
                            )
                        )
                    )
                )
                add(
                    ContextModule(
                        listOf(
                            Text(
                                TextType.KMARKDOWN,
                                content = "推送成员: [${e.pusher.name}](https://github.com/${e.pusher.name})"
                            )
                        )
                    )
                )
            }, color = "#8a2be2")
            val card2 = Card(buildList {
                if (e.forced) {
                    add(
                        ContextModule(
                            listOf(
                                Text(type = TextType.KMARKDOWN, content = "强制推送")
                            )
                        )
                    )
                }
                add(
                    SectionModule(
                        Text(
                            TextType.KMARKDOWN, content = "提交成员: ${
                                e.commits?.map {
                                    if (it.author.username != it.committer.username) {
                                        it.author.username + "(committer: " + it.committer.username + ")"
                                    } else if (it.author.username == "invalid-email-address") {
                                        return@map it.author.name
                                    } else it.author.username
                                }?.distinct()?.joinToString(", ")
                            }"
                        )
                    )
                )
                add(DividerModule())
                add(SectionModule(Text(TextType.KMARKDOWN, content = "提交记录:")))
                add(
                    SectionModule(
                        Paragraph(
                            2,
                            listOf(
                                Text(
                                    content = buildString {
                                        appendLine("提交时间")
                                        e.commits?.forEach {
                                            appendLine(DateUtil.parse(it.timestamp as CharSequence).formatTime())
                                        }
                                    }.removeSuffix("\n")
                                ),
                                Text(
                                    content = buildString {
                                        appendLine("提交信息")
                                        e.commits?.forEach {
                                            appendLine(it.message)
                                        }
                                    }.removeSuffix("\n")
                                )
                            )
                        )
                    )
                )
                add(DividerModule())
                val compare = "${e.before.shortId()}...${e.after.shortId()}"
                add(
                    SectionModule(
                        Text(
                            TextType.KMARKDOWN,
                            content = "提交对比: [${compare}](${e.repository.htmlUrl}/compare/${compare})"
                        )
                    )
                )
                add(SectionModule(Text(TextType.KMARKDOWN,
                    content = "增/删/改: ${e.commits?.map { it.added.size }?.sum()}/${
                        e.commits?.sumOf { it.removed.size }
                    }/${e.commits?.sumOf { it.modified.size }}"
                )
                )
                )
            }, color = "#01ff22")

            val json = mapper.writeValueAsString(CardMessage(listOf(card1, card2)))
            println("Processing a push event...")
            if (config.contains("channels")) {
                config.getStringList("channels").forEach { channel ->
                    bot.http.http(
                        Requests.Message.CREATE,
                        MessageCreateRequest(channel, json, type = SignalEventType.CARD.value)
                    )
                }
            }
        }
    }
}

fun formatTime(time: Long = System.currentTimeMillis()): String {
    return DatePattern.NORM_DATETIME_FORMAT.format(time)
}

fun DateTime.formatTime(): String {
    return DatePattern.NORM_DATETIME_FORMAT.format(this)
}

fun String.shortId(): String = substring(0, 7)