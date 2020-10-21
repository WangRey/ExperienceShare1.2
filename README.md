## 项目内容：稍微理了下spring的IOC容器发展，在git的版本历史里可以看到IOC思想的不同实现方法。


![image](https://raw.githubusercontent.com/WangRey/ExperienceShare_IOC/master/src/main/resources/img/%7B9E33946B-B1CD-4DDB-9189-D5BAB2B6212E%7D.png.jpg)


## 一、spring介绍：

* 概念:
  1. 是一个轻量级的控制反转IOC和切面编码AOP的容器框架。
  2. 它是为了解决企业应用开发的复杂性而创建的。
  3. 在spring的发展过程中，离它的初心越来越远。

* 雏形：

  1. 最老的interface21框架是雏形。

* Spring作者：

  1. Rod Johnson

* 理念：

  1. IOC使开发更简单，整合一些技术。如SSH和SSM。

* 特点：

  1. 很强的向后兼容性。

  2. 大杂烩，在SSH和和SSM发展中不断地往spring中加入别的东西。

* Spring优点：

  1. 开源
  2. 轻量级（体积很小），非入侵式的
  3. 核心：控制反转IOC，面向切面AOP
  4. 由于有ＡＯＰ所以支持事务的支持，对框架的整合支持。

* Spring弊端：

  1. 发展太久，违背了当初的理念，也就是配置太多了，多到记不下来的程度。

## 二、IOC 的思想:

### (一)理解控制与反转：

Control：谁来控制对象的创建，传统应用程序的对象是由程序本身控制创建的。而使用spring后，对象是由spring的bean容器来创建。

Inversion：程序本身不创建对象， 而变成被动的接收对象。

inversion of control：把创建对象的过程交给spring管理。

### (二)假设编写的类有：



1. UserDao实现类

   ```java
   public class UserDaoByOracle {
       public void findUserInfoInDB() {
           System.out.println("通过Oracle获取User信息");
       }
   }
   ```

   ```java
   public class UserDaoBySqlserver {
       public void findUserInfoInDB() {
           System.out.println("在SQL server数据库中获取了User的信息");
       }
   }
   ```

2. UserServicel业务类

   ```java
   public class UserServer {
   
       public void getUserInfo(){
           
           //UserDaoBySqlserver u = new UserDaoBySqlserver();
           //修改为：
           UserDaoByOracle u = new UserDaoByOracle();
           u.findUserInfoInDB();
       }
   }
   
   ```

### (三)IOC的思想产生原因：

**1. 没有IOC的时候** ：

​	我们是在ServiceImp层里面去new，去主动选择对应的DaoImp类，控制权在程序员，每次更新DaoImp的类型，比如从UserDaoByOracle更新UserDaoBySqlserver的类；那么ServiceImp都要去改变new出来的DapImp类，即从new  UserDaoByOracle换成new  UserDaoBySqlserver的类。

​	虽然现在看来改动一句UserDaoByOracle u = new UserDaoByOracle()很容易，但是项目很大的时候，各个地方都有可能需要去改动，非常麻烦，难以维护。

**2. 加入IOC思想（最简单的，在ServiceImp写setXXX方法）：**

```java
public class UserServiceImp implements UserService {
    UserDao userDao;
//  UserDao userDao = new UserDaoByOracleImp();
//  UserDao userDao = new UserDaoBySqlserverImp();

    //利用set方式进行动态注入依赖类
    public void setUserDao(UserDao userDao){
        this.userDao = userDao;
    }

    public void getUserServer() {
        userDao.getUser();
    }
}
```

   增加一个 setUserDao方法后，ServiceImp层的代码里面里面不再去new，而是从调用setUserDao的地方被动地接受传入的DaoImp类型。主动权在调用ServieImp的setUserDao方法这里。这样每次改变DaoImp的类型，切换mysql还是Oracle，就不用改动ServiceImp的代码了，直接在调用ServieImp的setUserDao方法这里，给ServiceImp传入不同的DaoImp类型就好了。

尽量不要去改动不应该改的代码！面向对象的七个     模式

​    以上的改动所表达的意思就是IOC的原型。

## 三、bean的引入：

### (一)bean的概念：

bean 定义包含称为**配置元数据**的信息。

### (二)实例化bean的手段：

#### **1.用无参数的构造方法注册**

注：也叫  构造器实例化（这个最常用，后续默认此方法）

```java
	1.在配置文件中配置如下
		<?xml version="1.0" encoding="UTF-8"?>
		<beans xmlns="http://www.springframework.org/schema/beans"
		    
			<!-- 关键部分 -->
		    <bean id="bean1" class="Educoder.Bean1"></bean>
		
		</beans>
	这样我们就创建了一个id为user的Bean，之后就可以在项目中任意位置通过容器的getBean方法进行访问User类。
	
	<bean class="org.sang.User" id="user"/>
	
	2.定义要获取的bean1类
	public class Bean1 {
		public void print(){
			System.out.println("Bean1......");
		}
	}
	
	3.获取bean1
	public void textUser(){
		 //1.获取spring文件
		 ApplicationContext context = new ClassPathXmlApplicationContext("Bean.xml");
		 //2.由配置文件返回对象
		 Bean1 b = (Bean1)context.getBean("bean1");
		 System.out.println(b);
		 b.print();
}
```

#### 2.**使用静态工厂创建**

```java
	1.配置
	<bean id="bean2"   class="com.spring.demo.Bean2Factory"   factory-method="getBean2"></bean>
	
	2.定义要获取的bean2类
	public class Bean2 {
		public void print(){
			System.out.println("Bean2......");
		}
	}
	
	3.定义静态工厂类
	public class Bean2Factory {
		public   static Bean2 getBean2(){
			return new Bean2();
		}
	}
	
	4.取bean2
	public void textUser(){
		//1.获取spring文件
		ApplicationContext context = new ClassPathXmlApplicationContext("Bean.xml");
		 //2.由配置文件返回对象
		 Bean3 b = (Bean2)context.getBean("bean2");
		 System.out.println(b);
		 b.print();
	}
	
//还是bean节点， class 不再直接指向待实例化的bean2，而是指向静态工厂类-Bean2Factory，只是多了一个factory-method属性，该属性指明该类中的静态工厂方法名为"getBean2"，这样Spring框架就知道调用哪个方法来获取bean2的实例了

```

#### 3.使用实例工厂创建

```java

	1.配置
	<bean id="bean3  factory" class="com.spring.demo.Bean3Factory"></bean>
	<bean id="bean3" factory-bean="bean3factory" factory-method="getBean3"></bean>
	
	
	2.定义要获取的bean3类
	public class Bean3 {
		public void print(){
			System.out.println("Bean3......");
		}
	}
	
	3.定义非静态工厂类
	public class Bean3Factory {
		public 　　 Bean3 getBean3(){
			return new Bean3();
		}
	}
	
	4.取bean3
	public void textUser(){
	/1.获取spring文件
			 ApplicationContext context = new ClassPathXmlApplicationContext("Bean.xml");
			 //2.由配置文件返回对象
			 Bean3 b = (Bean3)context.getBean("bean3");
			 System.out.println(b);
			 b.print();
	}
	
//静态工厂类与非静态工厂类的区别是，前者不需要创建对象，直接可以调用静态方法创建bean；后者则要先创建对象，然后再通过对象调用其方法创建bean

```

### (三)bean的生命周期：

原型模式：

1. bean类的创建在调用.getBean（id）的时候，即创建了。

单例模式

1. 当IOC容器被加载时，所有的Bean都被实例化了。

### (四)Bean对象的作用域：

```java
<bean id="bean2"   class="com.spring.demo.Bean2Factory" scope = "singleton"></bean>
<bean id="bean2"   class="com.spring.demo.Bean2Factory" scope = "prototype"></bean>
```

scope属性值:

| scope属性可取值 |                             描述                             |
| :-------------: | :----------------------------------------------------------: |
|    singleton    | 单例模式，每次从容器中取出的Bean对象都是同一个。spring默认选择。 |
|    prototype    |   原型模式，每次从容器中取出的Bean对象，都会重新new一个。    |
|     request     |                             ...                              |
|     session     |                             ...                              |
|   application   |                             ...                              |

## 四、显式装配：

| 概念 |                解释                |
| :--: | :--------------------------------: |
| 依赖 |      bean对象的创建依赖于容器      |
| 注入 | bean对象中的所有属性，由容器来注入 |

### (一)IOC的实现-通过set方法完成依赖注入

**xml注册bean，property标签完成依赖注入。底层还是调用的Set方法。**

```java
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans 
           http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="userDaoByOracleImp" class="org.experience.share.dao.UserDaoByOracleImp"></bean>
    <bean id="userDaoBySqlserverImp" class="org.experience.share.dao.UserDaoBySqlserverImp"></bean>

    <bean id="userServe" class="org.experience.share.serve.UserServe">
        <property name="userDao" ref="userDaoByOracleImp"/>
        //<property name="brand" value=" 红旗 CA72"/>
    </bean>

</beans>
```

**bean标签的属性**

| 属性  |                             作用                             |
| :---: | :----------------------------------------------------------: |
|  id   | 唯一识别该bean的名称。比如使用构造器实例化该bean时，构造器通过该id的值去容器中找这个bean。使用注解@bean（[name= ]），自动生成bean的时候，该bean的id默认为该类的名称首字母小写。 |
| class |      该bean对应的类的实际绝对路径，无需带上.java后缀。       |

**property标签的属性**

| 属性  |                             作用                             |
| :---: | :----------------------------------------------------------: |
| name  |                  该bean对象中的属性的名称。                  |
|  ref  | 该bean中的某个属性是一个类，则装配的时候，需要选择一个已经成为bean的类进行装入。 |
| value | 该bean中的某个属性是一个基本类型，则装配的时候，指定相应的值。 |

**通过setXXX方法完成依赖注入**

```java
	private UserDao userDao;

    public void setUserDao (UserDao userDao) {
        this.userDao = userDao;
    }

```

```xml
    <bean id="userDaoByOracleImp" class="org.experience.share.dao.UserDaoByOracleImp"></bean>
    <bean id="userDaoBySqlserverImp" class="org.experience.share.dao.UserDaoBySqlserverImp"></bean>

    <bean id="userServe" class="org.experience.share.serve.UserServe">
<!--        <property name="userDao" ref="userDaoBySqlserverImp"/>-->
        <property name="userDao" ref="userDaoByOracleImp"/>
    </bean>
```

### (二)IOC的实现-通过构造器完成依赖注入

**构造器完成依赖注入**

```java
public class UserServiceImp(){
    
    DaoBySqlserver daoBySqlserver;
    
    public UserServiceImp(){
        super();
    }
    
    public UserServiceImp(DaoBySqlserver daoBySqlserver){
        this.daoBySqlserver = daoBysqlserver;
    }
    
    public void getUserInfo(){
        print("...")
    }
}
```

```xml
<bean id="userServiceImp" class="org.wr.software.experience.service.imp.UserServiceImp">
		<constructor-arg index="0" value = "有参构造的第一个参数值" />
    	//<constructor-arg type="java.lang.String" value = "有参构造的参数值只有一个参数" />
    	//<constructor-arg name="实参名称" value = "有参构造的参数值" />
</bean>
```



```java
注：constructor-arg标签-有参构造时使用

通过下标给构造参数赋值；

- index="0"：表示给第1一个参数赋值
- value="": 具体值

通过类型给构造参数赋值；

- type="java.lang.String" 
- value = "有参构造的参数值只有一个参数"

通过参数名给构造参数赋值；

- name="实参名称"
- value = "有参构造的参数值"
```

## 五、自动装配：

注：自动装配是spring满足bean依赖的一种方式。

### (一) XML注册Bean,配置autowired属性完成自动装配

**autowire属性的值**

|   值   |                             作用                             |
| :----: | :----------------------------------------------------------: |
| ByName | 给这个bean对象里面的类属性赋值时，会自动在容器上下文中查找，和自己对象set方法后面的值(首字母小写)，相同的Bean的id，成功找到就赋该Bean。 |
| ByType | 给这个bean对象里面的类属性赋值时，会自动在容器上下文中查找，和自己对象属性类型相同的Bean的id，成功找到就赋值该Bean。 |

```java
public class UserDaoByOracleImp implements UserDao {
    public void findUserInfoInDB() {
        System.out.println("通过Oracle获取User信息");
    }
}
```

```java
public class UserServe {
    UserDao userDao ;

    public void getUserInfo(){

        userDao.findUserInfoInDB();
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
```

* byName

  ```java
     <bean id="userDao" class="org.wr.software.experience.dao.imp.UserDaoBySqlserverImp"></bean>
     <bean id="userServiceImp" class="org.wr.software.experience.service.imp.UserServiceImp" autowire="byName"></bean>
     //会去匹配和id叫userDao的Bean， 即此处的userDao。
  ```

* ByType

  ```java
  <-- <bean id="userDao1" class="org.wr.software.experience.dao.imp.UserDaoByOracleImp"></bean>   -->
      <bean id="userDao2" class="org.wr.software.experience.dao.imp.UserDaoBySqlserverImp"></bean>
      <bean id="userServiceImp" class="org.wr.software.experience.service.imp.UserServiceImp" autowire="byType"></bean>
      //会去匹配和UserDao类型一致的Bean，这里配置的userDao2是UserDao的子类，故会被装配上。
      //若能匹配Bean超过一个，则报错，上面放开就会报错。
  ```

### (二) XML注册Bean,注解完成自动装配

注：spring2.5后即开始支持注解实现。

**注：若是直接在属性上加注解，则是通过底层的反射机制实现的。**

**注：如果是加在setXXX方法上，则是通过setXXX方法实现的。**

|   对比   |                          @Autowired                          |                          @Resource                           |
| :------: | :----------------------------------------------------------: | :----------------------------------------------------------: |
| 注解来源 |                          Spring注解                          |              JDK注解(JSR-250标准注解，属于J2EE)              |
| 装配方式 | 默认通过byType的方式来查找容器中的Bean；<br />若需要使用byName的方式来查找容器中的Bean；则需要配合使用@Qualifier(value = "beanName")。 | 默认通过byName的方式来查找容器中的Bean，没找到才会按照byType的方式来查找。<br />@Resource([ name = "" ] / [ type = "" ]) |
|   属性   |   @Autowired(required=False)表示依赖的类不允许是null类型。   |           @Resource([ name = "" ] / [ type = "" ])           |
|   注意   |     也可以写在setXXX方法上，前提是该类已经在Bean容器中。     |                                                              |

* @Autowired

  默认byType，如果用要用byName则必须要求这个对象存在。

* @Resource

  默认byName，找不到再去byType，还没找到就报错。

1. @Autowired的作用要生效：有一个地方要注意

①需要修改xml的命名空间；（加content)

