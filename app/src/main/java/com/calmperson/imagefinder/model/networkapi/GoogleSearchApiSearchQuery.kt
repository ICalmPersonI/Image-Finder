package com.calmperson.imagefinder.model.networkapi

import com.calmperson.imagefinder.R

object GoogleSearchApiSearchQuery {

    interface Query {
        val stringResourceId: Int
        val value: String?
    }

    enum class ImageSize(override val stringResourceId: Int, override val value: String?) : Query {
        ANY_SIZE(R.string.any_size,null),
        ICON(R.string.icon, "icon"),
        SMALL(R.string.small, "small"),
        MEDIUM(R.string.medium,"medium"),
        HUGE(R.string.huge,"huge"),
        LARGE(R.string.large, "large"),
        XLARGE(R.string.xlarge,"xlarge"),
        XXLARGE(R.string.xxlarge,"xxlarge")
    }

    enum class DominantColor(override val stringResourceId: Int, override val value: String?) : Query {
        ANY_COLOR(R.string.any_color,null),
        BLACK(R.string.black, "black"),
        BLUE(R.string.blue, "blue"),
        BROWN(R.string.brown, "brown"),
        GRAY(R.string.gray, "gray"),
        GREEN(R.string.green,"green"),
        ORANGE(R.string.orange, "orange"),
        PINK(R.string.pink, "pink"),
        PURPLE(R.string.purple, "purple"),
        RED(R.string.red, "red"),
        TEAL(R.string.teal, "teal"),
        WHITE(R.string.white, "white"),
        YELLOW(R.string.yellow, "yellow")
    }

    enum class ImageType(override val stringResourceId: Int, override val value: String?) : Query {
        ANY_TYPE(R.string.any_type, null),
        CLIPART(R.string.clipart, "clipart"),
        FACE(R.string.face, "face"),
        LINEART(R.string.lineart, "lineart"),
        STOCK(R.string.stock, "stock"),
        PHOTO(R.string.photo, "photo")
    }

    enum class Period(override val stringResourceId: Int, override val value: String?) : Query {
        ANY_TIME(R.string.any_time, null),
        PAST_24_HOURS(R.string.past_day, "d1"),
        PAST_WEEK(R.string.past_week, "w1"),
        PAST_MONTH(R.string.past_month, "m1"),
        PAST_YEAR(R.string.past_year, "y1")
    }

}