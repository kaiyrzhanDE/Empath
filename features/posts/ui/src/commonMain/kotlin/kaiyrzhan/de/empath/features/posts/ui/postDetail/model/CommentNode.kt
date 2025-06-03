package kaiyrzhan.de.empath.features.posts.ui.postDetail.model

import kaiyrzhan.de.empath.features.posts.ui.model.CommentUi

internal data class CommentNode(
    val comment: CommentUi,
    val replies: MutableList<CommentNode> = mutableListOf()
)

internal fun List<CommentUi>.buildCommentTree(): List<CommentNode> {
    val nodeMap = associateBy { it.id }
        .mapValues { CommentNode(it.value) }
        .toMutableMap()

    val roots = mutableListOf<CommentNode>()

    for ((id, node) in nodeMap) {
        val parentId = node.comment.parentId
        if (parentId.isBlank() || parentId == id || !nodeMap.containsKey(parentId)) {
            roots.add(node)
        } else {
            if (!isCircular(node, parentId, nodeMap)) {
                nodeMap[parentId]?.replies?.add(node)
            } else {
                roots.add(node)
            }
        }
    }

    return roots
}

private fun isCircular(
    node: CommentNode,
    parentId: String,
    map: Map<String, CommentNode>
): Boolean {
    var current = map[parentId]
    while (current != null) {
        if (current.comment.id == node.comment.id) return true
        current = map[current.comment.parentId]
    }
    return false
}