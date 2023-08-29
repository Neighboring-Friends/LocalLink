package com.example.nextdoorfriend.attractionCourse

data class AttractionCourse(
    val courseNm: String,
    val distance: String,
    val time: String,
    val tourCourse: Array<String>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AttractionCourse

        if (courseNm != other.courseNm) return false
        if (distance != other.distance) return false
        if (time != other.time) return false
        if (!tourCourse.contentEquals(other.tourCourse)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = courseNm.hashCode()
        result = 31 * result + distance.hashCode()
        result = 31 * result + time.hashCode()
        result = 31 * result + tourCourse.contentHashCode()
        return result
    }
}