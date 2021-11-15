package course.kotilin.async.coroutines

import course.kotilin.async.preparePost
import javafx.application.Application.launch
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.coroutineContext
import kotlin.coroutines.suspendCoroutine

class Item
class Token
class Post

suspend fun preparePost(item: Item): Token {
    // makes a request and suspends the coroutine
    return suspendCoroutine {
        runBlocking {
            delay(1000)
            println("Post prepared.")
            it.resumeWith(Result.success(Token()))
        }
    }
}

fun submitPost(token: Token, item: Item): Post {
    println("Post submitted.")
    return Post()
}

fun processPost(post: Post) {
    println("Post processed.")
}

suspend fun postItem(item: Item) {
    coroutineScope {
        launch {
            val token = preparePost(item)
            val post = submitPost(token, item)
            processPost(post)
        }
    }
}

fun main() = runBlocking {
    postItem(Item())
    println("Demo complete.")
}

