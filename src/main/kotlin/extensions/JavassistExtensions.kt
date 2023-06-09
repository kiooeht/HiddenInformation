package com.evacipated.cardcrawl.mod.hiddeninfo.extensions

import javassist.expr.FieldAccess
import javassist.expr.MethodCall
import javassist.expr.NewExpr
import kotlin.reflect.KClass

fun MethodCall.iz(clz: KClass<*>, name: String): Boolean =
    this.className == clz.java.name && this.methodName == name

fun NewExpr.iz(clz: KClass<*>): Boolean =
    this.className == clz.java.name

fun FieldAccess.iz(clz: KClass<*>, name: String): Boolean =
    this.className == clz.java.name && this.fieldName == name
