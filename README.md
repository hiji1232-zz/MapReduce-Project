Hadoop Zhen Hu

File strucutre:

1. Hadoop is the source code of implement Hadoop mapreduce framework, with 2 algorithms included.
   Inside, src is the source code folder, bin is the class file folder. Input is the space program, which will be called by my source code.
   As the Hadoop jar file has too much. Please get all the Hadoop source code from Hadoop website. I'm using 2.6 version.
2. spark is the sourcecode of implement Spark framework, with 2 algorithm included.
   Inside, src is the source code folder, bin is the class file folder. Input is the space program, which will be called by my source code.The Spark jar file is in lib folder.
3. testInput is my test suite, which work as input.
4. testOracle is the folder to store the expected result. After running the program,the result which be store here.


How to run and compile the program:

1. I'm settign up the Hadoop and Spark environment on my local laptop. For setting up the single node, please check hadoop and Spark website and follow detail instruction.
2. After setting up the envrionment, for Hadoop, you only need to run hadoop and pass input filename as argument to it; For spark, just run program itself, no input file needed.

If you have problem about running the program, please contact me via zhu@cse.unl.edu.