class Foo
class Bar

val foo: Foo
    inline get() = Foo()

var _bar: Bar = Bar()
var bar: Bar
    get() = _bar
    inline set(v) {
        _bar = v
    }

inline var bar2: Bar
    get() = _bar
    set(v) {
        _bar = v
    }

fun main() {

}
