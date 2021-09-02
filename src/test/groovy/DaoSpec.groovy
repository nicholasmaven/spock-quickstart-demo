import com.github.nicholasmaven.spock.demo.SpockDemoApplication
import com.github.nicholasmaven.spock.demo.repo.entity.User
import com.github.nicholasmaven.spock.demo.repo.mapper.UserMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.dao.DuplicateKeyException
import org.springframework.jdbc.BadSqlGrammarException
import spock.lang.Shared
import spock.lang.Specification

//Dao层集成测试
//数据库中已提前插了3条数据, id为[1,2,3]
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = [SpockDemoApplication])
class UserMapperIntegrationTestSpec extends Specification {
    @Autowired
    UserMapper userMapper

    @Shared
    User example;

    def 'setupSpec'() {
        example = new User()
        example.id = 1
        example.username = 'mawen-1'
        example.nickname = 'nickname-1'
    }

    def '反例-insert'() {
        when: '插入重复数据'
        userMapper.insert(example)
        then: '主键冲突'
        thrown(DuplicateKeyException)

        when:'全部属性为null'
        def a = new User()
        then: '不插入但不报错'
        notThrown(Exception)

        when:
        userMapper.insert(null)
        then: 'null不插入'
        thrown(BadSqlGrammarException)
    }

    def '正例-select by primary key'() {
        when:
        def a = userMapper.selectByPrimaryKey(1)
        then:
        with(a) {//对实体进行断言
            a.id == 1
            a.nickname == 'nickname-1'
            a.username == 'mawen-1'
        }
    }

    def '反例-select by primary key'() {
        expect: '查询不存存在的数据返回null'
        !userMapper.selectByPrimaryKey(4)
    }

    def '正例-select by primary keys'() {
        given:
        def params = [1, 2, 3]
        expect:
        userMapper.selectByPrimaryKeys(params).size() == 3
    }

    def '反例-select by primary keys'() {
        expect: '参数为空查询全部'
        userMapper.selectByPrimaryKeys([]).size() == 3

        when: 'id列表为null'
        then: '查询全部'
        userMapper.selectByPrimaryKeys(null).size() == 3
    }

    def '正例-delete by primary key'() {
        expect:
        userMapper.deleteByPrimaryKey(1) == 1
        cleanup: '恢复数据'
        userMapper.insert(example)
    }

    //删除一条不存在的记录
    def '反例-delete by primary key'() {
        expect:
        userMapper.deleteByPrimaryKey(4) == 0
    }

    def '正例-update'() {
        given:
        def a = new User()
        a.nickname = 'nickname-1-modified'
        a.id = 1
        expect:
        userMapper.updateByPrimaryKey(a) == 1
        cleanup: '恢复数据'
        userMapper.updateByPrimaryKey(example)
    }

    def '反例-update'() {
        given:
        def a = new User()
        a.nickname = 'nickname-1-modified'
        a.id = 0
        expect:
        userMapper.updateByPrimaryKey(a) == 0

        when:
        a.nickname = null
        a.id = null
        userMapper.updateByPrimaryKey(a)
        then: 'user属性空则不更新'
        thrown(BadSqlGrammarException)

        when:
        userMapper.updateByPrimaryKey(null)
        then: 'user为null不更新'
        thrown(BadSqlGrammarException)
    }
}
