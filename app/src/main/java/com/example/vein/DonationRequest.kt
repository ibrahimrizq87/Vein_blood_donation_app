package com.example.vein

class DonationRequest {
    var userId:String?=null

    var name:String? = null
    var phone :String? = null
    var city :String? = null
    var bloodType :String? = null
    var numberOfBags:Int?=null

    constructor()
    constructor(userId:String?,name:String?,bloodType :String?,Phone :String?,city :String?,numberOfBags:Int?){
        this.name=name
        this.city=city
        this.bloodType=bloodType
        this.phone=Phone
        this.numberOfBags=numberOfBags
        this.userId=userId



    }
}