import com.github.nicholasmaven.spock.demo.exception.UserException
import com.github.nicholasmaven.spock.demo.repo.entity.User
import com.github.nicholasmaven.spock.demo.repo.mapper.UserMapper
import com.github.nicholasmaven.spock.demo.service.UserService
import spock.lang.Specification

class UserServiceSpec extends Specification {
    //依赖mock
    def mapper = Mock(UserMapper)
    def userService = new UserService(mapper)

    def '正例-get'() {
        when:
        def user = new User()
        user.username = 'mawen-1'
        user.nickname = 'nickname-1'
        user.id = 1
        mapper.selectByPrimaryKey(1) >> user //mock返回，不实际执行方法
        user = userService.get(1)
        then:
        with(user) {
            id == 1
            username == 'mawen-1'
            nickname == 'nickname-1'
        }
    }

    def '反例-throw exception because mapper throw exception'() {
        when:
        mapper.selectByPrimaryKey(1) >> null
        userService.get(1)
        then:
        thrown(UserException)
    }

    def '正例-getBunch with specified ids'() {
        given:
        def a = [1, 2]
        when:
        def user1 = new User()
        user1.username = 'mawen-1'
        user1.nickname = 'nickname-1'
        user1.id = 1
        def user2 = new User()
        user2.username = 'mawen-1'
        user2.nickname = 'nickname-1'
        user2.id = 1
        mapper.selectByPrimaryKeys(a) >> [user1, user2]

        then:
        userService.getBunch([1, 2]).size() == 2
    }

    def '反例-get none because record not exist'() {
        when:
        mapper.selectByPrimaryKeys([1, 2]) >> []
        then:
        userService.getBunch([1, 2]).isEmpty()

        expect:
        userService.getBunch(a).isEmpty()
        where:
        a    | _
        null | _
        []   | _
    }

    def '正例-valid'() {
        expect:
        userService.valid(1, 'mawen-1', 'nickname-m') //返回值为true
    }

    def '反例-not valid because param invalid'() {
        expect: '参数非法返回false'
        !userService.valid(a, b, c)
        where: '参数化测试'
        a    | b                          | c
        null | null                       | null
        1    | null                       | null
        2    | 'karen'                    | null
        3    | ''                         | null
        4    | 'kaka'                     | null
        5    | 'karen'                    | ''
        6    | 'this is a very long name' | 'nickname'
        7    | 'normal'                   | 'this is a very long nickname'

    }

    def getUser(id, username, nickname) {
        User user = new User()
        user.id = id
        user.nickname = nickname
        user.username = username
        return user;
    }

    def '正例-update success'() {
        given:
        def x = new User()
        x.username = 'mawen-1'
        x.nickname = 'nickname-1'
        x.id = 1
        when: 'mock方法返回'
        userService.update(x)
        then: '方法调用1次'
        1 * mapper.updateByPrimaryKey(x)
    }

    def '反例-update failed because param is null'() {
        when:
        userService.update(null)
        then:
        0 * mapper.updateByPrimaryKey(_)
    }

}
