package com.uniandes.myapplication.class_implementation_viewmodel_error

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.JavaContext
import com.uniandes.myapplication.incorrect_dependency_injection.IncorrectDependencyInjectionIssue
import org.jetbrains.uast.UCallExpression
import org.jetbrains.uast.ULambdaExpression
import org.jetbrains.uast.visitor.AbstractUastVisitor

class ClassImplementationViewModelErrorVisitor(private val context: JavaContext): UElementHandler() {

    override fun visitCallExpression(node: UCallExpression) {
        val lambdaExpression = node.valueArguments.lastOrNull() as? ULambdaExpression ?: return
        val body = lambdaExpression.body

        body.accept(object : AbstractUastVisitor(){
            override fun visitCallExpression(node: UCallExpression): Boolean {
                val className = node.classReference?.resolvedName ?: return super.visitCallExpression(node)
                if (className.contains("Adapter")) {
                    reportIssue(node)
                }
                return super.visitCallExpression(node)
            }
        })
    }


    private fun reportIssue(node: UCallExpression) {
        context.report(
            issue = ClassImplementationViewModelErrorIssue.ISSUE,
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