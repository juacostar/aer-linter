package com.uniandes.myapplication.logic_functions_rule

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.JavaContext
import org.jetbrains.uast.UBinaryExpression
import org.jetbrains.uast.UCallExpression
import org.jetbrains.uast.UClass
import org.jetbrains.uast.UForExpression
import org.jetbrains.uast.UMethod
import org.jetbrains.uast.UWhileExpression
import org.jetbrains.uast.visitor.AbstractUastVisitor

class LogicFunctionsVisitor(private val context: JavaContext): UElementHandler() {

    val classTypes = listOf("androidx.appcompat.app.AppCompatActivity", "androidx.fragment.app.Fragment")

    override fun visitClass(node: UClass) {
        if(node.superTypes.any{it.canonicalText in classTypes}){
            for(method in node.methods){

            }
        }
    }


    private fun returnsPrimitiveOrCollection(method: UMethod): Boolean {
        val returnType = method.returnType?.canonicalText ?: return false
        return returnType in listOf("int", "double", "boolean", "java.lang.String", "java.util.List", "java.util.Map", "java.util.Set")
    }

}