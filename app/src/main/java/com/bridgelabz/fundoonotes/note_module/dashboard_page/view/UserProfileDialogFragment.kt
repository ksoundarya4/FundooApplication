package com.bridgelabz.fundoonotes.note_module.dashboard_page.view

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.icu.util.Calendar
import android.media.MediaScannerConnection
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import com.bridgelabz.fundoonotes.R
import com.bridgelabz.fundoonotes.user_module.registration.model.User
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class UserProfileDialogFragment : DialogFragment() {

    private val galleryAccessRequestCode = 0
    private val cameraAccessRequestCode = 1
    private val imageDirectory = "/fundooNotes"
    private val userProfilePicture by lazy {
        requireView().findViewById<ImageView>(R.id.user_profile_pic)
    }
    private val userEmail by lazy {
        requireView().findViewById<TextView>(R.id.user_email_text)
    }
    private val userFullName by lazy {
        requireView().findViewById<TextView>(R.id.user_full_name_text)
    }
    private lateinit var user: User

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user_profile, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getUserArgument()
        setUserProfilePictureClickListenere()
    }

    private fun setUserProfilePictureClickListenere() {
        userProfilePicture.setOnClickListener {
            showPictureDialog()
        }
    }

    private fun showPictureDialog() {
        val pictureDialog = AlertDialog.Builder(requireView().context)
        pictureDialog.setTitle("Select Action")

        val pictureDialogItems = arrayOf("Select photo from gallery", "capture photo from camera")

        pictureDialog.setItems(pictureDialogItems
        ) { dialog, which ->
            when (which) {
                0 -> choosePhotoFromGallery()
                1 -> takePhotoFromCamera()
            }
        }
        pictureDialog.show()
    }

    private fun choosePhotoFromGallery() {
        val galleryIntent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )

        startActivityForResult(galleryIntent, galleryAccessRequestCode)
    }

    private fun takePhotoFromCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, cameraAccessRequestCode)
    }

    private fun getUserArgument() {
        if (arguments != null) {
            try {
                user = arguments?.get(getString(R.string.authenticated_user)) as User
                val fullName = "${user.firstName} ${user.lastName}"
                userFullName.text = fullName
                userEmail.text = user.email
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
        }
    }

    override fun onResume() {
        super.onResume()

        val params: ViewGroup.LayoutParams = dialog!!.window!!.attributes
        params.width = ViewGroup.LayoutParams.MATCH_PARENT
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT
        dialog!!.window!!.attributes = params as WindowManager.LayoutParams
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == galleryAccessRequestCode) {
            if (data != null) {
                val contentURI = data.data
                try {
                    val bitmap =
                        MediaStore.Images.Media.getBitmap(
                            requireActivity().contentResolver,
                            contentURI
                        )
                    saveImage(bitmap)
                    Toast.makeText(requireContext(), "Image Saved!", Toast.LENGTH_SHORT).show()
                    userProfilePicture.setImageBitmap(bitmap)
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(requireContext(), "Failed!", Toast.LENGTH_SHORT).show()
                }
            }
        }

        if (requestCode == cameraAccessRequestCode) {
            val thumbnail = data!!.extras!!["data"] as Bitmap?
            userProfilePicture.setImageBitmap(thumbnail)
            saveImage(thumbnail!!)
            Toast.makeText(requireContext(), "Image Saved!", Toast.LENGTH_SHORT).show()
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun saveImage(myBitmap: Bitmap): String {
        val bytes = ByteArrayOutputStream()
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes)
        val wallpaperDirectory = File(
            (Environment.getExternalStorageDirectory()).toString() + imageDirectory
        )
        // have the object build the directory structure, if needed.
        Log.d("fee", wallpaperDirectory.toString())
        if (!wallpaperDirectory.exists()) {

            wallpaperDirectory.mkdirs()
        }

        try {
            Log.d("heel", wallpaperDirectory.toString())
            val f = File(
                wallpaperDirectory, ((Calendar.getInstance()
                    .timeInMillis).toString() + ".jpg")
            )
            f.createNewFile()
            val fo = FileOutputStream(f)
            fo.write(bytes.toByteArray())
            MediaScannerConnection.scanFile(
                requireContext(),
                arrayOf(f.getPath()),
                arrayOf("image/jpeg"), null
            )
            fo.close()
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath())

            return f.absolutePath
        } catch (e1: IOException) {
            e1.printStackTrace()
        }
        return ""
    }
}