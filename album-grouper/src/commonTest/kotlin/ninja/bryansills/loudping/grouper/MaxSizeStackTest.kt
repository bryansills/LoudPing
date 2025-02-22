package ninja.bryansills.loudping.grouper

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class MaxSizeStackTest {
    @Test
    fun `basic`() {
        val stack = MaxSizeStack<String>(maxSize = 3)
        val item = "testy mctest"
        stack.push(item)
        val actual = stack.pop()
        assertEquals(item, actual)
    }

    @Test
    fun `must have size greater than 0`() {
        assertFails { MaxSizeStack<Int>(maxSize = -1) }
    }

    @Test
    fun `can add multiple`() {
        val stack = MaxSizeStack<String>(maxSize = 3)
        val items = listOf("bob", "linda", "gene")

        items.forEach { stack.push(it) }

        assertEquals(items, stack.values)
    }

    @Test
    fun `drops oldest item when adding more than can fit`() {
        val stack = MaxSizeStack<String>(maxSize = 3)

        val dropped = "tina"
        val items = listOf("bob", "linda", "gene")

        stack.push(dropped)
        items.forEach { stack.push(it) }

        assertFalse { stack.values.contains(dropped) }
    }

    @Test
    fun `can have initial items`() {
        val initialItems = listOf("bob", "linda", "gene")
        val stack = MaxSizeStack(initialValues = initialItems, maxSize = 3)

        assertEquals(initialItems, stack.values)
    }

    @Test
    fun `chops initial items if bigger than max size, keeping the last ones`() {
        val chopped = listOf("bob", "linda")
        val kept = listOf("gene", "tina", "louise")
        val stack = MaxSizeStack(initialValues = chopped + kept, maxSize = 3)

        chopped.forEach { missing -> assertFalse { stack.values.contains(missing) } }
        kept.forEach { stillThere -> assertTrue { stack.values.contains(stillThere) } }
    }
}
