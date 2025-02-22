package ninja.bryansills.loudping.grouper

class MaxSizeStack<Element>(
    initialValues: List<Element> = listOf(),
    var maxSize: Int
) {
    init {
        require(maxSize > 0) { "Don't try me" }
    }

    private val data: ArrayDeque<Element> = if (initialValues.size <= maxSize) {
        ArrayDeque(initialValues)
    } else {
        ArrayDeque(initialValues.subList(initialValues.size - maxSize, initialValues.size))
    }

    val values: List<Element>
        get() = data.toList()

    fun push(newElement: Element) {
        data.addLast(newElement)

        if (data.size > maxSize) {
            data.removeFirst()
        }
    }

    fun pop(): Element? {
        return data.removeLastOrNull()
    }

    fun clear() {
        data.clear()
    }
}
