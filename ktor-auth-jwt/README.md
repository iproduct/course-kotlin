# Ktor Authentication using Json Web Token

A simple example how to include JWT in Ktor applications. 




[Module.kt](\src\main\kotlin\ktor\example\auth\jwt\sample\Module.kt) contains the server setup, including the `Routing`.

There is a `login` route, which responds with a JWT. 

The returned token should be used to access the `secret-roles` route and `optional-auth`. 

The JWT configuration can be found in [JwtConfig.kt](\src\main\kotlin\ktor\example\auth\jwt\sample\JwtConfig.kt).

Tests can be found in [AuthTest.kt](\src\test\kotlin\ktor\example\auth\jwt\sample\AuthTest.kt)
