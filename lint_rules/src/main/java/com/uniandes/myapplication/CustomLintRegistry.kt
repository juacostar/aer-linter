package com.uniandes.myapplication



import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.detector.api.CURRENT_API
import com.uniandes.myapplication.bocking_operatons_use.BlockingOperationUseIssue
import com.uniandes.myapplication.class_implementation_viewmodel_error.ClassImplementationViewModelErrorIssue
import com.uniandes.myapplication.error_handler.ErrorHandlerIssue
import com.uniandes.myapplication.incorrect_dependency_injection.IncorrectDependencyInjectionIssue
import com.uniandes.myapplication.naming_rule.MethodNamingIssue
import com.uniandes.myapplication.viewmodel_error_handler.ViewModelErrorHandlerIssue

class CustomLintRegistry : IssueRegistry() {
    override val issues =
        listOf(
            IncorrectDependencyInjectionIssue.ISSUE,
            ViewModelErrorHandlerIssue.ISSUE,
            ClassImplementationViewModelErrorIssue.ISSUE,
            BlockingOperationUseIssue.ISSUE,
            ErrorHandlerIssue.ISSUE
        )

    override val api: Int = CURRENT_API

    override val minApi: Int = 6
}