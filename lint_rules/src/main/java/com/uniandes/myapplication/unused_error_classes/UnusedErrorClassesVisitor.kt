package com.uniandes.myapplication.unused_error_classes

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.JavaContext
import com.uniandes.myapplication.naming_rule.MethodNamingIssue
import org.jetbrains.uast.UClass
import org.jetbrains.uast.UFile
import org.jetbrains.uast.UMethod
import org.jetbrains.uast.USimpleNameReferenceExpression

class UnusedErrorClassesVisitor(private val context: JavaContext) : UElementHandler() {
    private val declaredClasses = mutableSetOf<String>()
    private val referencedClasses = mutableSetOf<String>()

    override fun visitClass(node: UClass) {
        node.qualifiedName?.let { declaredClasses.add(it) }
    }

    override fun visitSimpleNameReferenceExpression(node: USimpleNameReferenceExpression) {
        node.resolvedName?.let { referencedClasses.add(it) }
    }

    override fun visitFile(node: UFile) {
        node.classes.forEach { uClass ->
            uClass.qualifiedName?.let {
                declaredClasses.add(it)
                println("clase declarada: $it")
            }
        }


        val unusedClasses = declaredClasses - referencedClasses
        for (className in unusedClasses) {
            if(className.lowercase().contains("exception") || className.lowercase().contains("error")){
                reportIssue(node)
            }
        }
    }

    private fun reportIssue(node: UFile) {
        context.report(
            issue = UnusedErrorClassesIssue.ISSUE,
            location = context.getLocation(node),
            message = """
                [Class] Unused customized exception classes. Could generate an availability issue
            """
        )
    }
}