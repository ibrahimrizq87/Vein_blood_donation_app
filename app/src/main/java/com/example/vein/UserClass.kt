package com.example.vein

class UserClass {
    var userId:String?=null
    var name:String? = null
    var phone :String? = null
    var city :String? = null
    var bloodType :String? = null

    constructor()
    constructor(userId:String?,name:String?,bloodType :String?,Phone :String?,city :String?){
        this.name=name
        this.city=city
        this.bloodType=bloodType
        this.phone=Phone
        this.userId=userId


    }
}