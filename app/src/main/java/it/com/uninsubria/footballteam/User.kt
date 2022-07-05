package it.com.uninsubria.footballteam

data class User(
    var nome: String,
    var email : String,
    var pw : String
) {
    constructor ():this("","","")

    override fun toString(): String {
        return this.nome + " " + this.email + " " + this.pw
    }
}