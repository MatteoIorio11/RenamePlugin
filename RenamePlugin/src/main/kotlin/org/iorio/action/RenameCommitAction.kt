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
    }

    private fun lastCommitName(path: String): String {
        val command = "git log -1 --pretty=format:%s"
        return this.executeCommad(command, File(path))
    }

    private fun executeCommad(command: String, workingDir: File): String {
        val process = ProcessBuilder(command.split(" "))
            .directory(workingDir)
            .redirectErrorStream(true)
            .start()
        return process.inputStream.bufferedReader().use { it.readText() }
    }
}
