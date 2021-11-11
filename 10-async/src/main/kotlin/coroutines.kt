package course.kotilin.async.coroutines
import course.kotilin.async.preparePost
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.suspendCoroutine
class Item
class Token
class Post

fun submitPost(token: Token, item: Item): Post {
    return Post()
}
fun submitPostAsync(token: Token, item: Item) {
}

fun processPost(post: Post) {}

fun postItem(item: Item) {
    runBlocking {
    launch {
        val token = preparePost()
        val post = submitPost(token, item)
        processPost(post)
    }
}

suspend fun preparePost(): Token {
    // makes a request and suspends the coroutine
    return suspendCoroutine { /* ... */ }
}
