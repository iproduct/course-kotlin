package ktor.example.auth.jwt

import com.google.gson.Gson
import io.ktor.auth.UserPasswordCredential
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.TestApplicationEngine
import io.ktor.server.testing.TestApplicationRequest
import io.ktor.server.testing.setBody
import io.ktor.server.testing.withTestApplication
import org.amshove.kluent.*
import org.junit.jupiter.api.Test

class AuthTest {

    @Test
    fun `login request should return a token`() = withServer {
        val req = handleRequest {
            method = HttpMethod.Post
            uri = "/login"
            addHeader("Content-Type", "application/json")
            setBody(
                Gson().toJson(UserPasswordCredential("user", "password"))
            )
        }

        req.requestHandled shouldBe true
        req.response.status() shouldBeEqualTo HttpStatusCode.OK
        req.response.content.shouldNotBeNullOrBlank().length shouldBeGreaterThan 6
    }

    @Test fun `request to 'secret-roles' without token should fail with 401-Unauthorized`() = withServer {
        val req = handleRequest {
            uri = "/secret-roles"
        }
        req.requestHandled shouldBe true
        req.response.status() shouldBeEqualTo HttpStatusCode.Unauthorized
    }

    @Test fun `request to 'secret-roles' with valid token should return roles`() = withServer {
        val req = handleRequest {
            uri = "/secret-roles"
            addJwtHeader()
        }
        req.requestHandled shouldBe true
        req.response.let {
            it.status() shouldBeEqualTo HttpStatusCode.OK
            it.content.shouldNotBeNullOrBlank()
        }
    }

    @Test fun `optional-auth route should return 'auth is optional' without token`() = withServer {
        val req = handleRequest {
            uri = "/optional-auth"
        }
        req.let {
            it.requestHandled.shouldBeTrue()
            it.response.status() shouldBeEqualTo HttpStatusCode.OK
            it.response.content.shouldNotBeNullOrBlank() shouldStartWith "auth is optional here"
        }
    }

    @Test fun `optional-auth route should return 'authenticated as $User' with valid token`() = withServer {
        val req = handleRequest {
            uri = "/optional-auth"
            addJwtHeader()
        }
        req.let {
            it.requestHandled.shouldBeTrue()
            it.response.status() shouldBeEqualTo HttpStatusCode.OK
            it.response.content.shouldNotBeNullOrBlank() shouldStartWith "authenticated"
        }
    }

    private fun TestApplicationRequest.addJwtHeader() = addHeader("Authorization", "Bearer ${getToken()}")

    private fun getToken() =
        JwtConfig.makeToken(sampleUser)

    private fun withServer(block: TestApplicationEngine.() -> Unit) {
        withTestApplication({ module() }, block)
    }

}
