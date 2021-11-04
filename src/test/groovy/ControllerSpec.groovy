import com.github.nicholasmaven.spock.demo.SpockDemoApplication
import com.github.nicholasmaven.spock.demo.exception.UserException
import com.github.nicholasmaven.spock.demo.repo.entity.User
import com.github.nicholasmaven.spock.demo.repo.mapper.UserMapper
import com.github.nicholasmaven.spock.demo.service.UserService
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification
import org.springframework.web.util.NestedServletException

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

//类名可以和文件名不一致

@AutoConfigureMockMvc
@WebMvcTest
//推荐用这种方式测试controller
@ContextConfiguration(classes = [SpockDemoApplication])
class UserControllerSpec extends Specification {
    //spock自带的spring注入，区别于真实的@Autowired
    @SpringBean
    UserService userService = Mock(UserService);

    @SpringBean
    UserMapper mapper = Mock(UserMapper);

    @Autowired
    MockMvc mockMvc;

    //测试方法名可以使用中文，无需注释
    def '正例-查询用户'() {
        when:
        User user = new User()
        user.username = "mawen-1"
        user.nickname = "nickname-1"
        user.id = 1
        userService.get(1) >> user
        then:
        String x = this.mockMvc.perform(
                get("/v1/user/list/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().response.contentAsString
        x.contains("mawen-1")
    }

    def '反例-throw exception if record not exists'() {
        given:
        userService.get(0) >> { throw new UserException("no one") }
        when:
        this.mockMvc.perform(
                get("/v1/user/list/0")
                        .contentType(MediaType.APPLICATION_JSON))
        then: '示例代码未做异常捕获，所以这里直接断言异常'
        thrown(NestedServletException)
    }
}