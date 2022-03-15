package com.funin.todo.domain.user

import com.funin.todo.domain.BaseTimeEntity
import javax.persistence.*

@Entity
class User : BaseTimeEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(nullable = false, unique = true)
    var nickname: String? = null

    @Column(nullable = false)
    var password: String? = null

    @Column(nullable = false)
    var salt: ByteArray? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        if (id != other.id) return false
        if (nickname != other.nickname) return false
        if (password != other.password) return false
        if (salt != null) {
            if (other.salt == null) return false
            if (!salt.contentEquals(other.salt)) return false
        } else if (other.salt != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (nickname?.hashCode() ?: 0)
        result = 31 * result + (password?.hashCode() ?: 0)
        result = 31 * result + (salt?.contentHashCode() ?: 0)
        return result
    }
}