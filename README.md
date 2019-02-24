## Files
Input Files: 
1. soc-LiveJournal1Adj.txt
2. userdata.txt

Jar File: MapReduce.jar
Class: Part 1


## Running the program

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



