package com.example.cameraapp

import android.os.Bundle
import android.util.Log
import android.util.Size
import android.view.Surface
import android.view.ViewGroup
import android.widget.Toast
import androidx.camera.core.*
import androidx.core.app.ActivityCompat
import androidx.lifecycle.LifecycleOwner
import kotlinx.android.synthetic.main.activity_camera_x.*
import java.io.File
import java.util.concurrent.Executors

private const val REQUEST_CODE_PERMISSIONS = 10

private val REQUIRED_PERMISSIONS = arrayOf(android.Manifest.permission.CAMERA)

class CameraxActivity : BaseActivity(), LifecycleOwner {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera_x)

        //viewfinder = view_finder

        if (allPermissionsGranted(REQUIRED_PERMISSIONS, this)) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(
                this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS
            )
        }

        view_finder.addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ ->
            updateTransform()
        }
    }

    // Add this after onCreate
    private val executor = Executors.newSingleThreadExecutor()
//    private lateinit var viewfinder: TextureView


    /**
     * : Implement CameraX operations
     */
    fun startCamera() {
        val previewConfig = PreviewConfig.Builder().apply {
            setTargetResolution(Size(1920, 1080))
        }.build()

        val preview = Preview(previewConfig)

        preview.setOnPreviewOutputUpdateListener {

            val parent = view_finder.parent as ViewGroup
            parent.removeView(view_finder)
            parent.addView(view_finder, 0)

            view_finder.surfaceTexture = it.surfaceTexture
            updateTransform()
        }


        val imageCaptureConfig = ImageCaptureConfig.Builder().apply {
            setCaptureMode(ImageCapture.CaptureMode.MIN_LATENCY)
        }.build()

        val imageCapture = ImageCapture(imageCaptureConfig)

        capture_button.setOnClickListener {
            val file = File(externalMediaDirs.first(), "${System.currentTimeMillis()}.jpg")

            imageCapture.takePicture(file, executor, object : ImageCapture.OnImageSavedListener {
                override fun onError(
                    imageCaptureError: ImageCapture.ImageCaptureError,
                    message: String,
                    cause: Throwable?
                ) {
                    val msg = "Photo capture failed: $message"
                    Log.e("CameraXApp", msg, cause)
                    view_finder.post {
                        Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onImageSaved(file: File) {
                    val msg = "Photo capture succeeded: ${file.absolutePath}"
                    Log.d("CameraXApp", msg)
                    view_finder.post {
                        Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
                    }
                }

            })
        }

        //分析
        val analyzerConfig = ImageAnalysisConfig.Builder().apply {
            setImageReaderMode(
                ImageAnalysis.ImageReaderMode.ACQUIRE_LATEST_IMAGE
            )
        }.build()
        val analyzerUseCase = ImageAnalysis(analyzerConfig).apply {
            setAnalyzer(executor, LuminosityAnalyzer())
        }

        CameraX.bindToLifecycle(this, preview, imageCapture, analyzerUseCase)
    }

    /**
     * Implement camera viewfinder transformations
     */
    fun updateTransform() {

        val matrix = android.graphics.Matrix()

        val centerX = view_finder.width / 2f
        val centerY = view_finder.height / 2f

        val roationDegrees = when (view_finder.display.rotation) {
            Surface.ROTATION_0 -> 0
            Surface.ROTATION_90 -> 90
            Surface.ROTATION_180 -> 180
            Surface.ROTATION_270 -> 270
            else -> return
        }


        matrix.postRotate(-roationDegrees.toFloat(), centerX, centerY)

        view_finder.setTransform(matrix)
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted(REQUIRED_PERMISSIONS, this)) {
                view_finder.post { startCamera() }
            } else {
                Toast.makeText(
                    this, "Permissions not granted by the user.",
                    Toast.LENGTH_SHORT
                ).show()
                finish()

            }
        }
    }

//    private fun allPermissionsGranted() = REQUST_PERMISSIONS.all {
//        ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
//    }

}
