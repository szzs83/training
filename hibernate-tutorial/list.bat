echo usage: list events  vagy list persons
mvn compile exec:java -Dexec.mainClass="org.hibernate.tutorial.EventManager" -Dexec.args="list %1"