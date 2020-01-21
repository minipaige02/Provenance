package minipaige.example.provenance.com.Controller

import android.Manifest
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.util.Size
import android.graphics.Matrix
import android.view.TextureView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Surface
import android.view.ViewGroup
import androidx.camera.core.*
import androidx.lifecycle.LifecycleOwner
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_camera.*
import minipaige.example.provenance.com.Model.ArchivalItem
import minipaige.example.provenance.com.R
import minipaige.example.provenance.com.Utilities.EXTRA_ARCHVIAL_ITEM
import java.io.File
import java.util.*
import java.util.concurrent.Executors


private const val REQUEST_CODE_PERMISSIONS = 10
private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)

class CameraActivity : MainActivity(), LifecycleOwner {
    private var firebaseStore: FirebaseStorage? = null
    private var storageReference: StorageReference? = null
    var imageLinks = arrayListOf<String>()

    //View finder and capture functionality mainly taken from
    // Google's CameraX Codelab: https://codelabs.developers.google.com/codelabs/camerax-getting-started/
    override fun onCreate(savedInstanceState: Bundle?) {
        println("Image links: $imageLinks")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)

        firebaseStore = FirebaseStorage.getInstance()
        storageReference = FirebaseStorage.getInstance().reference

        val archivalItem = intent.getParcelableExtra<ArchivalItem>(EXTRA_ARCHVIAL_ITEM)

        cancelBtn.setOnClickListener{
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Are you sure you want to exit?")
            builder.setMessage("Any photos you have taken will be lost. Are you sure you want to proceed?")
            builder.setPositiveButton("Yes") { dialogInterface: DialogInterface, i: Int ->
                val homeActivity = Intent(this, HomeActivity::class.java)
                startActivity(homeActivity)
            }

            builder.setNegativeButton("Cancel", { dialogInterface: DialogInterface, i: Int -> })
            builder.show()
        }

        uploadBtn.setOnClickListener{
            if (imageLinks.isEmpty()) {
                Toast.makeText(this, "No images to upload.", Toast.LENGTH_SHORT).show()
            } else {
                writeToDatabase(archivalItem)

                val homeActivity = Intent(this, HomeActivity::class.java)
                startActivity(homeActivity)
            }
        }

        viewFinder = findViewById(R.id.view_finder)

        //Request camera permissions
        if (allPermissionsGranted()) {
            viewFinder.post {startCamera()}
        } else {
            ActivityCompat.requestPermissions(
                this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS
            )
        }

        // Every time the provided texture view changes, recompute layout
        viewFinder.addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ ->
            updateTransform()
        }
    }

    private val executor = Executors.newSingleThreadExecutor()
    private lateinit var viewFinder: TextureView

    private fun startCamera() {

        val previewConfig = PreviewConfig.Builder().apply {
            setTargetResolution(Size(640, 480))
        }.build()

        val preview = Preview(previewConfig)

        // Every time the viewfinder is updated, recompute layout
        preview.setOnPreviewOutputUpdateListener {

            // To update the SurfaceTexture, we have to remove it and re-add it
            val parent = viewFinder.parent as ViewGroup
            parent.removeView(viewFinder)
            parent.addView(viewFinder, 0)

            viewFinder.surfaceTexture = it.surfaceTexture
            updateTransform()
        }

        val imageCaptureConfig = ImageCaptureConfig.Builder()
            .apply {
                setCaptureMode(ImageCapture.CaptureMode.MIN_LATENCY)
            }.build()

        // Build the image capture use case and attach button click listener
        val imageCapture = ImageCapture(imageCaptureConfig)
        capture_button.setOnClickListener {
            val file = File(externalMediaDirs.first(),
                "${System.currentTimeMillis()}.jpg")

            imageCapture.takePicture(file, executor,
                object : ImageCapture.OnImageSavedListener {
                    override fun onError(
                        imageCaptureError: ImageCapture.ImageCaptureError,
                        message: String,
                        exc: Throwable?
                    ) {
                        val msg = "Photo capture failed: $message"
                        Log.e("CameraXApp", msg, exc)
                        viewFinder.post {
                            Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onImageSaved(file: File) {
                        val uri = Uri.fromFile(file)
                        val ref = storageReference?.child("uploads/" + UUID.randomUUID().toString())
                        val uploadTask = ref?.putFile(uri)

                        val urlTask = uploadTask?.continueWithTask { task ->
                            if (!task.isSuccessful) {
                                task.exception?.let {
                                    throw it
                                }
                            }
                            ref?.downloadUrl
                        }?.addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val downloadUri = task.result
                                imageLinks.add(downloadUri.toString())
                            } else {
                                Log.d("Firebase Storage", "Error uploading image to cloud.")
                            }
                        }

                        val msg = "Photo capture succeeded: ${file.absolutePath}"
                        Log.d("CameraXApp", msg)
                        viewFinder.post {
                            Toast.makeText(baseContext, "Document captured", Toast.LENGTH_SHORT).show()
                        }
                    }
                })
        }

        CameraX.bindToLifecycle(this, preview, imageCapture)
    }

    private fun updateTransform() {
        val matrix = Matrix()

        // Compute the center of the view finder
        val centerX = viewFinder.width / 2f
        val centerY = viewFinder.height / 2f

        // Correct preview output to account for display rotation
        val rotationDegrees = when(viewFinder.display.rotation) {
            Surface.ROTATION_0 -> 0
            Surface.ROTATION_90 -> 90
            Surface.ROTATION_180 -> 180
            Surface.ROTATION_270 -> 270
            else -> return
        }
        matrix.postRotate(-rotationDegrees.toFloat(), centerX, centerY)

        viewFinder.setTransform(matrix)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                viewFinder.post { startCamera() }
            } else {
                Toast.makeText(this,
                    "Permissions not granted by the user.",
                    Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    /**
     * Check if all permissions specified in the manifest have been granted
     */
    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    private fun writeToDatabase(archivalItem: ArchivalItem) {
        val databaseRef = FirebaseDatabase.getInstance().getReference(username)
        val imageCount = imageLinks.size

        for (link in imageLinks) {
            val newDatabaseRef = databaseRef.push()
            val key = newDatabaseRef.key
            archivalItem.image = link
            archivalItem.id = key
            newDatabaseRef.setValue(archivalItem)
        }

        val msg = "$imageCount images uploaded."
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}
