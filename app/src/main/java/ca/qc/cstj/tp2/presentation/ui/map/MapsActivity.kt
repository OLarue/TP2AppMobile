package ca.qc.cstj.tp2.presentation.ui.map

import android.Manifest
import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.navigation.navArgs
import ca.qc.cstj.tp2.R
import ca.qc.cstj.tp2.databinding.ActivityMapsBinding
import com.fondesa.kpermissions.allDenied
import com.fondesa.kpermissions.allGranted
import com.fondesa.kpermissions.extension.permissionsBuilder

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var cMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    private val args: MapsActivityArgs by navArgs()

    private val locationRequestPermission by lazy {
        permissionsBuilder(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        ).build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        locationRequestPermission.send()

        if(locationRequestPermission.checkStatus().allDenied()){
            Toast.makeText(this,"Permission non accordée", Toast.LENGTH_LONG).show()
            finish()
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    override fun onMapReady(googleMap: GoogleMap) {
        cMap = googleMap


        if (locationRequestPermission.checkStatus().allGranted()){
            @SuppressLint("MissingPermission")
            cMap.isMyLocationEnabled = true
        }

        val customerMarkerOptions = MarkerOptions()
            .position(args.position)
            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
            .title(args.customerName)
        cMap.addMarker(customerMarkerOptions)

        val cameraOptions = CameraPosition.Builder()
            .target(args.position)
            .zoom(15f).build()


        cMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraOptions))


    }
}