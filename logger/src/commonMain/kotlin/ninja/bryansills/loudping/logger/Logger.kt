package ninja.bryansills.loudping.logger

interface Logger {
    fun e(message: String, ex: Throwable? = null)
}

fun Logger.e(ex: Throwable) {
    this.e(message = ex.message!!, ex = ex)
}
