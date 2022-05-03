package com.funin.todo.domain.todo

import com.funin.todo.domain.BaseTimeEntity
import com.funin.todo.domain.user.User
import javax.persistence.*

@Entity
class Todo : BaseTimeEntity() {

    @Id
    @Column(name = "todo_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column
    var content: String? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id") var author: User? = null
}