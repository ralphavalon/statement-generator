# statement-generator #
JDBC Helper. It generates all statements from Java classes and its variables.

### Example - Converting from .java ###
```
java -jar statement-generator-{version}.jar yourClass.class
```

### Example - Converting from .class ###
```
java -jar statement-generator-{version}.jar yourClass.class
```

### Configuring ###
Just put a *config.properties* in the same path of the jar file. These are the available key properties and its values:

#### prepared_statement ####
The PreparedStatement variable name. **Default:** pstm

* pstm - The PreparedStatement variable name 

#### result_set ####
The ResultSet variable name. **Default:** resultSet
 
* resultSet - The ResultSet variable name 

#### ignore_fields ####
The fields to ignore. Example: ignore_fields=name,age

#### sqls ####
The SQLs for the classes. **Default** is all options.

* INSERT - Insert statement 

#### statements ####
The Java code for the classes. **Default** is all options.

* PSTM - Generates the PreparedStatement code for the classes.
* RESULTSET - Generates the ResultSet code for the classes.

#### naming_strategy ####
See https://docs.jboss.org/hibernate/orm/4.2/javadocs/org/hibernate/cfg/NamingStrategy.html

**Default:** improved

* ejb3 - EJB3NamingStrategy  
* improved - ImprovedNamingStrategy 

#### file_type ####
It changes the default extension to search (changes the parser). **Default:** class

* java - Uses .java parser
* class - Uses .class parser

#### output #### 
The output of the result. **Default:** file

* console - Prints the output to the console. 
* file - Prints the output to files.