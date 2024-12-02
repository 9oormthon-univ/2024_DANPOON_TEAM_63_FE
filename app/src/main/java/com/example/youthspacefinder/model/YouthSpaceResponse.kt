import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml
import kotlinx.parcelize.Parcelize

@Xml(name = "spacesInfo")
data class SpacesInfoResponse(
    @PropertyElement(name = "totalCnt")
    val totalCnt: Int,

    @PropertyElement(name = "pageIndex")
    val pageIndex: Int,

    @Element(name = "space")
    val youthSpaces: List<YouthSpace>
)

@Xml(name = "space")
data class YouthSpace(
    @PropertyElement(name = "rownum")
    val rownum: Int,

    @PropertyElement(name = "spcId")
    val spcId: String,

    @PropertyElement(name = "spcName")
    val spcName: String,

    @PropertyElement(name = "areaCpvn")
    val areaCpvn: String,

    @PropertyElement(name = "areaSggn")
    val areaSggn: String,

    @PropertyElement(name = "address")
    val address: String,

    @PropertyElement(name = "spcTime")
    val spcTime: String,

    @PropertyElement(name = "operOrgan")
    val operOrgan: String,

    @PropertyElement(name = "homepage")
    val homepage: String,

    @PropertyElement(name = "telNo")
    val telNo: String,

    @PropertyElement(name = "officeHours")
    val officeHours: String,

    @PropertyElement(name = "openDate")
    val openDate: String,

    @PropertyElement(name = "applyTarget")
    val applyTarget: String,

    @PropertyElement(name = "spcType")
    val spcType: String,

    @PropertyElement(name = "majorForm")
    val majorForm: String,

    @PropertyElement(name = "spcCost")
    val spcCost: String,

    @PropertyElement(name = "addFacilCost")
    val addFacilCost: String,

    @PropertyElement(name = "foodYn")
    val foodYn: String
)

@Parcelize
data class AmenitiesResponse(
    @SerializedName("place_name") val amenityName: String,
    @SerializedName("phone") val phoneNumber: String,
    @SerializedName("place_url") val placeUrl: String,
    @SerializedName("distance") val distance: String,
    @SerializedName("x") val positionX: String,
    @SerializedName("y") val positionY: String
): Parcelable

data class PositionResponse(
    @SerializedName("x") val positionX: String,
    @SerializedName("y") val positionY: String
)

data class RegisterUserInfo(
    val username: String,
    val nickname: String,
    val password: String,
    val email: String
)
