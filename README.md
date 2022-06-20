# MetroBillingSystem
System to calculate fare of each trip via using CSv file

Please execute below command to build your maven project which will validate your trips.csv file
mvn clean install
After your build is done then please run below command with your file name
mvn compile exec:java -Dexec.mainClass="com.metro.app.MetroBillingSystem" -Dexec.args="taps.csv"
Once your command is executed sucessfully then you will get your trips.csv file in your project folder. MetrobillingSystem/
You must keep your trips.csv inside src/main/resources/ folder.

If you need to change the request file name the you should change the command give in line number 4 with updated file name.
