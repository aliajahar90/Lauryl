package versatile.project.lauryl.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ErrorResponse {

    @SerializedName("error")
    @Expose
    var error: String? = null

    @SerializedName("error_description")
    @Expose
    var errorDescription: String? = null

}