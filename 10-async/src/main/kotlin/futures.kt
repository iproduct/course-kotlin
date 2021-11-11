package course.kotilin.async.futures


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
    preparePostAsync()
        .thenCompose { token: Token ->
            submitPostAsync(token, item)
        }
        .thenAccept { post: Post ->
            processPost(post)
        }

}

fun preparePostAsync(): Promise<Token> {
    // makes request and returns a promise that is completed later
    val promise = Promise<Token>()
    return promise
}
