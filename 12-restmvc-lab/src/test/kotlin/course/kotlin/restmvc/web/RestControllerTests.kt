package course.kotlin.restmvc.web

import course.kotlin.restmvc.Application
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = [Application::class])
@ActiveProfiles("test")
@AutoConfigureMockMvc
class RestControllerTests(@Autowired val mockMvc: MockMvc) {


}
