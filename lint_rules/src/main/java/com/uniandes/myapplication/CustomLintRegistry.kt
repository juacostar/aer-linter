package com.uniandes.myapplication
import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.detector.api.CURRENT_API
import com.uniandes.myapplication.android_entry_point_rule.AndroidEntryPointImplementationIssue
import com.uniandes.myapplication.error_handler.ErrorHandlerIssue
import com.uniandes.myapplication.naming_rule.MethodNamingIssue
import com.uniandes.myapplication.unused_error_classes.UnusedErrorClassesIssue

class CustomLintRegistry : IssueRegistry() {
    override val issues =
        listOf(
            AndroidEntryPointImplementationIssue.ISSUE,
            MethodNamingIssue.ISSUE,
            UnusedErrorClassesIssue.ISSUE,
            ErrorHandlerIssue.ISSUE
        )

    override val api: Int = CURRENT_API

    override val minApi: Int = 6
}