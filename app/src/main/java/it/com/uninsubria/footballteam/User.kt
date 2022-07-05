package it.com.uninsubria.footballteam

data class User(
    var name: String,
    var email : String,
    var password : String
) {
    constructor ():this("","","")

    override fun toString(): String {
        return this.name + " " + this.email + " " + this.password
    }

    fun set(user:User){
        this.name = user.name
        this.email = user.email
        this.password = user.password
    }
}