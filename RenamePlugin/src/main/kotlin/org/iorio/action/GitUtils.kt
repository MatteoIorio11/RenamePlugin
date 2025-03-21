package org.iorio.action

import java.io.File

object GitUtils {
    fun isRepository(path: String): Boolean {
        return false
    }

    fun lastCommitName(path: String): String {
        return ""
    }

    fun changeLastCommitName(newName: String, path: String) {

    }

    private fun executeCommand(command: List<String>, workingDir: File) {

    }
}
