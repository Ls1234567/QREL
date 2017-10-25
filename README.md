QREL is a toolkit to find a maximum successful sub-query
=========================================
/QREL contains the whole project.<br>
/example contains an example entity-relation graph.<br>
/example/ExampleGraph.pdf is how the exapmle entity-relation graph look like.<br>
/example/ExampleTriples is the source data of the example.<br>
/qrel.jar the runnable jar file. To run the example correctly, you have to juxtapose the example and qrel.jar <br>

To running a completed progress, you may wish to run <br>
'java -cp qrel.jar name.sxli.exapmle.Step1_ExampleTriplePreprocessor'<br>
for the preprocessing and run <br>
'java -cp qrel.jar name.sxli.exapmle.Step2_ExampleOracleUsage'<br>
to construct distance oracle.Then <br>
'java -cp qrel.jar name.sxli.exapmle.Step3_ExampleQueryRelaxation'<br>
is to find a maximum successful sub-query of Alice,Bob and Dan in the example graph.<br>
