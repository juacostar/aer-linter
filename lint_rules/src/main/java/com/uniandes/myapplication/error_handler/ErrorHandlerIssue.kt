package com.uniandes.myapplication.error_handler

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.Category
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.JavaContext
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity
import com.uniandes.myapplication.naming_rule.MethodNamingIssue
import com.uniandes.myapplication.naming_rule.MethodNamingVisitor
import org.jetbrains.uast.UClass
import org.jetbrains.uast.UElement
import org.jetbrains.uast.UMethod
import org.jetbrains.uast.UTryExpression

object ErrorHandlerIssue {

    /**
     * The fixed id of the issue
     */
    private const val ID = "ErrorHandlerIssue"

    /**
     * The priority, a number from 1 to 10 with 10 being most important/severe
     */
    private const val PRIORITY = 10

    /**
     * Description short summary (typically 5-6 words or less), typically describing
     * the problem rather than the fix (e.g. "Missing minSdkVersion")
     */
    private const val DESCRIPTION = "Incorrect Error Handling"

    /**
     * A full explanation of the issue, with suggestions for how to fix it
     */
    private const val EXPLANATION = """
        Error Handling is not corrected implemented, use specific exceptions and process them.
    """

    /**
     * The associated category, if any @see [Category]
     */
    private val CATEGORY = Category.CUSTOM_LINT_CHECKS

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
            ErrorHandlingDetector::class.java,
            Scope.JAVA_FILE_SCOPE
        )
    )

    class ErrorHandlingDetector : Detector(), Detector.UastScanner {

        override fun createUastHandler(context: JavaContext): UElementHandler =
            ErrorHandlerVisitor(context)

        override fun getApplicableUastTypes(): List<Class<out UElement>> =
            listOf(UTryExpression::class.java)

        fun getApplicableIssues(): List<Issue> = listOf(ErrorHandlerIssue.ISSUE)
    }

}