②需要在xml文件中间加入 **<context:annotation-config />**，表示支持注解完成依赖注入。

即xml配置文件的格式应该如下：

```java
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/context
       http://www.springframework.org/schema/context/spring-context.xsd">

    <context:annotation-config/>

    <bean id="userDao2" class="org.wr.software.experience.dao.imp.UserDaoBySqlserverImp"></bean>
    <bean id="userServiceImp" class="org.wr.software.experience.service.imp.UserServiceImp" />

</beans>
```

### (三) 注解注册Bean,注解完成自动装配

1. 能够注册Bean的注解：

   @component  = @Service 、@Controller 、@Repository (@Scope... 、)

   @Autowired = @Resource 

   ```java
   //该类被spring管理了，即成为了Bean。
   //相当于<bean id = "user" class="org.www.xxx.xxx"/>,id默认为首字母小写版本
   @component
   //@Scope（"prototype"）
   public class User{
       @Value("WR")
       public String name;
       //相当于<property name = "name" value = "WR"/>
   }
   ```

2. @component等注解的作用要生效：有两个地方要注意

   ①需要修改xml的命名空间；（加content)

   ②需要在xml文件中间加入 **<context:annotation-config />**，表示支持注解完成依赖注入。

   ③需要在xml文件中加入**<context:component-scan base-package="org.wr.software.experience.dao"/>**，表示该目录下的注解生效。

   ```java
   <?xml version="1.0" encoding="UTF-8"?>
   <beans xmlns="http://www.springframework.org/schema/beans"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xmlns:context="http://www.springframework.org/schema/context"
          xsi:schemaLocation="http://www.springframework.org/schema/beans
          http://www.springframework.org/schema/beans/spring-beans.xsd
          http://www.springframework.org/schema/context
          http://www.springframework.org/schema/context/spring-context.xsd">
   
       <context:annotation-config/>
       <context:component-scan base-package="org.wr.software.experience.dao"/>
       <context:component-scan base-package="org.wr.software.experience.service"/>
   
   </beans>
   ```

