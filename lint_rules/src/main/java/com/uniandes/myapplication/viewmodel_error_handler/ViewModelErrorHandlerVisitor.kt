package com.uniandes.myapplication.viewmodel_error_handler

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.JavaContext
import org.jetbrains.uast.UCallExpression
import org.jetbrains.uast.UExpression
import org.jetbrains.uast.UQualifiedReferenceExpression

class ViewModelErrorHandlerVisitor(private val context: JavaContext): UElementHandler() {

    override fun visitCallExpression(node: UCallExpression) {
        val parent = node.uastParent
        if(parent is UQualifiedReferenceExpression){
            val callChain = getCallChain(parent)
            if (callChain.contains("subscribeOn") && callChain.contains("observeOn") && !callChain.contains("doOnError")) {
                reportIssue(node)
            }
        }
    }

    private fun getCallChain(expression: UQualifiedReferenceExpression): List<String> {
        val methods = mutableListOf<String>()
        var current: UExpression? = expression
        while (current is UQualifiedReferenceExpression) {
            val selector = current.selector as? UCallExpression
            selector?.methodName?.let { methods.add(it) }
            current = current.receiver
        }
        return methods
    }

    private fun reportIssue(node: UCallExpression) {
        context.report(
            issue = ViewModelErrorHandlerIssue.ISSUE,
            node,
            location = context.getLocation(node),
            message = """
                Error handling must be implemented in viewmodel statements. It could
                indicate an availability insight inside the application
            """
        )
    }

}