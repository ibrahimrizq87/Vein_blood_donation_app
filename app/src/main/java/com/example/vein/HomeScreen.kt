package com.example.vein

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class HomeScreen : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var user: UserClass
var bloodType:String=""
    var city:String=""
    private lateinit var userImageUri: Uri
    companion object{
        val IMAGE_REQUEST_CODE =100
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)
        database = Firebase.database.reference
        auth = Firebase.auth
        userImageUri= Uri.EMPTY
        val donatBt=findViewById<Button>(R.id.donat)
        val request=findViewById<Button>(R.id.request)
        val donatBt2=findViewById<Button>(R.id.donat2)
        val logOut=findViewById<Button>(R.id.donat3)

        val request2=findViewById<Button>(R.id.request2)
        val nu=findViewById<EditText>(R.id.no_of_bags)
        val txt1=findViewById<TextView>(R.id.txt1)
        val txt2=findViewById<TextView>(R.id.txt2)


logOut.setOnClickListener {
    auth.signOut()
    startActivity(Intent(this,MainActivity::class.java))
}

        val cityList = resources.getStringArray(R.array.person_types)
        val adapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(this,R.array.person_types,R.layout.spinner_items)
        val autoCom = findViewById<Spinner>(R.id.personal_registration_spinner2)
        autoCom.setAdapter(adapter)
        val cityList2 = resources.getStringArray(R.array.spinnerItem)
        val adapter2: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(this,R.array.spinnerItem,R.layout.spinner_items)
        val autoCom2 = findViewById<Spinner>(R.id.city_spinner2)
        autoCom2.setAdapter(adapter2)

        autoCom.onItemSelectedListener= object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0 : AdapterView<*>?, p1: View?, p2:Int, p3: Long) { bloodType =cityList[p2]
                txt1.text=bloodType}
            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }
        autoCom2.onItemSelectedListener= object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0 : AdapterView<*>?, p1: View?, p2:Int, p3: Long) { city =cityList2[p2]
                txt2.text=city}
            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }




donatBt2.setOnClickListener {
    val intent =  Intent(this,RequestsList::class.java).also {
        it.putExtra("AHAH","AHAH")

    }
    startActivity(intent)
}

        request2.setOnClickListener {
            val intent =  Intent(this,OffersList::class.java).also {
                it.putExtra("AHAH","AHAH")

            }
            startActivity(intent)
        }
donatBt.setOnClickListener {
    var number=1
    if (nu.text.toString().isEmpty()){

}else{
    number=Integer. parseInt(nu.text.toString())
}
    addDonation( user.name,bloodType,user.phone,city, number)



}
        request.setOnClickListener {

            var number=1
            if (nu.text.toString().isEmpty()){

            }else{
                number=Integer. parseInt(nu.text.toString())
            }
            addRequest( user.name,bloodType,user.phone,city, number)

        }


    }


    private fun addDonation( name:String?,bloodType :String,Phone :String?,city :String,numberOfbags:Int) {
        val currentUser = auth.currentUser
        val id = currentUser?.uid
        if (name!!.isEmpty() || bloodType.isEmpty() || Phone!!.isEmpty() || city.isEmpty()) {
            Toast.makeText(this, "make sure to choose", Toast.LENGTH_LONG).show()

        } else {

            database.child("DonationOffers").child(city).child(bloodType).push().setValue(DonationOffer(id,name, bloodType, Phone, city,numberOfbags))
                .addOnSuccessListener {
                    Toast.makeText(this, "your offer has been placed", Toast.LENGTH_LONG).show()
                    val intent =  Intent(this,RequestsList::class.java).also {
                        it.putExtra("name",city)
                        it.putExtra("name2",bloodType)
                        it.putExtra("AHAH","bemo")
                    }
                    startActivity(intent)
                }.addOnFailureListener {
                    Toast.makeText(this, it.toString(), Toast.LENGTH_LONG).show()
                }
            database.child("DonationAllOffers").push().setValue(DonationOffer(id,name, bloodType, Phone, city,numberOfbags))
                .addOnSuccessListener {

                }.addOnFailureListener {
                    Toast.makeText(this, it.toString(), Toast.LENGTH_LONG).show()
                }
        }
    }
    private fun getUser(id:String){


        database.child("Users").child(id).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {



                    user = snapshot.getValue(UserClass::class.java) as UserClass


            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }
    private fun addRequest( name:String?,bloodType :String,Phone :String?,city :String,numberOfbags:Int) {
        val currentUser = auth.currentUser
        val id = currentUser?.uid
        if (name!!.isEmpty() || bloodType.isEmpty() || Phone!!.isEmpty() || city.isEmpty()) {
            Toast.makeText(this, "make sure to choose", Toast.LENGTH_LONG).show()

        } else {
            database.child("DonationRequests").child(city).child(bloodType).push().setValue(DonationRequest(id,name, bloodType, Phone, city,numberOfbags))
                .addOnSuccessListener {
                    Toast.makeText(this, "your request has been placed", Toast.LENGTH_LONG).show()
                    val intent =  Intent(this,OffersList::class.java).also {
                        it.putExtra("name",city)
                        it.putExtra("name2",bloodType)
                        it.putExtra("AHAH","bemo")

                    }
                    startActivity(intent)

                }.addOnFailureListener {
                    Toast.makeText(this, it.toString(), Toast.LENGTH_LONG).show()
                }
            database.child("DonationAllRequests").push().setValue(DonationRequest(id,name, bloodType, Phone, city,numberOfbags))
                .addOnSuccessListener {

                }.addOnFailureListener {
                    Toast.makeText(this, it.toString(), Toast.LENGTH_LONG).show()
                }

        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if(currentUser != null){
            val id=currentUser.uid
            getUser(id)

    }else{
    startActivity(Intent(this, MainActivity::class.java))
    }
    }
    private fun pickImageFromGallery(){
        val intent = Intent (Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_REQUEST_CODE)

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK)
        {
            userImageUri = data?.data!!

        }
    }


}

