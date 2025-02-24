package ninja.bryansills.loudping.core.model.test

import app.cash.turbine.TurbineContext
import app.cash.turbine.turbineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.test.runTest

interface TurbineTestScope : TurbineContext {
    val backgroundScope: CoroutineScope
}

fun runTestTurbine(validate: suspend TurbineTestScope.() -> Unit) = runTest {
    turbineScope {
        val turbineTestScope = object : TurbineTestScope, TurbineContext by this {
            override val backgroundScope: CoroutineScope = this@runTest.backgroundScope
        }

        turbineTestScope.validate()
    }
}
