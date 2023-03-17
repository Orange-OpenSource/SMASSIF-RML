# SMASSIF-RML / lib folder

Place for third party components.

## IBCNServices / StreamingMASSIF

```shell
git clone https://github.com/IBCNServices/StreamingMASSIF.git
cd StreamingMASSIF/
mvn install -Dmaven.test.skip=true
```

Install process should end with output akin to:

```
[INFO] Installing ~/SMASSIF-RML/lib/StreamingMASSIF/target/massif.jar to ~/.m2/repository/be/ugent/idlab/massif/0.0.1/massif-0.0.1.jar
[INFO] Installing ~/SMASSIF-RML/lib/StreamingMASSIF/pom.xml to ~/.m2/repository/be/ugent/idlab/massif/0.0.1/massif-0.0.1.pom
```

... thus satisfying dependencies for [SMASSIF-RML/massif/pom.xml](../massif/pom.xml) and enabling further compilation/usage.


