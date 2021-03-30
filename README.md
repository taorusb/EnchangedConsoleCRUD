### Enchanged Console CRUD application v 1.0

#### JDK version used: 15

##### To launch the application, type in the command prompt:

javac -sourcepath ./src -d bin  src/main/java/com/taorusb/consolecrudenchanged/Main.java

java -classpath ./bin com.taorusb.consolecrudenchanged.Main

##### The data is saved and loaded after each request.

##### Used library for converting to json:
Gson

##### Used design patterns:
Chain of responsability,  Mediator, Facade, Singleton

##### The data is stored in files with the extension .json and written to them in the same format.

##### The first line in all files is the id counter, which is incremented after each object is added.

##### The query command has a similar sql structure (commands are processed regardless of the case of letters).

##### Request structure:

[operation type] [model type] [arguments]

##### Where operation type:

select all, update, insert into, delete from

##### Where model type:

writers, posts, regions

##### Where arguments:

##### writers: 
select - none, update - id= firstname= lastname= , insert - firstname= lastname= , delete - id=

##### posts: 
select - writerid= , update - id= content= , insert - writerid= content= , delete - id= 

##### regions: 
select - writerid= , update - id= name= , insert - writerid= name= , delete - id=
