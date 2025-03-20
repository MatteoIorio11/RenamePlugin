package org.iorio.action

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.vcs.FilePath
import com.intellij.openapi.vcs.LocalFilePath
import com.intellij.openapi.vcs.changes.ui.AsyncChangesTreeImpl.FilePaths
import java.io.File

class RenameCommitAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project
        val name = project?.basePath?.let { lastCommitName(it) }
        val newName = Messages.showInputDialog("Enter new commit", "Enter the new commit name", null, name, null)
        project?.basePath?.let { changeLastCommitName(newName!!, it) }
    }

    private fun changeLastCommitName(newName: String, path: String) {
        val command = listOf("git", "commit", "--amend", "-m", newName)
        executeCommand(command, File(path))
    }

    private fun lastCommitName(path: String): String {
        val command = listOf("git", "log", "-1", "--pretty=format:%s")
        return executeCommand(command, File(path))
    }

    private fun executeCommand(command: List<String>, workingDir: File): String {
        val process = ProcessBuilder(command)
            .directory(workingDir)
            .redirectErrorStream(true)
            .start()
        return process.inputStream.bufferedReader().use { it.readText() }.trim()
    }
}
