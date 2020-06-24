package com.app.dnadetec.models

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.gson.annotations.SerializedName

data class UserProfile(
    @SerializedName("id") var id: String
    ,@SerializedName("Name") var Name: String
    ,@SerializedName("User") var User: String
    ,@SerializedName("Password") var Password: String
    ,@SerializedName("Avatar") var Avatar: String
){


    companion object{
        @JvmStatic
        @BindingAdapter("profileImage")
        fun loadImage(view: ImageView, imageUrl: String?) {
            Glide.with(view.context)
                .load(imageUrl).apply(RequestOptions().circleCrop())
                .into(view)
        }

    }

}
