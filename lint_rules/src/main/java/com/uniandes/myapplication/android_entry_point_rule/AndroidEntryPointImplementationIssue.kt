@file:Suppress("UnstableApiUsage")

package com.uniandes.myapplication.android_entry_point_rule


import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.Category
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.JavaContext
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity
import org.jetbrains.uast.UClass
import org.jetbrains.uast.UElement

object AndroidEntryPointImplementationIssue {
    /**
     * The fixed id of the issue
     */
    private const val ID = "AndroidEntryPointImplementationIssue"

    /**
     * The priority, a number from 1 to 10 with 10 being most important/severe
     */
    private const val PRIORITY = 7

    /**
     * Description short summary (typically 5-6 words or less), typically describing
     * the problem rather than the fix (e.g. "Missing minSdkVersion")
     */
    private const val DESCRIPTION = "Use @AndroidEntryPoint before running the app"

    /**
     * A full explanation of the issue, with suggestions for how to fix it
     */
    private const val EXPLANATION = """
        Class is not contain @AndroidEntryPoint Annotation,
        Use @AndroidEntryPoint before running the app
    """

    /**
     * The associated category, if any @see [Category]
     */
    private val CATEGORY = Category.CORRECTNESS

    /**
     * The default severity of the issue
     */
    private val SEVERITY = Severity.WARNING

    val ISSUE = Issue.create(
        ID,
        DESCRIPTION,
        EXPLANATION,
        CATEGORY,
        PRIORITY,
        SEVERITY,
        Implementation(
            AndroidEntryPointDetector::class.java,
            Scope.JAVA_FILE_SCOPE
        )
    )

    class AndroidEntryPointDetector : Detector(), Detector.UastScanner {
        override fun getApplicableUastTypes(): List<Class<out UElement>> =
            listOf(UClass::class.java)

        override fun createUastHandler(context: JavaContext): UElementHandler =
            AndroidEntryPointVisitor(context)
    }
}