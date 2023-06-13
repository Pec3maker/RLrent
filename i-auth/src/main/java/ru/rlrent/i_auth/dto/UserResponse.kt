package ru.rlrent.i_auth.dto

import com.google.gson.annotations.SerializedName
import ru.rlrent.domain.user.User
import ru.rlrent.domain.user.UserRole
import ru.rlrent.i_network.network.Transformable
import ru.surfstudio.android.utilktx.ktx.text.EMPTY_STRING

data class UserResponse(
    @SerializedName("id") val id: Int?,
    @SerializedName("role") val role: UserRoleResponse?,
    @SerializedName("login") val login: String?,
    @SerializedName("avatarImageUrl") val imageUrl: String?,
    @SerializedName("firstName") val firstName: String?,
    @SerializedName("email") val email: String?,
    @SerializedName("totalTrips") val tripsCount: Int?,
    @SerializedName("spent") val tripsCost: Double?,
    @SerializedName("registeredAt") val registrationDate: String?,
    @SerializedName("phoneNumber") val phoneNumber: String?,
    @SerializedName("bill") val bill: Double?
) : Transformable<User> {

    override fun transform(): User {
        return User(
            id = id ?: 0,
            role = role?.transform() ?: UserRole.USER,
            login = login ?: EMPTY_STRING,
            imageUrl = imageUrl ?: EMPTY_STRING,
            firstName = firstName ?: EMPTY_STRING,
            email = email ?: EMPTY_STRING,
            tripsCount = tripsCount ?: 0,
            tripsCost = tripsCost ?: 0.0,
            registrationDate = registrationDate ?: EMPTY_STRING,
            phoneNumber = phoneNumber ?: EMPTY_STRING,
            bill = bill ?: 0.0
        )
    }
}