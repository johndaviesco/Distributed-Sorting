#!/bin/sh
#$ -S /bin/sh
#$ -N nwen303_test
#$ -wd /vol/grid-solar/sgeusers/daviesjohn
#$ -pe nwen303_1.pe 4
#
echo ==UNAME==
uname -n
/usr/pkg/bin/mpirun -np $NSLOTS \
 /usr/pkg/java/sun-7/bin/java   \
   -classpath /u/students/daviesjohn/work/NWEN_303_Project_2/bin/ \
DistributedInsertionSort /u/students/daviesjohn/work/NWEN_303_Project_2/data/seq.saw.1000.txt