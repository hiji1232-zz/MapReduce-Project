Hadoop vs. Spark
=====

This is the source code of Hadoop and Spark project. 

The purpose of this project is to compare the performance of Hadoop MapReduce vs. Spark MapReduce, when solving the test dependence problem. 

Test Dependence Problem:
=====================
In a test suite,all the testcases should be independent, which means no test should be affect other test result. I'm using Hadoop and Spark map reduce framework to detect this problem. 

As test suites are too large, so I store them in Amazon S3 and run them on Amazon Elastic MapReduce (Amazon EMR).  

By Zhen Hu


