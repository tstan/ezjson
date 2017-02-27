# ezjson - still a work in progress as of 02/27/17
An easier to use JSON library for Java.
Information from http://www.json.org.

Intended compliance with http://www.ietf.org/rfc/rfc4627.txt

Intended support:
- reading JSON
- writing JSON
- customizable printing options
- validating JSON

## Usage:

```java
JsonObject myObj = new JsonObject();
myObj.add("attribute1", "value1");
myObj.add("attribute2", "value2");
myObj.add("attribute3", "value3");

System.out.println(myObj.toStringPretty());
```

## Output:

```
{
    "attribute1": "value1",
    "attribute2": "value2",
    "attribute3": "value3"
}
```