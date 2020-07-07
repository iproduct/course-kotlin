package intro

/*
 Create a POJO with getters, setters, `equals()`, `hashCode()`, `toString()` and `copy()` in a single line:
*/
data class Customer(val name: String, val email: String, val company: String)

// Want a singleton? Create an object:
object ThisIsASingleton {
    val companyName: String = "JetBrains"
}

fun main() {
    // Or filter a list using a lambda expression:
    val positiveNumbers = listOf(0, 1, -1, 2, -2, 3, -3).filter { it > 0 }
    println(positiveNumbers);

    val customerWithCompany = listOf(
        Customer("Trayan Iliev", "office@iproduct.org", "IPT"),
        Customer("John Smith", "john@nobrainer.com", "NoBrainer"),
        Customer("Silvia Popova", "silvia@acme.com", "ACME Corp")
    ).map({"${it.name} - ${it.company}"})
    println(customerWithCompany); // => [Trayan Iliev - IPT, John Smith - NoBrainer, Silvia Popova - ACME Corp]
}
