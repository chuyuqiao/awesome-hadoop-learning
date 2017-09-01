# How to Install Hadoop in Stand-Alone Mode on Ubuntu 16.04 #

## Step 1 — Installing Java ##

To get started, we'll update our package list:

    sudo apt-get update

Next, we'll install OpenJDK, the default Java Development Kit on Ubuntu 16.04.

    sudo apt-get install default-jdk

Once the installation is complete, let's check the version.

    java -version
## Step 2 — Installing Hadoop ##
find the most recent stable release.

    wget http://apache.mirrors.tds.net/hadoop/common/hadoop-2.7.3/hadoop-2.7.3.tar.gz
check using SHA-256

    wget https://dist.apache.org/repos/dist/release/hadoop/common/hadoop-2.7.3/hadoop-2.7.3.tar.gz.mds
Then run the verification:

    shasum -a 256 hadoop-2.7.3.tar.gz
Compare this value with the SHA-256 value in the .mds file

    cat hadoop-2.7.3.tar.gz.mds
    
we'll use the tar command with the -x flag to extract, -z to uncompress, -v for verbose output, and -f to specify that we're extracting from a file

    tar -xzvf hadoop-2.7.3.tar.gz
Finally, we'll move the extracted files into /usr/local, the appropriate place for locally installed software. Change the version number, if needed, to match the version you downloaded.

    sudo mv hadoop-2.7.3 /usr/local/hadoop

## Step 3 — Configuring Hadoop's Java Home ##
he path to Java, /usr/bin/java is a symlink to /etc/alternatives/java, which is in turn a symlink to default Java binary. We will use readlink with the -f flag to follow every symlink in every part of the path, recursively. Then, we'll use sed to trim bin/java from the output to give us the correct value for JAVA_HOME.

To find the default Java path

    readlink -f /usr/bin/java | sed "s:bin/java::"

    Output
    
    /usr/lib/jvm/java-8-openjdk-amd64/jre/
You can copy this output to set Hadoop's Java home to this specific version, which ensures that if the default Java changes, this value will not. Alternatively, you can use the readlink command dynamically in the file so that Hadoop will automatically use whatever Java version is set as the system default.

To begin, open hadoop-env.sh:

    sudo nano /usr/local/hadoop/etc/hadoop/hadoop-env.sh

### Option 1: Set a Static Value ###
     . . .
    #export JAVA_HOME=${JAVA_HOME}
    export JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64/jre/
     . . . 
### Option 2: Use Readlink to Set the Value Dynamically ###
     . . .
    #export JAVA_HOME=${JAVA_HOME}
    export JAVA_HOME=$(readlink -f /usr/bin/java | sed "s:bin/java::")
     . . . 

## Step 4 — Running Hadoop ##
    /usr/local/hadoop/bin/hadoop

    mkdir ~/input
    cp /usr/local/hadoop/etc/hadoop/*.xml ~/input

    /usr/local/hadoop/bin/hadoop jar /usr/local/hadoop/share/hadoop/mapreduce/hadoop-mapreduce-examples-2.7.3.jar grep ~/input ~/grep_example 'principal[.]*'
    cat ~/grep_example/*

## 参考 ##
1. [How to Install Hadoop in Stand-Alone Mode on Ubuntu 16.04](https://www.digitalocean.com/community/tutorials/how-to-install-hadoop-in-stand-alone-mode-on-ubuntu-16-04)