## 六、自动装配（脱离xml）

*实际上底层还是或多或少使用了第五点第（三）项里面的注解，只不过不需要再去配置XML文件了*

1. **springboot中能够完成注册Bean的注解**

   @Configuration 、@Bean

   ```java
   //Configuration注解表示下面的这个类是一个配置类，即Bean.xml文件。
   //该注解的底层实现其实也加了@component
   @Configuration
   public class User{
   	
       //等价于<bean id="makeUserBean" class="org.xxx.xxx.xx" />
       //id为方法名，class为返回的类的位置。
       @Bean
       public  User makeUserBean(){
   		return new User();
       }
   }
   @component  = @Service 、@Controller 、@Repository (@Scope... 、)
   
   @Autowired = @Resource 
   ```

2. 

3. **如果完全使用了配置类的方式去注册bean，那么只能通过AnnotationConfig上下文来获取容器,参数为@Configuration配置类对象。**

   ```java
   public class MyTest{
       public static void main(String [] args){
           AnnotationConfigApplicationContext acac = new AnnotationConfigApplictionContext("User.class);
           User u = (User) context.getBean（"makeUserBean"）;
       }
   }
   ```

4. 多认识几个注解

   @import（其他配置类）

   如果有多个@Configuration配置类对象，那么可以在任意一个@Configuation上写import（其他配置类），使之成为一个类。

   @ComponentScan（其他包的路径）

   如果你的其他包都在使用了@SpringBootApplication注解的main app所在的包及其下级包，则你什么都不用做，SpringBoot会自动帮你把其他包都扫描了

   如果你有一些bean所在的包，不在main app的包及其下级包，那么你需要手动加上@ComponentScan注解并指定那个bean所在的包

