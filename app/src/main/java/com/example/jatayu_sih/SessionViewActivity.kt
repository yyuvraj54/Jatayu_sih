package com.example.jatayu_sih

import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.jatayu_sih.loaction.LocationService
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class SessionViewActivity : AppCompatActivity() {
    private lateinit var imageAdd: ImageButton
    private lateinit var tvSurviversData: TextView
    private lateinit var tvEstimatedAfffectiesData: TextView
    private lateinit var tvInjuredData: TextView
    private lateinit var tvCasualitiesData: TextView
    private lateinit var tvSeverityLevelData: TextView

    private lateinit var floatingButton: FloatingActionButton
    private lateinit var uploadButton: TextView
    private lateinit var fbEditButton: FloatingActionButton
    private lateinit var ibNeedMoreAssistance:ImageButton
    private lateinit var ibAddArea:ImageButton
    private lateinit var prefs: loginStatus
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var googleMap: GoogleMap


    var auth: FirebaseAuth?=null
    var database: FirebaseDatabase? = null
    var storage: FirebaseStorage? = null
    var selectedImage: Uri? = null
    private lateinit var uri: Uri
    var image:String?=null
    private var organisationId: String? = null


    override fun onResume() {
        super.onResume()
        prefs = loginStatus(this@SessionViewActivity)
        val userId = prefs.userId
        val surviver = prefs.survivors.toString()
        val injured = prefs.injured.toString()
        val Estimated = prefs.estimatedAffectees.toString()
        val casualities = prefs.casualties.toString()
        val severity = prefs.severity.toString()


        tvSurviversData =findViewById<TextView>(R.id.tvSurviversData)
        tvInjuredData =findViewById<TextView>(R.id.tvInjuredData)
        tvEstimatedAfffectiesData =findViewById<TextView>(R.id.tvEstimatedAfffectiesData)
        tvCasualitiesData =findViewById<TextView>(R.id.tvCasualitiesData)
        tvSeverityLevelData =findViewById<TextView>(R.id.tvSeverityLevelData)

        tvSurviversData.text=surviver
        tvInjuredData.text=injured
        tvEstimatedAfffectiesData.text=Estimated
        tvCasualitiesData.text=casualities
        tvSeverityLevelData.text=severity




    }

        override fun onDestroy() {
        super.onDestroy()
        prefs = loginStatus(this@SessionViewActivity)
        val userId = prefs.userId



        val intent=Intent(this@SessionViewActivity, LocationService::class.java).apply { action = LocationService.ACTION_STOP }
        intent.putExtra("troopId", "${userId}")
        startService(intent)

    }



    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_session_view)





//        // Hide the status bar
//        window.insetsController?.hide(WindowInsets.Type.statusBars())

        // Hide the status bar


        // Hide the status bar and make your app full-screen
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val controller = window.insetsController
            controller?.hide(WindowInsets.Type.statusBars())
            controller?.systemBarsBehavior =
                WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        } else {
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility = (
                    View.SYSTEM_UI_FLAG_FULLSCREEN
                            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    )
        }

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
        // Initialize FusedLocationProviderClient
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        fbEditButton.setOnClickListener {
            val intent= Intent(this@SessionViewActivity,EditDetailsActivity::class.java)
            startActivity(intent)
        }

        window.decorView.systemUiVisibility= View.SYSTEM_UI_FLAG_FULLSCREEN
        supportActionBar?.hide()
        val supportMapFragment =
            supportFragmentManager.findFragmentById(R.id.google_map) as SupportMapFragment

        supportMapFragment.getMapAsync { map->

            // You can add your Google Map customization code here

            googleMap = map

            // Check for location permission
            if (ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted, enable the location layer
                googleMap.isMyLocationEnabled = true

                // Get the user's current location
                fusedLocationProviderClient.lastLocation.addOnSuccessListener { location: Location? ->
                    if (location != null) {
                        val currentLatLng = LatLng(location.latitude, location.longitude)

                        // Add a marker at the current location
                        val markerOptions = MarkerOptions()
                            .position(currentLatLng)
                            .title("Current Location")
                        googleMap.addMarker(markerOptions)

                        // Move the camera to the current location
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15f))
                    }
                }
            } else {
                // Request location permission if not granted
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                    LOCATION_PERMISSION_REQUEST_CODE
                )
            }
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
        // Hide the status bar and enable full-screen mode
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

    }
    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1001
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