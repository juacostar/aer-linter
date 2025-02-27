package com.uniandes.myapplication.incorrect_dependency_injection

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

object IncorrectDependencyInjectionIssue {

    /**
     * The fixed id of the issue
     */
    private const val ID = "IncorrectDependencyInjectionIssue"

    /**
     * The priority, a number from 1 to 10 with 10 being most important/severe
     */
    private const val PRIORITY = 10

    /**
     * Description short summary (typically 5-6 words or less), typically describing
     * the problem rather than the fix (e.g. "Missing minSdkVersion")
     */
    private const val DESCRIPTION = "incorrect Dependency Injection inside the class."

    /**
     * A full explanation of the issue, with suggestions for how to fix it
     */
    private const val EXPLANATION = """
        Injection dependencies are directly instantiated in constructor, you must use
        dagger hilt or abstract dependency injection
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
            IncorrectDependencyInjectionDetector::class.java,
            Scope.JAVA_FILE_SCOPE
        )
    )

    class IncorrectDependencyInjectionDetector : Detector(), Detector.UastScanner {
        override fun getApplicableUastTypes(): List<Class<out UElement>> =
            listOf(UClass::class.java)

        override fun createUastHandler(context: JavaContext): UElementHandler =
            IncorrectDependencyInjectionVisitor(context)

        fun getApplicableIssues(): List<Issue> = listOf(IncorrectDependencyInjectionIssue.ISSUE)

    }
}