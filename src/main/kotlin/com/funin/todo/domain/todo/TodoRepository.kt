package com.funin.todo.domain.todo

import org.springframework.data.jpa.repository.JpaRepository

interface TodoRepository : JpaRepository<Todo, Long>