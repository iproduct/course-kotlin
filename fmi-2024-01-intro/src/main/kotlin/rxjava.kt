import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    println("Staring demo")
    Flowable
        .fromCallable {
            Thread.sleep(2000) //  imitate expensive computation
            "Done"
        }
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.single())
        .subscribe(::println, Throwable::printStackTrace)
    delay(4000)
    println("Demo finished.")
}

