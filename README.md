Cloud Dataflow samples 
=

Prerequisites
-
1. Install Maven.
1. Create bucket.
1. Enable Dataflow API


WordCountSum
-
Check how many types a certain word shows up in Hamlet.
```$ mvn -Pdataflow-runner compile exec:java -Dexec.mainClass=ro.robertsicoie.gcp.dataflow.WordCountSum \ -Dexec.args="--project=<PROJECT_ID> --stagingLocation=gs://<BUCKET>/staging/ --output=gs://<BUCKET>/output/ --runner=DataFlowRunner"```

Check output bucket.