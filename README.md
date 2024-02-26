# the-matcher

## Purpose

The purpose of the library is to provide a method for checking the differences between two JSON. In particular, define precisely how to compare two JSON.

### Definition of comparison object

A comparison between two json must be possible by excluding some paths or defining false positive elements

i.e.


| left                 | right                |
| -------------------- | -------------------- |
| {"data":•[1,2,3]•} | {"data":•[3,2,1]•} |

These two objects have an array and this array can be defined as unordered with a specific configuration

There are differents types of filter comparison depending on types:

* Primitives
* Array
* Object

#### Primitives

Comparison on primitives, should be ignore a value


| left            | right             |
|-----------------|-------------------|
| {"data":•true•} | {"data":•"true"•} |

In this case should be possibile to define that two objects  are equals

| left                                  | right             |
|---------------------------------------|-------------------|
| {"data":•"true",<br/>"data1":"value"•} | {"data":•"true"•} |

In this case it is necessary to be able to define that the <data1> field is not taken into consideration in the differences

#### Array 

| left                 | right                |
| -------------------- | -------------------- |
| {"data":•[1,2,3]•} | {"data":•[3,2,1]•} |

In this case it is necessary to define a path to exclude sorting in the array


#### Object 
| left                 | right                |
| -------------------- | -------------------- |
| {"data":•[1,2,3]•} | {"data":•[3,2,1]•} |
