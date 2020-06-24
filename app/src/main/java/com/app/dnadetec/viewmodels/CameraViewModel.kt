package com.app.dnadetec.viewmodels

import android.app.Activity
import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.dnadetec.responses.Result
import com.github.dhaval2404.imagepicker.ImagePicker
import java.io.File
import javax.inject.Inject


class CameraViewModel @Inject constructor(private val context: Context) : ViewModel() {

    val selectedImage = MutableLiveData<Result<Uri>>()

    fun openGallery(activity:Activity){

        ImagePicker.with(activity)
            .galleryOnly()
            .start { resultCode, data ->

                when (resultCode) {
                    Activity.RESULT_OK -> {
                        //Image Uri will not be null for RESULT_OK
                        val fileUri = data?.data

                        if (fileUri != null){
                            selectedImage.value = Result.success(fileUri)
                        }else{
                            selectedImage.value = Result.error("Cannot get selected image.")
                        }

                        //You can get File object from intent
                        val file: File? = ImagePicker.getFile(data)
                        //You can also get File Path from intent
                        val filePath: String? = ImagePicker.getFilePath(data)
                    }
                    ImagePicker.RESULT_ERROR -> {
                        selectedImage.value = Result.error(ImagePicker.getError(data))
                    }
                    else -> {
                    }
                }

            }

    }

    fun openCamera(activity:Activity){

        ImagePicker.with(activity)
            .cameraOnly()
            .start { resultCode, data ->

                when (resultCode) {
                    Activity.RESULT_OK -> {
                        //Image Uri will not be null for RESULT_OK
                        val fileUri = data?.data

                        if (fileUri != null){
                            selectedImage.value = Result.success(fileUri)
                        }else{
                            selectedImage.value = Result.error("Cannot get selected image.")
                        }

                        //You can get File object from intent
                        val file: File? = ImagePicker.getFile(data)
                        //You can also get File Path from intent
                        val filePath: String? = ImagePicker.getFilePath(data)
                    }
                    ImagePicker.RESULT_ERROR -> {
                        selectedImage.value = Result.error(ImagePicker.getError(data))
                    }
                    else -> {
                        selectedImage.value = Result.error("Task Cancelled")
                    }
                }

            }

    }

}