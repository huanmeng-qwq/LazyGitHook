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
data class Repository(
    val id: Long,

    @JsonProperty("node_id")
    val nodeId: String,
    val name: String,

    @JsonProperty("full_name")
    val fullName: String,

    @JsonProperty("private")
    val privateX: Boolean,
    val owner: User,

    @JsonProperty("html_url")
    val htmlUrl: String,
    val description: String,
    val fork: Boolean,
    val url: String,

    @JsonProperty("forks_url")
    val forksUrl: String,

    @JsonProperty("keys_url")
    val keysUrl: String,

    @JsonProperty("collaborators_url")
    val collaboratorsUrl: String,

    @JsonProperty("teams_url")
    val teamsUrl: String,

    @JsonProperty("hooks_url")
    val hooksUrl: String,

    @JsonProperty("issue_events_url")
    val issueEventsUrl: String,

    @JsonProperty("events_url")
    val eventsUrl: String,

    @JsonProperty("assignees_url")
    val assigneesUrl: String,

    @JsonProperty("branches_url")
    val branchesUrl: String,

    @JsonProperty("tags_url")
    val tagsUrl: String,

    @JsonProperty("blobs_url")
    val blobsUrl: String,

    @JsonProperty("git_tags_url")
    val gitTagsUrl: String,

    @JsonProperty("git_refs_url")
    val gitRefsUrl: String,

    @JsonProperty("trees_url")
    val treesUrl: String,

    @JsonProperty("statuses_url")
    val statusesUrl: String,

    @JsonProperty("languages_url")
    val languagesUrl: String,

    @JsonProperty("stargazers_url")
    val stargazersUrl: String,

    @JsonProperty("contributors_url")
    val contributorsUrl: String,

    @JsonProperty("subscribers_url")
    val subscribersUrl: String,

    @JsonProperty("subscription_url")
    val subscriptionUrl: String,

    @JsonProperty("commits_url")
    val commitsUrl: String,

    @JsonProperty("git_commits_url")
    val gitCommitsUrl: String,

    @JsonProperty("comments_url")
    val commentsUrl: String,

    @JsonProperty("issue_comment_url")
    val issueCommentUrl: String,

    @JsonProperty("contents_url")
    val contentsUrl: String,

    @JsonProperty("compare_url")
    val compareUrl: String,

    @JsonProperty("merges_url")
    val mergesUrl: String,

    @JsonProperty("archive_url")
    val archiveUrl: String,

    @JsonProperty("downloads_url")
    val downloadsUrl: String,

    @JsonProperty("issues_url")
    val issuesUrl: String,

    @JsonProperty("pulls_url")
    val pullsUrl: String,

    @JsonProperty("milestones_url")
    val milestonesUrl: String,

    @JsonProperty("notifications_url")
    val notificationsUrl: String,

    @JsonProperty("labels_url")
    val labelsUrl: String,

    @JsonProperty("releases_url")
    val releasesUrl: String,

    @JsonProperty("deployments_url")
    val deploymentsUrl: String,

    @JsonProperty("created_at")
    val createdAt: Any,

    @JsonProperty("updated_at")
    val updatedAt: String,

    @JsonProperty("pushed_at")
    val pushedAt: Any,

    @JsonProperty("git_url")
    val gitUrl: String,

    @JsonProperty("ssh_url")
    val sshUrl: String,

    @JsonProperty("clone_url")
    val cloneUrl: String,

    @JsonProperty("svn_url")
    val svnUrl: String,
    val homepage: String,
    val size: Long,

    @JsonProperty("stargazers_count")
    val stargazersCount: Long,

    @JsonProperty("watchers_count")
    val watchersCount: Long,
    val language: String,

    @JsonProperty("has_issues")
    val hasIssues: Boolean,

    @JsonProperty("has_projects")
    val hasProjects: Boolean,

    @JsonProperty("has_downloads")
    val hasDownloads: Boolean,

    @JsonProperty("has_wiki")
    val hasWiki: Boolean,

    @JsonProperty("has_pages")
    val hasPages: Boolean,

    @JsonProperty("forks_count")
    val forksCount: Long,

    @JsonProperty("mirror_url")
    val mirrorUrl: String? = null,

    @JsonProperty("archived")
    val archived: Boolean,

    @JsonProperty("disabled")
    val disabled: Boolean,

    @JsonProperty("open_issues_count")
    val openIssuesCount: Long,

    @JsonProperty("license")
    val license: Any,

    @JsonProperty("allow_forking")
    val allowForking: Boolean,

    @JsonProperty("is_template")
    val isTemplate: Boolean,

    @JsonProperty("web_commit_signoff_required")
    val webCommitSignoffRequired: Boolean,

    @JsonProperty("topics")
    val topics: Array<String>,

    @JsonProperty("visibility")
    val visibility: String,

    @JsonProperty("forks")
    val forks: Long,

    @JsonProperty("open_issues")
    val openIssues: Long,

    @JsonProperty("watchers")
    val watchers: Long,

    @JsonProperty("default_branch")
    val defaultBranch: String,

    @JsonProperty("stargazers")
    val stargazers: Long,

    @JsonProperty("master_branch")
    val masterBranch: String,

    @JsonProperty("organization")
    val organization: String? = null,
    val unknownField: MutableMap<String, Any?> = hashMapOf()
) {
    @JsonAnySetter
    fun setUnknownField(key: String, value: Any?) {
        unknownField[key] = value
    }

    operator fun <V, V1 : V> getValue(thisRef: Any?, property: KProperty<*>): V1 = locateValue(unknownField, property)
}