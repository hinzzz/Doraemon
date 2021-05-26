package com.hinz.problems.spring.annotation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class QualifierProblem {

    /*@Autowired
    @Qualifier("myQualifierTwo")
    private MyQualifier myQualifier;*/

    @Qualifier
    @Autowired(required = false)
    private List<MyQualifier> myQualifierList = Collections.emptyList();;

    /*public void toSay(){
        myQualifier.say();
    }
*/

    public void list(){
        System.out.println("myQualifierList.size() = " + myQualifierList.size());
    }

}


interface MyQualifier{
    void say();
}

//@Component("myQualifierOne")
@Qualifier
@Component
class MyQualifierOne implements MyQualifier{
    @Override
    public void say() {
        System.out.println("one");
    }
}
//@Component("myQualifierTwo")
@Qualifier
@Component
class MyQualifierTwo implements MyQualifier{
    @Override
    public void say() {
        System.out.println("two");
    }
}