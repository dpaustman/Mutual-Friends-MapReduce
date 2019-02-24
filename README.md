## Mutual Friends - MapReduce

## Task A
Write a MapReduce program in Hadoop that implements a simple “Mutual/Common friend list of two friends". The key idea is that if two people are friend then they have a lot of mutual/common friends. This program will find the common/mutual friend list for them.

For example,
Alice’s friends are Bob, Sam, Sara, Nancy Bob’s friends are Alice, Sam, Clara, Nancy Sara’s friends are Alice, Sam, Clara, Nancy
As Alice and Bob are friend and so, their mutual friend list is [Sam, Nancy]
As Sara and Bob are not friend and so, their mutual friend list is empty. (In this case you may exclude them from your output).

Here, <User> is a unique integer ID corresponding to a unique user and <Friends> is a
comma-separated list of unique IDs corresponding to the friends of the user with the unique ID <User>. Note that the friendships are mutual (i.e., edges are undirected): if A is friend with B then B is also friend with A. The data provided is consistent with that rule as there is an explicit entry for each side of each edge. So when you make the pair, always consider (A, B)or(B,A)foruserAandBbutnotboth.
Output: The output should contain one line per user in the following format:
<User_A>, <User_B><TAB><Mutual/Common Friend List>
where <User_A> & <User_B> are unique IDs corresponding to a user A and B (A and B are friend). < Mutual/Common Friend List > is a comma-separated list of unique IDs corresponding to mutual friend list of User A and B.


Please find the output for the following pairs:
(0,1), (20, 28193), (1, 29826), (6222, 19272), (28041, 28056)



### Running Task A: 
Input Files: 
1. soc-LiveJournal1Adj.txt

Jar File: MapReduce.jar

Class: Part2

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
  hdfs dfs -cat /test/out/part-r-00000 | grep "<userid_1>,<userid_2> <press ctrl+v> <press tab>"
  ```
  
### Output 
```
0,1               5,20
20,28193          1
1,29826         
6222,19272
28041,28056
```


## Task B
Please answer this question by using dataset from Q1.
Find friend pairs whose number of common friends (number of mutual friend) is within the top-10 in all the pairs. Please
output them in decreasing order.
Output Format:
<User_A>, <User_B> <TAB> <Number of Mutual Friends><TAB><Mutual/Common Friend Number>

### Running Task B
Input Files: 
1. soc-LiveJournal1Adj.txt
Jar File: MapReduce.jar
Class: Part2

1. Delete the output directories if they already exists:
```
hdfs dfs -rm -r /test/out1
hdfs dfs -rm -r /test/out2
```
2. Run the jar file:
```
hadoop jar <Location-of-MapReduce.jar-on-local-PC> Part2 /test/soc-LiveJournal1Adj.txt /test/out1 /test/out2
```
3. Read the output
  ``` 
  hdfs dfs -cat /test/out2/part-r-00000
 ```

### Output  
```
16539,40423     64
3610,3634       61
31482,31545     60
41851,41903     58
2689,29425      55
47034,47047     55
32867,32869     54
7983,8003       52
4069,16215      52
4425,4447       52
```

### Task C
Please use in-memory join to answer this question.
Given any two Users (they are friend) as input, output the list of the names and the city of their mutual friends.
Note: use the userdata.txt to get the extra user information. Output format:
UserA id, UserB id, list of cities of their mutual Friends.
Sample Output:

0, 41 [Evangeline: Loveland, Agnes: Marietta]

### Running Task C:
Input Files: 
1. soc-LiveJournal1Adj.txt
2. userdata.txt

Jar File: MapReduce.jar
Class: Part3

1. Delete the output directories if they already exists:
```
hdfs dfs -rm -r /test/out1
hdfs dfs -rm -r /test/out2
```
2. Run the jar file:
```
hadoop jar <Location-of-MapReduce.jar-on-local-PC> Part2 /test/userdata.txt /test/soc-LiveJournal1Adj.txt /test/out1 /test/out2 <userid_1> <userid_2>
```
3. Read the output
  ``` 
  hdfs dfs -cat /test/out2/part-r-00000
 ```

### Output 
Sample output:
```
0,1	[Juan Figueroa: Columbia,Beth Watson: Lake Worth]
```

