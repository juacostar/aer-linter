@file:Suppress("UnstableApiUsage")

package com.uniandes.myapplication.naming_rule

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.Category
import com.android.tools.lint.detector.api.Context
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.JavaContext
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity
import org.jetbrains.uast.UElement
import org.jetbrains.uast.UMethod

object MethodNamingIssue {
    /**
     * The fixed id of the issue
     */
    private const val ID = "MethodNamingIssue"

    /**
     * The priority, a number from 1 to 10 with 10 being most important/severe
     */
    private const val PRIORITY = 10

    /**
     * Description short summary (typically 5-6 words or less), typically describing
     * the problem rather than the fix (e.g. "Missing minSdkVersion")
     */
    private const val DESCRIPTION = "Wrong function name suffix."

    /**
     * A full explanation of the issue, with suggestions for how to fix it
     */
    private const val EXPLANATION = """
        Function is wrongly named.
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
            MethodNamingDetector::class.java,
            Scope.JAVA_FILE_SCOPE
        )
    )

    class MethodNamingDetector : Detector(), Detector.UastScanner {
        override fun getApplicableUastTypes(): List<Class<out UElement>> =
            listOf(UMethod::class.java)

        override fun createUastHandler(context: JavaContext): UElementHandler =
            MethodNamingVisitor(context)

        fun getApplicableIssues(): List<Issue> = listOf(ISSUE)

    }


}