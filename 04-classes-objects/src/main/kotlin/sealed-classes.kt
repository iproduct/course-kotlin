package course.kotlin.sealed

import java.io.File
import javax.sql.DataSource

sealed interface Error // has implementations only in same package and module

sealed class IOError(): Error

class FileReadError(val file: File): IOError()

class DatabaseError(val source: DataSource): IOError()

object RuntimeError : Error

sealed class IOError2 {
    constructor() { /*...*/ } // protected by default
    private constructor(description: String): this() { /*...*/ } // private is OK
    // public constructor(code: Int): this() {} // Error: public and internal are not allowed
}

// indirect inheritance
sealed interface Error3 // has implementations only in same package and module
sealed class IOError3(): Error3 // extended only in same package and module
open class CustomError(): Error3 // can be extended wherever it's visible

// when expression
fun log(e: Error) = when(e) {
    is FileReadError -> { println("Error while reading file ${e.file}") }
    is DatabaseError -> { println("Error while reading from database ${e.source}") }
    RuntimeError ->  { println("Runtime error") }
    // the `else` clause is not required because all the cases are covered
}
