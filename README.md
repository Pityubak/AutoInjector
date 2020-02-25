# AutoInjector
Example library with Liberator

### Overview

Here's example for usage:

1. Create an interface:
```java
public interface SomeMagic {
    void send(String msg);
}

```
2. Implement the interface and here should be used the @Service annotation:

```java
@Service
public class SomeMagicImpl implements SomeMagic {

    @Override
    public void send(String msg) {
        System.out.println(msg);
    }
    
}
```

 3. Inject with @AutoInject annotation:

```java
    //case 1: just one class implements interface
    @AutoInject
    private SomeMagic magic;
    
    //case 2: more than one 
    @AutoInject(SomeMagicImpl.class)
    private SomeMagic pureMagic;
    
 ```
 
 ### Example project:
 [AutoInjectorExample](https://github.com/Pityubak/AutoInjectorExample)

