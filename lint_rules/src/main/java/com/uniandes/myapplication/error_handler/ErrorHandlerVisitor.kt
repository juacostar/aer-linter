package com.uniandes.myapplication.error_handler

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.JavaContext
import com.intellij.psi.PsiClass
import com.uniandes.myapplication.naming_rule.MethodNamingIssue
import org.jetbrains.uast.UBlockExpression
import org.jetbrains.uast.UCatchClause
import org.jetbrains.uast.UClass
import org.jetbrains.uast.UExpression
import org.jetbrains.uast.UFile
import org.jetbrains.uast.UMethod
import org.jetbrains.uast.UReferenceExpression
import org.jetbrains.uast.USimpleNameReferenceExpression
import org.jetbrains.uast.UTryExpression
import org.jetbrains.uast.visitor.AbstractUastVisitor

class ErrorHandlerVisitor(private val context: JavaContext): UElementHandler() {


        override fun visitTryExpression(node: UTryExpression) {
        println("visita try")
        for(clause in node.catchClauses){
            if (containsGenericExceptionClause(clause) || isEmptyClause(clause)
                || hasSoutStatement(clause)) reportIssue(clause)
        }
    }


    private fun containsGenericExceptionClause(node: UCatchClause): Boolean {
        for(parameter in node.parameters){
            if(parameter.type.equalsToText("Exception")) return true
        }
        return false
    }

    private fun isEmptyClause(node: UCatchClause): Boolean {
        val statements = (node.body as? UBlockExpression)?.expressions ?: return true
        return statements.all { statement ->
            statement.asRenderString().startsWith("//")
        }
    }

    private fun hasSoutStatement(node: UCatchClause): Boolean {
        val statements = (node.body as? UBlockExpression)?.expressions ?: return true
        return statements.all { statement ->
            statement.asRenderString().contains("print")
        }
    }



    private fun reportIssue(node: UCatchClause) {
        context.report(
            issue = ErrorHandlerIssue.ISSUE,
            location = context.getLocation(node),
            message = """
                Exceptions must process the error. Only print stack trace or comments is a bad practice.
            """
        )
    }

    private fun reportClassIssue(node: UClass) {
        context.report(
            issue = ErrorHandlerIssue.ISSUE,
            scopeClass = node,
            location = context.getNameLocation(node),
            message = """
                Custom exceptions must be used
            """
        )
    }

    /*
    * cases:
    * 1. only one catch clause that catches generic exception -i
    * 2. catch clauses contain comments -i
    * 3. catch clause that contains generic exception -i
    * 4. sealed classes use
    * 5. use of a custmize error handler class
    * 6. class shoud be in viewmodel
    * 7. sysout del stacktrace 
    * */
}