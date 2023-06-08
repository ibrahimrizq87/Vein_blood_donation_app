package com.example.vein

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class RequestsList : AppCompatActivity() {


    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var medicalAdapter: OffersAdapter
    private lateinit var medicalOrderList: ArrayList<DonationOffer>
    var city:String=""
    var type:String=""
    var txt:String=""

    private lateinit var diseaseRecyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_requests_list)
        medicalOrderList = ArrayList()
        txt=intent.getStringExtra("AHAH").toString()







            city=intent.getStringExtra("name").toString()
            type=intent.getStringExtra("name2").toString()
            database = Firebase.database.reference
            auth = Firebase.auth

            medicalAdapter = OffersAdapter(this,medicalOrderList)
            diseaseRecyclerView = findViewById(R.id.order_recycler2)
            diseaseRecyclerView.layoutManager = LinearLayoutManager(this)
        if (txt!="AHAH"){
            downloadData(city,type)
        }else{
            downloadAllData()
        }
        medicalAdapter.setOnItemClickListener(object : OffersAdapter.onItemClickListener{
            override fun onItemClick(pos: Int) {

            }})

    }
        private fun downloadData(message1:String,message2:String){
            diseaseRecyclerView.adapter = medicalAdapter

            database.child("DonationRequests").child(message1).child(message2).addValueEventListener(object :
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    medicalOrderList.clear()
                    for (postSnapshot in snapshot.children){
                        val currentDisease = postSnapshot.getValue(DonationOffer::class.java)
                        medicalOrderList.add(currentDisease!!)
                    }
                    medicalAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })
        }

        private fun downloadAllData(){
            diseaseRecyclerView.adapter = medicalAdapter

            database.child("DonationAllRequests").addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    medicalOrderList.clear()
                    for (postSnapshot in snapshot.children){
                        val currentDisease = postSnapshot.getValue(DonationOffer::class.java)
                        medicalOrderList.add(currentDisease!!)
                    }
                    medicalAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })}
    }
