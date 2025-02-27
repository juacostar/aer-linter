package com.uniandes.myapplication.viewmodel_error_handler

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.Category
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.JavaContext
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity
import com.intellij.psi.PsiMethod
import com.uniandes.myapplication.incorrect_dependency_injection.IncorrectDependencyInjectionIssue
import com.uniandes.myapplication.naming_rule.MethodNamingIssue
import com.uniandes.myapplication.naming_rule.MethodNamingVisitor
import org.jetbrains.uast.UCallExpression
import org.jetbrains.uast.UClass
import org.jetbrains.uast.UElement
import org.jetbrains.uast.UQualifiedReferenceExpression

object ViewModelErrorHandlerIssue {

    /**
     * The fixed id of the issue
     */
    private const val ID = "ViewModelErrorHandlerIssue"

    /**
     * The priority, a number from 1 to 10 with 10 being most important/severe
     */
    private const val PRIORITY = 10

    /**
     * Description short summary (typically 5-6 words or less), typically describing
     * the problem rather than the fix (e.g. "Missing minSdkVersion")
     */
    private const val DESCRIPTION = "No exception handling in viewmodel statements"

    /**
     * A full explanation of the issue, with suggestions for how to fix it
     */
    private const val EXPLANATION = """
        Exception handling must be used in viewmodel statements for correct data handling.
        It could be generate an availability issue in architectural design of the app
    """

    /**
     * The associated category, if any @see [Category]
     */
    private val CATEGORY = Category.PERFORMANCE

    /**
     * The default severity of the issue
     */
    private val SEVERITY = Severity.WARNING

    @JvmField
    val ISSUE = Issue.create(
        ID,
        DESCRIPTION,
        EXPLANATION,
        CATEGORY,
        PRIORITY,
        SEVERITY,
        Implementation(
            MethodNamingIssue.MethodNamingDetector::class.java,
            Scope.JAVA_FILE_SCOPE
        )
    )

    class ViewModelErrorHandlerDetector : Detector(), Detector.UastScanner {
        override fun getApplicableUastTypes(): List<Class<out UElement>> =
            listOf(UQualifiedReferenceExpression::class.java,
                UCallExpression::class.java)

        override fun getApplicableMethodNames(): List<String>? {
            return listOf("subscribeOn")
        }

        override fun createUastHandler(context: JavaContext): UElementHandler =
            ViewModelErrorHandlerVisitor(context)


        fun getApplicableIssues(): List<Issue> = listOf(ViewModelErrorHandlerIssue.ISSUE)

    }
}