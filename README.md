## Task A
Write a MapReduce program in Hadoop that implements a simple “Mutual/Common friend list of two friends". The key idea is that if two people are friend then they have a lot of mutual/common friends. This program will find the common/mutual friend list for them.

For example,
Alice’s friends are Bob, Sam, Sara, Nancy Bob’s friends are Alice, Sam, Clara, Nancy Sara’s friends are Alice, Sam, Clara, Nancy
As Alice and Bob are friend and so, their mutual friend list is [Sam, Nancy]
As Sara and Bob are not friend and so, their mutual friend list is empty. (In this case you may exclude them from your output).

Input Files: 
1. soc-LiveJournal1Adj.txt
2. userdata.txt

## Running the program for Task A
Jar File: MapReduce.jar
Class: Part 1
1. Create a directory on HDFS and put the input files.
  ```
  hdfs dfs -mkdir /test
  hdfs dfs -put <location of soc-LiveJournal1Adj.txt on PC> /test
  hdfs dfs -put <location of userdata.txt PC> /test
  ```
2. Delete the output directory if it already exists:
```
hdfs dfs -rm -r /test/out
```
3. Run the jar file:
```
hadoop jar <Location of MapReduce.jar on PC> Part1 /test/soc-LiveJournal1Adj.txt /test/out
```
4. Read the output
  ``` 
  hdfs dfs -cat /test/out/part-r-00000
 ```
5. To get output for specific pairs, run:
  ```
  hdfs dfs -cat /test/out/part-r-00000 | grep "<userid_1>,userid_2> <press ctrl+v><press tab>"
  ```
  
## Output
0,1             5,20

20,28193        1

1,29826         

6222,19272

28041,28056



