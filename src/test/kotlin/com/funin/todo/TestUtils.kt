package com.funin.todo

import org.assertj.core.internal.bytebuddy.utility.RandomString
import kotlin.random.Random

fun generateSimpleString(): String = RandomString.make()

fun generateSimpleInteger(): Int = Random.nextInt()

fun generateSimpleLong(): Long = Random.nextLong()

fun generateSimpleAbsoluteLong(): Long = kotlin.math.abs(Random.nextLong())