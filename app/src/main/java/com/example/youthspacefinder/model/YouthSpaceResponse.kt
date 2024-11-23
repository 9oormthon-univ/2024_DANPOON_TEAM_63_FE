import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

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
