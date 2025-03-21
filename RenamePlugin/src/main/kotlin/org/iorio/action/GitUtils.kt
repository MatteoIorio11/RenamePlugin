package org.iorio.action

import com.intellij.util.containers.stream
import java.io.File

object GitUtils {
    fun isRepository(path: String): Boolean {
        return File(path).listFiles()
            .stream()
            .anyMatch{ f: File -> f.isDirectory and f.name.equals(".git")}
    }

    fun lastCommitName(path: String): String {
        val command = listOf("git", "log", "-1", "--pretty=format:%s")
        return executeCommand(command, File(path))
    }

    fun changeLastCommitName(newName: String, path: String): String {
        val command = listOf("git", "commit", "--amend", "-m", newName)
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
