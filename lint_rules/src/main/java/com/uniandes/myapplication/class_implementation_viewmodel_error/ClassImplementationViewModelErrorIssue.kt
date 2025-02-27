package com.uniandes.myapplication.class_implementation_viewmodel_error

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.Category
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.JavaContext
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity
import com.uniandes.myapplication.incorrect_dependency_injection.IncorrectDependencyInjectionIssue
import com.uniandes.myapplication.incorrect_dependency_injection.IncorrectDependencyInjectionVisitor
import org.jetbrains.uast.UClass
import org.jetbrains.uast.UElement

object ClassImplementationViewModelErrorIssue {
    /**
     * The fixed id of the issue
     */
    private const val ID = "ClassImplementationViewModelErrorIssue"

    /**
     * The priority, a number from 1 to 10 with 10 being most important/severe
     */
    private const val PRIORITY = 10

    /**
     * Description short summary (typically 5-6 words or less), typically describing
     * the problem rather than the fix (e.g. "Missing minSdkVersion")
     */
    private const val DESCRIPTION = "Class declaration inside viewmodel statements"

    /**
     * A full explanation of the issue, with suggestions for how to fix it
     */
    private const val EXPLANATION = """
        Class declaration are implement inside viewmodel body statements. It could generate
        a performance issue and coupling insight inside view model code fragments
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
            CassImplementationViewModelErrorDetector::class.java,
            Scope.JAVA_FILE_SCOPE
        )
    )

    class CassImplementationViewModelErrorDetector : Detector(), Detector.UastScanner {
        override fun getApplicableUastTypes(): List<Class<out UElement>> =
            listOf(UClass::class.java)

        override fun createUastHandler(context: JavaContext): UElementHandler =
            ClassImplementationViewModelErrorVisitor(context)

        fun getApplicableIssues(): List<Issue> = listOf(ClassImplementationViewModelErrorIssue.ISSUE)

    }
}