## 七、注意点

1. SpringBoot项目的Bean装配默认规则是根据Application类所在的包位置从上往下扫描！ 
   “Application类”是指SpringBoot项目入口类。这个类的位置很关键：

   如果Application类所在的包为：`io.github.gefangshuai.app`，则只会扫描`io.github.gefangshuai.app`包及其所有子包，如果service或dao所在包不在`io.github.gefangshuai.app`及其子包下，则不会被扫描

2. @Autowired是Spring 提供的,默认按照byType 注入，也就是bean的类型的来传入。如果需要指定名字，那么需要使`@Qualifier("这是bean的id名字")`

3. @Resource装配顺序：

   ①如果同时指定了name和type，则从Spring上下文中找到唯一匹配的bean进行装配，找不到则抛出异常。

   ②如果指定了name，则从上下文中查找名称（id）匹配的bean进行装配，找不到则抛出异常。

   ③如果指定了type，则从上下文中找到类似匹配的唯一bean进行装配，找不到或是找到多个，都会抛出异常。

   ④如果既没有指定name，又没有指定type，则自动按照byName方式进行装配；如果没有匹配，则回退为一个原始类型进行匹配，如果匹配则自动装配。

   @Resource的作用相当于@Autowired，只不过@Autowired按照byType自动注入。

   
