package course.kotilin.async.callbacks

import course.kotilin.async.preparePost

class Item
class Token
class Post

fun submitPost(token: Token, item: Item): Post {
    return Post()
}

fun processPost(post: Post) {}


fun postItem(item: Item) {
    preparePostAsync { token ->
        submitPostAsync(token, item) { post: Post ->
            processPost(post)
        }
    }
}

fun submitPostAsync(token: Token, item: Item, any: Any) {
}

fun preparePostAsync(callback: (Token) -> Unit) {
    // make request and return immediately
    // arrange callback to be invoked later
}
