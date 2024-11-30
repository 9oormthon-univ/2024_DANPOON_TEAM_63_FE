import com.example.youthspacefinder.Utils

fun main() {
    val districts = Utils.regionCode["서울"]?.get("districts") as Map<*, *> // String 과 Map 이 같이 있는 Any 구조여서 Type Casting 필요함
    val districtCode = districts["동대문구"]
//    println(districtCode)

    // 1차적으로 사용자가 입력한 값에서 시 도 찾기
    // 2차적으로 해당 시 도 에 해당하는 시, 군, 구 가 내용에 포함되어있는지 찾기

//    Utils.regionCode.forEach { (city, _) ->
//        println(city)
//    }

    val userInput = "서울 성동구"
    val result = filterUserInput(userInput)
    print(result)
}

private fun filterUserInput(userInput: String): Any {
    val cityInfo = Utils.regionCode.filter { (city, _) ->
        userInput.contains(city)
    }
    if(cityInfo.isEmpty()) {
        // 사용자가 시도를 입력하지 않은 경우
        return "사용자가 시도를 입력하지 않았습니다! 토스트 메세지를 띄우세요!"
    } else {
        // 사용자가 시도를 입력한 경우 → 시,군,구까지 입력했는지 확인해야 함
        val districtsInfo = cityInfo.values.first()["districts"] as Map<String, String>  // Collection<Map<String, Any>> → Map<String, Any>
        val districtInfo = districtsInfo.filter { (gu, _) ->
            userInput.contains(gu)
        }
        if(districtInfo.isEmpty()) {
            // 사용자가 시, 군, 구 까지 입력하지 않은 경우 → 시, 도 만 출력
            return cityInfo.values.first()["code"]!! // 시,도 에 해당하는 지역코드 반환
        } else {
            return districtInfo.values.first()
        }
    }
}

