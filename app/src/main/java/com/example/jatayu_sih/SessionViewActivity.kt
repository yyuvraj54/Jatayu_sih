package com.example.jatayu_sih

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class SessionViewActivity : AppCompatActivity() {
    private lateinit var imageAdd: ImageButton
    private lateinit var floatingButton: FloatingActionButton
    private lateinit var uploadButton: Button
    private lateinit var fbEditButton: FloatingActionButton
    private lateinit var ibNeedMoreAssistance:ImageButton
    private lateinit var ibAddArea:ImageButton


    var auth: FirebaseAuth?=null
    var database: FirebaseDatabase? = null
    var storage: FirebaseStorage? = null
    var selectedImage: Uri? = null
    private lateinit var uri: Uri
    var image:String?=null
    private var organisationId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_session_view)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        storage = FirebaseStorage.getInstance()

        imageAdd = findViewById(R.id.ibImage)
        floatingButton = findViewById(R.id.floatingActionButton)
        uploadButton = findViewById(R.id.btnUpload)
        fbEditButton=findViewById(R.id.fbEdit)
        ibNeedMoreAssistance=findViewById(R.id.ibNeedMoreAssistance)
        ibAddArea=findViewById(R.id.ibAddArea)

        organisationId = intent.getStringExtra("organizationId")

        fbEditButton.setOnClickListener {
            val intent= Intent(this@SessionViewActivity,EditDetailsActivity::class.java)
            startActivity(intent)
        }

        ibNeedMoreAssistance.setOnClickListener {
            val intent=Intent(this@SessionViewActivity,NeedMoreAssistance::class.java)
            startActivity(intent)
        }
        ibAddArea.setOnClickListener {
            val intent=Intent(this@SessionViewActivity,AddArea::class.java)
            startActivity(intent)
        }

        floatingButton.setOnClickListener {
            ImagePicker.with(this)
                .compress(1024)
                .maxResultSize(1080, 1080)
                .start()
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        uploadButton=findViewById(R.id.btnUpload)
        val uid= auth?.currentUser?.uid
        database = FirebaseDatabase.getInstance()

        val organizationID=intent.getStringExtra("organizationID")
        val troopId=intent.getStringExtra("troopId")
        val time = Date().time.toString()

        if (resultCode == RESULT_OK) {
            val imageUri: Uri? = data?.data
            if (imageUri != null) {
                selectedImage = imageUri
                imageAdd.setImageURI(selectedImage)
                Toast.makeText(this, "Image selected successfully", Toast.LENGTH_SHORT).show()
            }
            if(uid!=null && selectedImage != null && organizationID != null){
                val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
                val imageName = "image_$timestamp.jpg"
                val folderName = "Users" // Change this to your desired folder name
                val imagePath = "$folderName/$imageName"
            }
            uploadButton.setOnClickListener {
                if (data != null) {
                    val uri = data.data//filepath
                    val storage = FirebaseStorage.getInstance()
                    val time = Date().time
                    val imagInfo= imageUri.toString()

                    val reference = storage.reference
                        .child("Profile")
                        .child(time.toString() + "")

                    reference.putFile(uri!!).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            reference.downloadUrl.addOnCompleteListener { uri ->
                                val filePath = uri.toString()

                                val databaseReference= database!!.reference
                                    .child("Users")
                                    .child("images")
                                    .push()

                                databaseReference.setValue(filePath)
                                    .addOnSuccessListener {
                                        image = filePath
                                        Toast.makeText(this,"Successfully uploaded", Toast.LENGTH_SHORT).show()
                                    }.addOnFailureListener {
                                        Toast.makeText(this,"Failed", Toast.LENGTH_SHORT).show()
                                    }
                            }
                        }
                        selectedImage = data.data
                    }
                }
            }
        }
    }
}