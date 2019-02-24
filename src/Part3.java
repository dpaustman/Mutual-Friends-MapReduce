
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;

import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.hadoop.util.StringUtils;

import java.util.HashMap;

public class Part3  {
	
	public static class Map extends Mapper<LongWritable,Text,Text,Text>
	{
		private static HashMap<String,String> users;
		String personA="";
		String personB="";
		
		
		public void setup(Context context) throws IOException
		{
			Configuration conf=context.getConfiguration();
			users = new HashMap<String, String>();
			String userDataPath=conf.get("userPath");
			Path path = new Path("hdfs://localhost:9000"+userDataPath );
			FileSystem fs=FileSystem.get(conf);
			BufferedReader buff=new BufferedReader(new InputStreamReader(fs.open(path)));
		
			String input=buff.readLine();
			while(input!=null)
			{
				String[] temp=input.split(",");
				if(temp.length==10)
				{
					String user=temp[1]+" "+temp[2]+": "+temp[4];
					users.put(temp[0].trim(), user);
				}
			
				input=buff.readLine();
			}
		}
	
		public void map(LongWritable key, Text value,Context context) throws IOException, InterruptedException
		{
			String[] line = value.toString().split("\t");
			if(line.length!=2)
			{
				return;
			}
			Configuration conf=context.getConfiguration();
			personA=conf.get("personA");
			personB=conf.get("personB");
			String[] parts=line[0].split(",");
			if(parts[0]!=null && parts[1]!=null)
			{
				
				if(parts[0].equals(personA) && parts[1].equals(personB))
				{
			
				
					String[] mutualFriends=line[1].split(",");
					for(String mutualFriend:mutualFriends)
					{
						if(users.containsKey(mutualFriend))
						{
							String details=users.get(mutualFriend);
							context.write(new Text(line[0].toString()), new Text(details));
						}
					
					}
				}
			}
		
		}
	}
	
	
	
	
	public static class Reduce extends Reducer<Text,Text,Text,Text>
	{
		public void reduce(Text key, Iterable<Text> values,Context context) throws IOException,InterruptedException
		{
		
		ArrayList<String> temp=new ArrayList<String>();
		
		for(Text value:values)
		{
			
			temp.add(value.toString());
		}
		
		context.write(key,new Text("["+StringUtils.join(",",temp)+"]"));
		}
		
	}
	
	public static void main(String args[]) throws Exception
	{
		Configuration confA=new Configuration();
		String[] otherargs=new GenericOptionsParser(confA,args).getRemainingArgs();
		
		
		Job jobA = Job.getInstance(confA,"MutualFriends");
		jobA.setJarByClass(Part3.class);
		jobA.setMapperClass(Part1.Map.class);
		
		jobA.setReducerClass(Part1.Reduce.class);
		jobA.setOutputKeyClass(Text.class);
		jobA.setOutputValueClass(Text.class);
		FileInputFormat.addInputPath(jobA,new Path(otherargs[1]));
		FileOutputFormat.setOutputPath(jobA, new Path(otherargs[2]));
		boolean mapReduce=jobA.waitForCompletion(true);
		
		if(mapReduce)
		{
			Configuration confB=new Configuration();
			confB.set("personA",otherargs[4].trim());
			confB.set("personB",otherargs[5].trim());
			confB.set("userPath",otherargs[0].trim());
			Job jobB = Job.getInstance(confB,"MutualFriends");
			jobB.setJarByClass(Part3.class);

			jobB.setInputFormatClass(TextInputFormat.class);
			jobB.setMapperClass(Map.class);
			
			jobB.setReducerClass(Reduce.class);
			jobB.setOutputKeyClass(Text.class);
			jobB.setOutputValueClass(Text.class);
			FileInputFormat.addInputPath(jobB,new Path(otherargs[2]));
			FileOutputFormat.setOutputPath(jobB, new Path(otherargs[3]));
			
			System.exit(jobB.waitForCompletion(true)?0:1);
		}
		
	}
}


