package com.uniandes.myapplication.bocking_operatons_use

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.JavaContext
import com.uniandes.myapplication.viewmodel_error_handler.ViewModelErrorHandlerIssue
import org.jetbrains.uast.UCallExpression

class BlockingOperationUseVisitor(private val context: JavaContext): UElementHandler() {

    override fun visitCallExpression(node: UCallExpression) {
        reportIssue(node)
    }

    private fun reportIssue(node: UCallExpression) {
        context.report(
            issue = BlockingOperationUseIssue.ISSUE,
            node,
            location = context.getLocation(node),
            message = """
                You must not use Blocking operations, can block current threads and
                generate problems in terms on response rate application metric.
            """
        )
    }
}