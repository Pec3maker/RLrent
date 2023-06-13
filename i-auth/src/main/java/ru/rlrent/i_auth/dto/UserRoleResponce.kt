package ru.rlrent.i_auth.dto

import com.google.gson.annotations.SerializedName
import ru.rlrent.domain.user.UserRole
import ru.rlrent.i_network.network.Transformable

enum class UserRoleResponse : Transformable<UserRole> {

    @SerializedName("USER")
    USER,

    @SerializedName("ADMIN")
    ADMIN,

    @SerializedName("EMPLOYEE")
    EMPLOYEE,

    @SerializedName("TECHNICIAN")
    TECHNICIAN,

    @SerializedName("SUPPORT")
    SUPPORT;

    override fun transform(): UserRole {
        return when (this) {
            USER -> UserRole.USER
            ADMIN -> UserRole.ADMIN
            EMPLOYEE -> UserRole.EMPLOYEE
            TECHNICIAN -> UserRole.TECHNICIAN
            SUPPORT -> UserRole.SUPPORT
        }
    }
}
