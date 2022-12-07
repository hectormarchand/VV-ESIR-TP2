# Extending PMD

Use XPath to define a new rule for PMD to prevent complex code. The rule should detect the use of three or more nested `if` statements in Java programs so it can detect patterns like the following:

```Java
if (...) {
    ...
    if (...) {
        ...
        if (...) {
            ....
        }
    }

}
```
Notice that the nested `if`s may not be direct children of the outer `if`s. They may be written, for example, inside a `for` loop or any other statement.
Write below the XML definition of your rule.

You can find more information on extending PMD in the following link: https://pmd.github.io/latest/pmd_userdocs_extending_writing_rules_intro.html, as well as help for using `pmd-designer` [here](https://github.com/selabs-ur1/VV-TP2/blob/master/exercises/designer-help.md).

Use your rule with different projects and describe you findings below. See the [instructions](../sujet.md) for suggestions on the projects to use.

## Answer

La règle à créer est la suivante : ```//IfStatement[count(ancestor::IfStatement)>=2]```

On génère la règle .xml grâce à PMD designer, on obtient : 
```xml
<?xml version="1.0"?>

<ruleset name="Custom Rules"
    xmlns="http://pmd.sourceforge.net/ruleset/2.0.0"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://pmd.sourceforge.net/ruleset/2.0.0 https://pmd.sourceforge.io/ruleset_2_0_0.xsd">

    <description>
        My custom rules
    </description>


    <rule name="if Rule"
      language="java"
      message="3 nested if statements"
      class="net.sourceforge.pmd.lang.rule.XPathRule">
   <description>
      detect the use of three or more nested if statements in
      Java programs
   </description>
   <priority>3</priority>
   <properties>
      <property name="version" value="2.0"/>
      <property name="xpath">
         <value>
<![CDATA[
//IfStatement[count(ancestor::IfStatement)>=2]
]]>
         </value>
      </property>
   </properties>
</rule>
</ruleset>
```

On l'utilise de la façon suivante :  
```$ pmd -d your-project/src -R path/to/rule.xml -f text```

Encore sur le projet Apache commons-cli, on analyse le code grâce à PMD et on obtient :  
**commons-cli/src/main/java/org/apache/commons/cli/DefaultParser.java:363:    if Rule:    3 nested if statements**