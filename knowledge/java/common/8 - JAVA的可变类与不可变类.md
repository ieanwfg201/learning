# JAVA的可变类与不可变类
## 1. 定义
可变类和不可变类(Mutable and Immutable Objects)的初步定义：
* 可变类：当你获得这个类的一个实例引用时，你可以改变这个实例的内容。
* 不可变类：当你获得这个类的一个实例引用时，你不可以改变这个实例的内容。不可变类的实例一但创建，其内在成员变量的值就不能被修改。
## 2. 如何创建一个自己的不可变类：
* 所有成员都是private
* 不提供对成员的改变方法，例如：setXXXX
* 确保所有的方法不会被重载。手段有两种：使用final Class(强不可变类)，或者将所有类方法加上final(弱不可变类)。
* 如果某一个类成员不是原始变量(primitive)或者不可变类，必须通过在成员初始化(in)或者get方法(out)时通过深度clone方法，来确保类的不可变。
## 3. 一个示例
     
     public final class BrokenPerson{                                                               
       private String firstName;                                     
       private String lastName;                                      
       private Date dob;                                             
                                               
       public BrokenPerson( String firstName,                   
         String lastName, Date dob)                                                    
       {                                                              
        this.firstName = firstName;               
        this.lastName = lastName;                         
        // this.dob = dob;     //error       
        this.dob = new Date( dob.getTime() ); //correct
       }                                                                                       }                                      
                                                                  
       public String getFirstName()
       {
        return this.firstName;
       }
       public String getLastName()
       {
        return this.lastName;
       }
       public Date getDOB()                                                               
       {        
        // return this.dob;    //error      
        return new Date( this.dob.getTime() );//correct            
       }                                                                                              
      }

由于java.util.Date对象是可变对象，所以无论是通过构造函数传入合适通过get方法获取到该对象的时候都需要做深度拷贝．