package com.uniandes.myapplication.android_entry_point_rule

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.JavaContext
import org.apache.log4j.Logger


import org.jetbrains.uast.UClass
import java.io.File

@Suppress("UnstableApiUsage")
class AndroidEntryPointVisitor(private val context: JavaContext) : UElementHandler() {
    private val logger = Logger.getLogger(AndroidEntryPointVisitor::class.java)
    private val debugFile = File("lint-debug.log")

    var DEBUG_MODE = System.getProperty("lint.debug")?.toBoolean() ?: false
    private val superClassQualifiedName = "com.uniandes.aer_linter.base.BaseFragment"
    init {
        // Inicializar archivo de debug
        if (DEBUG_MODE) {
            debugFile.writeText("") // Limpiar archivo previo
        }
    }

    override fun visitClass(node: UClass) {
        // Habilitar/deshabilitar debugging
        if (node.javaPsi.superClass?.qualifiedName == superClassQualifiedName &&
            !node.hasAnnotation("dagger.hilt.android.AndroidEntryPoint")
        ) {
            reportIssue(node)
        }
    }

    private fun reportIssue(node: UClass) {
        debug("reportando")
        context.report(
            issue = AndroidEntryPointImplementationIssue.ISSUE,
            scopeClass = node,
            location = context.getNameLocation(node),
            "Use @AndroidEntryPoint before running the app"
        )
    }

    private fun debug(message: String) {
        if (DEBUG_MODE) {
            logger.debug(message)
            debugFile.appendText("$message\n")
        }
    }
}