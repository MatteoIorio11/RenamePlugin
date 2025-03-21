package org.iorio.action

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.ui.Messages
import java.io.File

class RenameCommitAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project
        project?.let {
            it.basePath?.let { basePath ->
                if (!GitUtils.isRepository(basePath)) {
                    Messages.showErrorDialog("This project is not a git repository", "Error")
                    return
                } else {
                    val name = GitUtils.lastCommitName(basePath)
                    val newName =
                        Messages.showInputDialog("Enter new commit", "Enter the new commit name", null, name, null)
                    GitUtils.changeLastCommitName(newName!!, basePath)
                }
            }
        }
    }
}
