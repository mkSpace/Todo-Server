package com.funin.todo.domain.user

import com.funin.todo.domain.BaseTimeEntity
import javax.persistence.*

@Entity
class User : BaseTimeEntity(){

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(nullable = false)
    var nickname: String? = null

    @Column(nullable = false)
    var password: String? = null
}