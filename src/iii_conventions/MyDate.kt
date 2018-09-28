package iii_conventions

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int)

operator fun MyDate.compareTo(other: MyDate): Int {
    var tmp = year.compareTo(other.year)
    if (tmp != 0) {
	return tmp
    }
    tmp = month.compareTo(other.month)
    if (tmp != 0) {
	return tmp
    }

    return dayOfMonth.compareTo(other.dayOfMonth)
}

operator fun MyDate.rangeTo(other: MyDate): DateRange {
    return DateRange(this, other)
}

enum class TimeInterval {
    DAY,
    WEEK,
    YEAR
}

class MultiTimeInterval(val ti: TimeInterval, val mult: Int)

operator fun TimeInterval.times(n: Int): MultiTimeInterval {
    return MultiTimeInterval(this, n)
}

operator fun MyDate.plus(ti: TimeInterval): MyDate {
    return this.addTimeIntervals(ti, 1)
}

operator fun MyDate.plus(mti: MultiTimeInterval): MyDate {
    return this.addTimeIntervals(mti.ti, mti.mult)
}

class DateRange(val start: MyDate, val endInclusive: MyDate): Iterable<MyDate> {
    override operator fun iterator(): Iterator<MyDate> {
	return object : Iterator<MyDate> {

	    var current: MyDate = start
	    
	    override operator fun hasNext(): Boolean {
		return current <= endInclusive
	    }

	    override operator fun next(): MyDate {
		val result = current
		current = current.nextDay()
		return result
	    }
	}
    }
}

operator fun DateRange.contains(point: MyDate): Boolean {
    return start <= point && point <= endInclusive
}
