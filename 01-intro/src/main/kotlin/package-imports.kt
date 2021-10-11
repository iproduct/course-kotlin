package course.kotlin.other
import course.kotlin.Message // Message is now accessible without qualification
import course.kotlin.* // everything in 'course.kotlin' becomes accessible
import course.kotlin.Message2 // Message is accessible
import course.kotlin.Message as MyMessage // myMessage stands for 'course.kotlin.Message'

fun main() {
    val m = Message()
    val m2 = Message2()
    val myM = MyMessage()
}
