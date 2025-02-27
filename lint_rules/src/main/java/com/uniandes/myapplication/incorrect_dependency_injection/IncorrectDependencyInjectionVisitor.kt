package com.uniandes.myapplication.incorrect_dependency_injection

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.JavaContext
import com.uniandes.myapplication.incorrect_dependency_injection.IncorrectDependencyInjectionIssue
import org.jetbrains.uast.UCallExpression
import org.jetbrains.uast.UClass
import org.jetbrains.uast.UMethod
import org.jetbrains.uast.UVariable
import org.jetbrains.uast.UastCallKind

class IncorrectDependencyInjectionVisitor(private val context: JavaContext): UElementHandler() {

    override fun visitCallExpression(node: UCallExpression) {
        if(node.kind == UastCallKind.CONSTRUCTOR_CALL){
            val parent = node.uastParent
            if(parent is UVariable){
                reportIssue(node)
            }
        }
    }


    private fun reportIssue(node: UCallExpression) {
        context.report(
            issue = IncorrectDependencyInjectionIssue.ISSUE,
            node,
            location = context.getLocation(node),
            message = """
                Dependency injection must be optimized, components musn't be declared
                in constructor component. Use dependency injection tools or responsabilities
                separation.
            """
        )
    }

}